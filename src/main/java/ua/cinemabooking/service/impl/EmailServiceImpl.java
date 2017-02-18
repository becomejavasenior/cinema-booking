package ua.cinemabooking.service.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ua.cinemabooking.service.EmailService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@Async
public class EmailServiceImpl implements EmailService {

    private String host="smtp.gmail.com";
    private String user="cinemabecomejavasenior@gmail.com";
    private String password="becomejavasenior0218";

    @Override
    public void sendMessage(String textMessage, String email) {
        sendMessage(textMessage, "Cinema Becomejavasenior" , email);
    }

    @Override
    public void sendMessage(String textMessage, String subject, String email) {
        Properties props = new Properties();
        props.put("mail.smtp.host",host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user,password);
                    }
                });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(email));
            message.setSubject(subject);
            message.setText(textMessage);

            //send the message
            Transport.send(message);
            System.out.println("message sent successfully...");
        } catch (MessagingException e) {e.printStackTrace();}
    }
}
