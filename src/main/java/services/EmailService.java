/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import exceptions.ServiceException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author Admin
 */
public class EmailService {

    private static final Logger LOGGER = Logger.getLogger(EmailService.class.getName());
    // Mail configurations
    public static final String HOST = "smtp.gmail.com";
    public static final int SSL_PORT = 465;
    public static final int TSL_PORT = 587;
    private static final String EMAIL = "congntse151288@fpt.edu.vn";
    private static final String PASSWORD = "singba2001";

    private Properties buildProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.socketFactory.port", SSL_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port", SSL_PORT);
        return props;
    }

    private Session getSession() {
        Properties props = buildProperties();
        PasswordAuthentication credentails = new PasswordAuthentication(EMAIL, PASSWORD);
        return Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return credentails;
            }
        });
    }

    public void sendTextMail(String to, String subject, String text) throws ServiceException {
        try {
            Session session = getSession();

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(EMAIL));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject(subject, "UTF-8");

            message.setText(text, "UTF-8");

            Transport.send(message);
        } catch (MessagingException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new ServiceException(ex.getMessage());
        }
    }

    public void sendHtmlMail(String to, String subject, String html) throws ServiceException {
        try {
            Session session = getSession();

            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(EMAIL));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject(subject, "UTF-8");

            message.setContent(html, "text/html; charset=UTF-8");

            Transport.send(message);
        } catch (MessagingException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            throw new ServiceException(ex.getMessage());
        }
    }
}
