package com.hryoichi.amateras.Services;

import com.hryoichi.amateras.Clients.AwsSesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class EmailService {
  @Autowired
  AwsSesClient awsSesClient;
  @Autowired
  MessageSource messageSource;


  public void sendEmail(String to) {
    String[] arg = {"simulator1", "USD/JPY", "Ask", "New order", "130", "2"};
    to = "ryo.robolabo@gmail.com";
    String subject = messageSource.getMessage("simulator.notice.new-trade.subject", null, Locale.JAPANESE);
    String message = messageSource.getMessage("simulator.notice.new-trade.body", arg, Locale.JAPANESE);
    try{
      awsSesClient.sendEmail(to, subject, message);
    } catch (UnsupportedEncodingException | MessagingException e){
      return;
    }
  }

  public void sendNewTradeNotificationEmail(String to){
    String[] arg = {"simulator1", "USD/JPY", "Ask", "New order", "130", "2"};
    to = "ryo.robolabo@gmail.com";

    String subject = messageSource.getMessage("simulator.notice.new-trade.subject", null, Locale.JAPANESE);
    String message = messageSource.getMessage("simulator.notice.new-trade.body", arg, Locale.JAPANESE);
    try{
      awsSesClient.sendEmail(to, subject, message);
    } catch (UnsupportedEncodingException | MessagingException e){
      return;
    }
  }

  public void sendNewTradeNotificationEmailToSubscriber(int algorithmId, String to){
    String[] arg = {"simulator1", "USD/JPY", "Ask", "New order", "130", "2"};
    to = "ryo.robolabo@gmail.com";

    String subject = messageSource.getMessage("simulator.notice.new-trade.subject", null, Locale.JAPANESE);
    String message = messageSource.getMessage("simulator.notice.new-trade.body", arg, Locale.JAPANESE);
    try{
      awsSesClient.sendEmail(to, subject, message);
    } catch (UnsupportedEncodingException | MessagingException e){
      return;
    }
  }

}
