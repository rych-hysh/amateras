package com.hryoichi.amateras.RestController;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hryoichi.amateras.Entities.Rates;
import com.hryoichi.amateras.Services.RatesService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/rates")
public class RatesRestController {
    @Autowired
    RatesService ratesService;
    Object res;
    @GetMapping("/all")
    public @ResponseBody Iterable<Rates> getAllRates(){
        return ratesService.getAllRates();
    }

    @GetMapping("/collect")
    public @ResponseBody void cll(){

        ratesService.collectCurrentUSD_JPY();

    }

}
