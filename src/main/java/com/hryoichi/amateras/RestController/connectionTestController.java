package com.hryoichi.amateras.RestController;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class connectionTestController {

    @Value("${spring.profiles.active}")
    private String prof;

    @Value("${test}")
    private String test;

    @GetMapping("/connection")
    public String connectionTest(){
        return "connected.";
    }
    @GetMapping("/profile")
    public String profileTest(){
        return prof;
    }
    @GetMapping("/test")
    public String testTest(){
        return test;
    }

}
