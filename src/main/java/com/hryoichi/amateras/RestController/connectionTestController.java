package com.hryoichi.amateras.RestController;

import com.hryoichi.amateras.Entities.User;
import com.hryoichi.amateras.Repositories.UserRepository;
import com.hryoichi.amateras.Services.EmailService;
import com.hryoichi.amateras.Services.LINEMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("test")
public class connectionTestController {

    @Autowired
    LINEMessagingService lineMessagingService;
    @Autowired
    EmailService emailService;
    @Autowired
    UserRepository userRepository;
    @Value("${spring.profiles.active}")
    String prof;

    @Value("${test}")
    String test;



    @GetMapping("/connection")
    String connectionTest(){
        return "connected.";
    }
    @GetMapping("/profile")
    String profileTest(){
        return prof;
    }
    @GetMapping("/test")
    String testTest(){
        return test;
    }
    @GetMapping("/user")
    String getUserEmail(){
        return userRepository.getEmailById(0);
    }
    @GetMapping("/user/add/{name}")
    String addUser(@PathVariable String name){
        //userRepository.addUser(name, "ryo.robolabo@gmail.com", UUID.randomUUID());
        userRepository.save(new User(name, "ryo.robolabo@gmail.com", UUID.randomUUID()));
        return "ok";
    }

    @GetMapping("/line")
    void lineSendTest() {
        lineMessagingService.sendTestMessage();
    }
    @GetMapping("/mail")
    String mailSendTest(){
        try{
            emailService.sendEmail("ryo.robolabo@gmail.com");
            return "success";
        } catch (Exception e){
            return e.getMessage().toString();
        }
    }

}
