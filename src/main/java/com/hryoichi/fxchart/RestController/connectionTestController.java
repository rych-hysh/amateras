package com.hryoichi.fxchart.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class connectionTestController {


    @GetMapping("/connection")
    public String connectionTest(){
        return "connected.";
    }
}
