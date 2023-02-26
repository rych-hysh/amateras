package com.hryoichi.fxchart.RestController;

import com.hryoichi.fxchart.Entities.Rates;
import com.hryoichi.fxchart.Repositories.RateRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RatesRestController {
    private RateRepository rateRepository;
    @GetMapping("/rates/all")
    public @ResponseBody Iterable<Rates> getAllRates(){
        return rateRepository.findAll();
    }
}
