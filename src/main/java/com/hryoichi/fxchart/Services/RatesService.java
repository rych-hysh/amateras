package com.hryoichi.fxchart.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hryoichi.fxchart.Entities.Rates;
import com.hryoichi.fxchart.Repositories.RatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Service
public class RatesService {
    @Autowired
    private RatesRepository ratesRepository;
    @Value("${alpha-vantage.api-key}")
    private String AlphaVantageApiKey;
    @Value("${alpha-vantage.api-host}")
    private String AlphaVantageApiHost;
    RestTemplate restTemplate = new RestTemplate();

    public Iterable<Rates> getAllRates() {
        return ratesRepository.findAll();
    }


    @Scheduled(cron = "0 */5 * * * *", zone = "Asia/Tokyo")
    public void collectCurrentUSD_JPY() {
        ObjectMapper mapper = new ObjectMapper();
        final var url = "https://alpha-vantage.p.rapidapi.com/query?to_currency=JPY&function=CURRENCY_EXCHANGE_RATE&from_currency=USD";
        RequestEntity.BodyBuilder builder = (RequestEntity.BodyBuilder) RequestEntity.get(url);
        builder.header("X-RapidAPI-Key", AlphaVantageApiKey);
        builder.header("X-RapidAPI-Host", AlphaVantageApiHost);
        RequestEntity request = builder.build();
        try{
            ResponseEntity<LinkedHashMap> response = restTemplate.exchange(request, LinkedHashMap.class);
            LinkedHashMap<String, LinkedHashMap> res = response.getBody();
            LinkedHashMap<String, String> responseObj = res.get("Realtime Currency Exchange Rate");
            Rates currentRate = new Rates();
            currentRate.setBidPrice(Float.parseFloat(responseObj.get("8. Bid Price")));
            currentRate.setAskPrice(Float.parseFloat(responseObj.get("9. Ask Price")));

            DateTimeFormatter dtFt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime date = LocalDateTime.parse((CharSequence) responseObj.get("6. Last Refreshed"), dtFt);
            date = date.plusHours(9);
            currentRate.setDate(date);
            ratesRepository.save(currentRate);
        }catch(Exception error){

        }
    }
}