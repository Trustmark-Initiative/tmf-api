package edu.gatech.gtri.trustmark.v1_0.impl.util;

import edu.gatech.gtri.trustmark.v1_0.util.TrustmarkMailClient;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

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

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class TrustmarkMailClientImpl implements TrustmarkMailClient {

    public static final String SMTP_USER = "smtp.user";
    public static final String SMTP_PSWD = "smtp.pswd";
    public static final String SMTP_HOST = "smtp.host";
    public static final String SMTP_PORT = "smtp.port";
    public static final String SMTP_AUTH = "mail.smtp.auth";
    public static final String FROM_ADDRESS = "smtp.from.address";

    private Properties mailProps = new Properties();

    private String user;
    private String pswd;
    private String subject;
    private String text;
    private String host;
    private String fromAddress;
    private boolean authorize = true;

    private Object content;
    private String type;

    private List<String> attachments = new ArrayList<>();

    private List<String> toList = new ArrayList<>();
    private List<String> ccList = new ArrayList<>();
    private List<String> bccList = new ArrayList<>();

    /**
     * default constructor, allows for injection
     */
    public TrustmarkMailClientImpl()  {
        mailProps.put("mail.transport.protocol", "smtp");
        mailProps.put("mail.smtp.auth", "true");
        mailProps.put("mail.smtp.starttls.enable", "true");
    }

    /**
     * constructor taking userid and password
     * @param user
     * @param pswd
     */
    public TrustmarkMailClientImpl(String user, String pswd)  {
        super();

        mailProps.put("mail.user", user);
        this.user = user;
        this.pswd = pswd;
    }

    @Override
    public TrustmarkMailClient setUser(String user) {
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
        mailProps.put("mail.smtp.host", host);
        this.host = host;
        return this;
    }

    @Override
    public TrustmarkMailClient setSmtpPort(String port) {
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

    @Override
    public TrustmarkMailClient addAttachment(String fn) {
        if(fn != null && fn.length() > 0)  {
            attachments.add(fn);
        }
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
            mailProps.put("mail.smtp.auth", "true");
        } else {
            mailProps.put("mail.smtp.auth", "false");
        }
        this.authorize = auth;
        return this;
    }

    @Override
    public void sendMail() {
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

            attachments.forEach(f -> {
                MimeBodyPart bodyPart = new MimeBodyPart();
                try {
                    bodyPart.setDataHandler(new DataHandler(new FileDataSource(f)));
                    bodyPart.setFileName(f.substring(f.lastIndexOf(File.separator)+1));
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
