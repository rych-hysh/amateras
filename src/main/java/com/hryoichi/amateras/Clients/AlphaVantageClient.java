package com.hryoichi.amateras.Clients;

import com.hryoichi.amateras.Entities.Rates;
import com.hryoichi.amateras.Enums.CurrencyPairEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Component
public class AlphaVantageClient{
    @Value("${alpha-vantage.api-key}")
    String AlphaVantageApiKey;
    @Value("${alpha-vantage.api-host}")
    String AlphaVantageApiHost;

    final String baseUrl;
    final String queriedUrl;
    public AlphaVantageClient(){
        baseUrl = "https://" + "alpha-vantage.p.rapidapi.com";
        queriedUrl = baseUrl + "/query?";

    }

    public Rates getCurrentRate() { return getCurrentRate(CurrencyPairEnum.USD_JPY); }
    public Rates getCurrentRate(CurrencyPairEnum pair) {
        return getCurrentRate(pair.getToCurrency(), pair.getFromCurrency());
    }
    public Rates getCurrentRate(String toCurrency, String fromCurrency) {
        HashMap<String, String> queryParam = new HashMap<>();
        queryParam.put("to_currency", toCurrency);
        queryParam.put("from_currency", fromCurrency);
        queryParam.put("function", "CURRENCY_EXCHANGE_RATE");
        String requestUrl = generateRequestUrl(queryParam);

        Rates currentRate = new Rates();
        try{
            LinkedHashMap<String, String> result = doRequest(requestUrl).get("Realtime Currency Exchange Rate");
            currentRate.setBidPrice(Float.parseFloat(result.get("8. Bid Price")));
            currentRate.setAskPrice(Float.parseFloat(result.get("9. Ask Price")));

            DateTimeFormatter dtFt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime date = LocalDateTime.parse(result.get("6. Last Refreshed"), dtFt);
            date = date.plusHours(9);
            currentRate.setDate(date);
        }catch(Error e){
            System.out.println(e);
            //TODO: Error handling
        }
        return currentRate;
    }

    LinkedHashMap<String, LinkedHashMap> doRequest(String requestUrl){
        RequestEntity.BodyBuilder builder = (RequestEntity.BodyBuilder) RequestEntity.get(requestUrl);
        builder.header("X-RapidAPI-Key", AlphaVantageApiKey);
        builder.header("X-RapidAPI-Host", AlphaVantageApiHost);
        RequestEntity request = builder.build();
        RestTemplate restTemplate = new RestTemplate();
        LinkedHashMap<String, LinkedHashMap> result = new LinkedHashMap<>();
        try{
            ResponseEntity response = restTemplate.exchange(request, LinkedHashMap.class);
            result = (LinkedHashMap) response.getBody();
        }catch(Exception error){
            System.out.println(error);
            //TODO: Error handling
        }
        return result;
    }

    String generateRequestUrl(HashMap<String, String> queryParam){
        StringBuilder requestUrlBase = new StringBuilder(queriedUrl);
        for (String key : queryParam.keySet()){
            if(!requestUrlBase.toString().equals(queriedUrl)) requestUrlBase.append("&");
            requestUrlBase.append(key).append("=").append(queryParam.get(key));
        }
        return requestUrlBase.toString();
    }
}
