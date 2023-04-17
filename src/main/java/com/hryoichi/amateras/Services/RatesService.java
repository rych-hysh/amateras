package com.hryoichi.amateras.Services;

import com.hryoichi.amateras.Clients.AlphaVantageClient;
import com.hryoichi.amateras.Entities.Rates;
import com.hryoichi.amateras.Events.Publisher.RatesUpdatedPublisher;
import com.hryoichi.amateras.Repositories.RatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class RatesService {
    @Autowired
    RatesRepository ratesRepository;
    @Autowired
    AlphaVantageClient alphaVantageClient;
    @Autowired
    RatesUpdatedPublisher ratesUpdatedPublisher;
    public Iterable<Rates> getAllRates() {
        return ratesRepository.findAll();
    }
    public float getLatestRate(){return ratesRepository.getLatest().getAskPrice();}

    @Scheduled(cron = "0 */5 * * * *", zone = "Asia/Tokyo")
    public void collectCurrentUSD_JPY() {
        Rates currentRate = alphaVantageClient.getCurrentRate();
        ratesRepository.save(currentRate);
        ratesUpdatedPublisher.Updated();
    }
}