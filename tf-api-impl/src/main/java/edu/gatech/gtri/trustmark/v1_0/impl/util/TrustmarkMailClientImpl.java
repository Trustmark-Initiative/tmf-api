package edu.gatech.gtri.trustmark.v1_0.impl.util;

import edu.gatech.gtri.trustmark.v1_0.util.TrustmarkMailClient;
import org.apache.log4j.Logger;

import javax.activation.DataHandler;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import java.util.*;

public class TrustmarkMailClientImpl implements TrustmarkMailClient {

    public static final Logger log = Logger.getLogger(TrustmarkMailClientImpl.class);

    public static final String SMTP_USER = "smtp.user";
    public static final String SMTP_PSWD = "smtp.pswd";
    public static final String SMTP_HOST = "smtp.host";
    public static final String SMTP_PORT = "smtp.port";
    public static final String SMTP_AUTH = "mail.smtp.auth";
    public static final String FROM_ADDRESS = "smtp.from.address";

    private final Properties mailProps = new Properties();

    private String user;
    private String pswd;
    private String subject;
    private String text;
    private String host;
    private String fromAddress;
    private boolean authorize = true;

    private Object content;
    private String type;

    private final Map<String, ByteArrayDataSource> attachments = new HashMap<>();

    private final List<String> toList = new ArrayList<>();
    private final List<String> ccList = new ArrayList<>();
    private final List<String> bccList = new ArrayList<>();

    /**
     * default constructor, allows for injection
     */
    public TrustmarkMailClientImpl()  {
        mailProps.put("mail.transport.protocol", "smtp");
        mailProps.put("mail.smtp.auth", "true");
        mailProps.put("mail.smtp.starttls.enable", "true");
    }

    @Override
    public TrustmarkMailClient setUser(String user) {
        log.debug(String.format("TrustmarkMailClientImpl.setUser %s", user));
        this.user = user;
        return this;
    }

    @Override
    public TrustmarkMailClient setPswd(String pswd) {
        this.pswd = pswd;
        return this;
    }

    @Override
    public TrustmarkMailClient setSmtpHost(String host) {
        log.debug(String.format("TrustmarkMailClientImpl.setSmtpHost %s", host));
        mailProps.put("mail.smtp.host", host);
        this.host = host;
        return this;
    }

    @Override
    public TrustmarkMailClient setSmtpPort(String port) {
        log.debug(String.format("TrustmarkMailClientImpl.setPort %s", port));
        mailProps.put("mail.smtp.port", port);
        return this;
    }

    @Override
    public TrustmarkMailClient addRecipient(String addr) {
        if (addr != null & addr.length() > 0) {
            toList.add(addr);
        }
        return this;
    }

    @Override
    public TrustmarkMailClient setFromAddress(String addr) {
        log.debug(String.format("TrustmarkMailClientImpl.setFromAddress %s", addr));
        this.fromAddress = addr;
        return this;
    }

    @Override
    public TrustmarkMailClient addCCRecipient(String addr) {
        if(addr != null && addr.length() > 0)  {
            ccList.add(addr);
        }
        return this;
    }

    @Override
    public TrustmarkMailClient addBCCRecipient(String addr) {
        if (addr != null & addr.length() > 0)  {
            bccList.add(addr);
        }
        return this;
    }

    public TrustmarkMailClient addAttachment(String fn, ByteArrayDataSource bad) {
        if(fn != null && fn.length() > 0 && bad != null)  {
            attachments.put(fn, bad);
        }
        return this;
    }

    @Override
    public TrustmarkMailClient addAttachment(String fn) {
        return this;
    }

    @Override
    public TrustmarkMailClient setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    @Override
    public TrustmarkMailClient setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public TrustmarkMailClient setContent(Object obj, String type) {
        this.content = obj;
        this.type = type;
        return this;
    }

    @Override
    public TrustmarkMailClient setSmtpAuthorization(boolean auth)  {
        if(auth)  {
            log.debug(String.format("TrustmarkMailClientImpl.setSmtpAuthorization true"));
            mailProps.put("mail.smtp.auth", "true");
        } else {
            log.debug(String.format("TrustmarkMailClientImpl.setSmtpAuthorization true"));
            mailProps.put("mail.smtp.auth", "false");
        }
        this.authorize = auth;
        return this;
    }

    @Override
    public void sendMail() {
        log.debug(printMailProps());

        Session session = Session.getDefaultInstance(mailProps,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, pswd);
                    }
                }
        );

        session.setDebug(true);

        try (Transport transport = session.getTransport();) {
            Message msg =  new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromAddress));
            msg.setSubject(subject);
            msg.setSentDate(new Date());

            loadBody(msg);

            loadRecipients(msg);
            loadCCRecipients(msg);
            loadBCCRecipients(msg);

            loadAttachments(msg);

            if(authorize)  {
                transport.connect(host, user, pswd);
            }  else {
                transport.connect();
            }

            transport.sendMessage(msg, msg.getAllRecipients());
        } catch(MessagingException me)  {
            me.printStackTrace();
        }
    }

    private String printMailProps () {
        String propsText = "Properties: \n";
        for (Object key: mailProps.keySet()) {
            propsText += "   " + key + ": " + mailProps.getProperty(key.toString()) + "\n";
        }
        return propsText;
    }

    /**
     * load all files as attachments to the message
     * @param msg
     * @return
     * @throws MessagingException
     */
    private boolean loadAttachments(Message msg) throws MessagingException {

        if (attachments.size() > 0)  {  //  set text as the first part of the message then all the file attachments
            Multipart multipart = new MimeMultipart();

            MimeBodyPart hdrPart = new MimeBodyPart();

            loadBody(hdrPart);

            multipart.addBodyPart(hdrPart);

            attachments.forEach((k, v) -> {
                MimeBodyPart bodyPart = new MimeBodyPart();
                try {
                    bodyPart.setDataHandler(new DataHandler(v));
                    bodyPart.setFileName(k);
                    multipart.addBodyPart(bodyPart);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
            msg.setContent(multipart);
            return true;
        }

        return false;
    }

    private boolean loadBody(Message msg) throws MessagingException {
        if(text != null)  {
            msg.setText(text);
            return true;
        }  else  {
            msg.setContent(content, type);
        }
        return false;
    }

    /**
     * load the body part for this email
     * @param msg
     * @return
     * @throws MessagingException
     */
    private boolean loadBody(MimeBodyPart msg) throws MessagingException {
        if(text != null)  {
            msg.setText(text);
            return true;
        }  else  {
            msg.setContent(content, type);
        }
        return false;
    }

    /**
     * load all recipients to the message
     * @param msg
     */
    private void loadRecipients(Message msg)  {
        toList.forEach(s -> {
            try {
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(s));
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * load all recipients for carbon copy to the message
     * @param msg
     */
    private void loadCCRecipients(Message msg)  {
        ccList.forEach(s -> {
            try {
                msg.addRecipient(Message.RecipientType.CC, new InternetAddress(s));
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     *  load all recipients for blind carbon copy to the message
     * @param msg
     */
    private void loadBCCRecipients(Message msg)  {
        bccList.forEach(s -> {
            try {
                msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(s));
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
    }
}
