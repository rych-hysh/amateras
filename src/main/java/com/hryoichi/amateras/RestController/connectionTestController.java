package com.hryoichi.amateras.RestController;

import com.hryoichi.amateras.Services.LINEMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class connectionTestController {

    @Autowired
    LINEMessagingService lineMessagingService;
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

    @GetMapping("/line")
    void lineSendTest() {
        lineMessagingService.sendTestMessage();
    }

}
