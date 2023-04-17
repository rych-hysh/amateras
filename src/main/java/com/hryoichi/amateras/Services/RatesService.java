package com.hryoichi.amateras.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hryoichi.amateras.Dtos.RateListForCandleChartDto;
import com.hryoichi.amateras.Dtos.RatesForCandleChartDto;
import com.hryoichi.amateras.Entities.Rates;
import com.hryoichi.amateras.Events.Publisher.RatesUpdatedPublisher;
import com.hryoichi.amateras.Repositories.RatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class RatesService {
    @Autowired
    RatesRepository ratesRepository;
    @Value("${alpha-vantage.api-key}")
    private String AlphaVantageApiKey;
    @Value("${alpha-vantage.api-host}")
    private String AlphaVantageApiHost;
    RestTemplate restTemplate = new RestTemplate();
    @Autowired
    RatesUpdatedPublisher ratesUpdatedPublisher;
    public Iterable<Rates> getAllRates() {
        return ratesRepository.findAll();
    }
    public float getLatestRate(){return ratesRepository.getLatest().getAskPrice();}

    public List<RatesForCandleChartDto> getRateForCandleChartDtoList(LocalDateTime endingDate, int numOfBar, int dataInBar){
        int numOfData = dataInBar * numOfBar;

        //endingDate = endingDate.minusHours(48);
        List<Rates> ratesFromRepository = ratesRepository.getRatesBeforeDate(endingDate, numOfData);
        //ratesRepository.getRatesAfterDate()
        //TODO: ratesFromRepository is possibly null.
        int gotMinutes =ratesFromRepository.stream().findFirst().get().getDate().getMinute();
        while(!(gotMinutes == 00 || gotMinutes == 59)){
            ratesFromRepository.remove(0);
            gotMinutes =ratesFromRepository.stream().findFirst().get().getDate().getMinute()
        }
        for(int i = 0; i < ratesFromRepository.size(); i++){
            if(){
                ratesFromRepository.remove(i);
                i++;
            }
        }
        var v = ratesFromRepository;
        return new ArrayList<>();
    }

    // TODO: Port to Alpha Vantage Client(not created yet)
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
            ratesUpdatedPublisher.Updated();
        }catch(Exception error){
            final var s = error.toString();
        }
    }
}