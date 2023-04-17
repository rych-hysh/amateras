package com.hryoichi.amateras.RestController;

import com.hryoichi.amateras.Dtos.RatesForCandleChartDto;
import com.hryoichi.amateras.Entities.Rates;
import com.hryoichi.amateras.Services.RatesService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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

    @GetMapping("candle")
    public @ResponseBody List<RatesForCandleChartDto> getRatesForCandleDto(@RequestParam(name = "endingDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endingDate, @RequestParam(name = "numOfBar") int numOfBar, @RequestParam(name = "dataInBar") int dataInBar) {
        return ratesService.getRateForCandleChartDtoList(endingDate, numOfBar, dataInBar);
    }

    @GetMapping("/collect")
    public @ResponseBody void cll(){

        ratesService.collectCurrentUSD_JPY();

    }

}