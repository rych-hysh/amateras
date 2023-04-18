package com.hryoichi.amateras.RestController;

import com.hryoichi.amateras.Dtos.RatesForCandleChartDto;
import com.hryoichi.amateras.Entities.Rates;
import com.hryoichi.amateras.Services.RatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/rates")
public class RatesRestController {
    @Autowired
    RatesService ratesService;
    @GetMapping("/all")
    @CrossOrigin
    public @ResponseBody Iterable<Rates> getAllRates(){
        return ratesService.getAllRates();
    }

    @GetMapping("candle")
    @CrossOrigin
    public @ResponseBody List<RatesForCandleChartDto> getRatesForCandleDto(@RequestParam(name = "endingDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> endingDate, @RequestParam(name = "numOfBar") int numOfBar, @RequestParam(name = "dataInBar") int dataInBar, @RequestParam(name = "nForSigma", required = false) Optional<Integer> nForSigma) {
        Integer n = nForSigma.orElse(20);
        LocalDateTime date = endingDate.orElse(LocalDateTime.now());
        return ratesService.getRateForCandleChartDtoList(date, numOfBar, dataInBar, n);
    }

    @GetMapping("/collect")
    @CrossOrigin
    public @ResponseBody void cll(){

        ratesService.collectCurrentUSD_JPY();

    }

}