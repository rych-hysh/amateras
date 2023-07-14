package com.hryoichi.amateras.Clients;

import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Component
public class AwsSesClient {
  public void sendEmail(String to, String subject, String body) throws UnsupportedEncodingException, MessagingException {
    final String FROM = "ryo.robolabo@gmail.com";
    final String FROMNAME = "ryoichi";

    final String SMTP_USER_NAME = "AKIA4HZTYCVX6BKGIW4X";
    final String SMTP_PASSWORD = "BFHoCwdSVAYMI2KHPWBEnJ3RBgwnBPzd9NxGFf8YD2OZ";

    final String HOST = "email-smtp.us-east-1.amazonaws.com";

    final int PORT = 587;

    Properties props = System.getProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.port", PORT);
    props.put("mail.smtp.starttls.enable", "true");
    props.put("main.smtp.auth", "true");

    Session session = Session.getDefaultInstance(props);

    MimeMessage msg = new MimeMessage(session);
    msg.setFrom(new InternetAddress(FROM, FROMNAME));
    msg.setRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(to)));
    msg.setSubject(subject);
    msg.setContent(body, "text/html");

    Transport transport = session.getTransport();

    try{
      System.out.println("Sending...");

      transport.connect(HOST, SMTP_USER_NAME, SMTP_PASSWORD);

      transport.sendMessage(msg, msg.getAllRecipients());

      System.out.println("Email sent");
    } catch (Exception e){
      System.out.println("error");
    } finally {
      transport.close();
    }


  }
}
