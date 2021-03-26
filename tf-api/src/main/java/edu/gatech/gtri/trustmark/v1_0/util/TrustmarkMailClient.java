package edu.gatech.gtri.trustmark.v1_0.util;

import javax.mail.util.ByteArrayDataSource;

public interface TrustmarkMailClient {

    /**
     * sets the user for this email
     *
     * @param user
     * @return
     */
    TrustmarkMailClient setUser(String user);

    /**
     * set the password for this email
     *
     * @param pswd
     * @return
     */
    TrustmarkMailClient setPswd(String pswd);

    /**
     * set the host mail server
     *
     * @param host
     * @return
     */
    TrustmarkMailClient setSmtpHost(String host);

    /**
     * set the host mail server port
     *
     * @param port
     * @return
     */
    TrustmarkMailClient setSmtpPort(String port);

    /**
     * add a mail recipient to send this email to
     *
     * @param addr
     * @return
     */
    TrustmarkMailClient addRecipient(String addr);

    /**
     * set whether authorization should be done for this email
     * @param auth
     * @return
     */
    TrustmarkMailClient setSmtpAuthorization(boolean auth);
    /**
     * set the from address for this email
     * @param addr
     * @return
     */
    TrustmarkMailClient setFromAddress(String addr);

    /**
     * add a mail recipient to carbon copy on this email
     *
     * @param addr
     * @return
     */
    TrustmarkMailClient addCCRecipient(String addr);

    /**
     * add a mail recipient to blind carbon copy on this email
     *
     * @param addr
     * @return
     */
    TrustmarkMailClient addBCCRecipient(String addr);

    /**
     * add a file attachment to this email
     *
     * @param fn
     * @return
     */
    TrustmarkMailClient addAttachment(String fn);

    /**
     * add a byte array to the attachments
     *
     * @param fn
     * @param bad
     * @return
     */
    TrustmarkMailClient addAttachment(String fn, ByteArrayDataSource bad);

    /**
     * set the subject of this email
     *
     * @param subject
     * @return
     */
    TrustmarkMailClient setSubject(String subject);

    /**
     * set the text body of this email
     *
     * @param text
     * @return
     */
    TrustmarkMailClient setText(String text);

    /**
     * sets the body content of this email
     * @param obj
     * @param type
     * @return
     */
    TrustmarkMailClient setContent(Object obj, String type);

    /**
     * construct this email from all the various parts and send it
     */
    void sendMail();
}