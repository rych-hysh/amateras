package com.hryoichi.amateras.RestController;

import com.hryoichi.amateras.Entities.Rates;
import com.hryoichi.amateras.Services.RatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rates")
public class RatesRestController {
    @Autowired
    RatesService ratesService;
    @GetMapping("/all")
    public @ResponseBody Iterable<Rates> getAllRates(){
        return ratesService.getAllRates();
    }

    @GetMapping("/collect")
    public @ResponseBody void cll(){

        ratesService.collectCurrentUSD_JPY();

    }

}
