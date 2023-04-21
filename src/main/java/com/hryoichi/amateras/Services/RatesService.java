package com.hryoichi.amateras.Services;

import com.hryoichi.amateras.Configs;
import com.hryoichi.amateras.Dtos.RatesForCandleChartDto;
import com.hryoichi.amateras.Clients.AlphaVantageClient;
import com.hryoichi.amateras.Entities.Rates;
import com.hryoichi.amateras.Events.Publisher.RatesUpdatedPublisher;
import com.hryoichi.amateras.Repositories.RatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;
@Service
public class RatesService {
    @Autowired
    RatesRepository ratesRepository;
    @Autowired
    AlphaVantageClient alphaVantageClient;
    @Autowired
    RatesUpdatedPublisher ratesUpdatedPublisher;
    @Autowired
    AnalyzeService analyzeService;
    public Iterable<Rates> getAllRates() {
        return ratesRepository.findAll();
    }
    public float getLatestRate(){return ratesRepository.getLatest().getAskPrice();}
    public List<Float> getLatestRate(int limit){return ratesRepository.getLatest(limit).stream().map(Rates::getAskPrice).toList();}

    public List<RatesForCandleChartDto> getRateForCandleChartDtoList(LocalDateTime endingDate, int numOfBar, int numOfDataInBar){ return getRateForCandleChartDtoList(endingDate, numOfBar, numOfDataInBar, 20);}

    public List<RatesForCandleChartDto> getRateForCandleChartDtoList(LocalDateTime endingDate, int numOfBar, int numOfDataInBar, int nForSigma){
        // TODO: nForSigmaがnumOfBarより小さい場合の例外処理
        // TODO: nForSigma, numOfBar, numOfDataInBar の関係を調べて例外処理
        // sigmaの計算のためにnForSigma-1本前のデータから取得する。
        int numOfData = numOfDataInBar * (numOfBar + nForSigma - 1);
        List<Rates> ratesFromRepository = ratesRepository.getRatesBeforeDate(endingDate, numOfData);
        //ratesRepository.getRatesAfterDate()
        //TODO: ratesFromRepository is possibly null.
        int gotMinutes =ratesFromRepository.stream().findFirst().get().getDate().getMinute();
        // 当面は1時間足のみ
        // データの先頭を時間足の先頭にする処理
        while(!(gotMinutes == 0 || gotMinutes == 59)){
            ratesFromRepository.remove(0);
            gotMinutes = ratesFromRepository.stream().findFirst().get().getDate().getMinute();
        }

        List<RatesForCandleChartDto> result = new ArrayList<>();
        // ローソク一本分のデータ毎に取得して最小値や最大値の計算
        List<Float> closes = new ArrayList<>();
        for (int i = 0; i * numOfDataInBar < ratesFromRepository.size(); i++){
            List<Rates> dataInBar;
            if(!((i+1)*numOfDataInBar > ratesFromRepository.size())){
                dataInBar = ratesFromRepository.subList(i * numOfDataInBar, (i + 1) * numOfDataInBar);
            }else{
                dataInBar = ratesFromRepository.subList(i*numOfDataInBar, ratesFromRepository.size());
            }
            List<Float> askRates = dataInBar.stream().map(Rates::getAskPrice).toList();
            Float open = askRates.stream().findFirst().orElse((float) 0);
            Float close = askRates.get(dataInBar.size() - 1);
            closes.add(close);
            Float high = askRates.stream().max(Comparator.naturalOrder()).orElse((float) 0);
            Float low = askRates.stream().min(Comparator.naturalOrder()).orElse((float) 0);
            RatesForCandleChartDto rateForCandleChartDto = new RatesForCandleChartDto();
            rateForCandleChartDto.setOpen(open);
            rateForCandleChartDto.setClose(close);
            rateForCandleChartDto.setHigh(high);
            rateForCandleChartDto.setLow(low);
            rateForCandleChartDto.setDate(dataInBar.get(0).getDate());
            result.add(rateForCandleChartDto);
        }
        HashMap<String, List<Float>> averageAndSigmas = analyzeService.calcAveragesAndSigmas(nForSigma, closes);
        // 移動平均線や標準偏差（ボリンジャーバンド）の計算のために余分に取得したデータを削除
        result.subList(0, nForSigma-1).clear();
        for (int j = 0; j < averageAndSigmas.get("averages").size(); j++){
            Float ave = averageAndSigmas.get("averages").get(j);
            Float sig = averageAndSigmas.get("sigmas").get(j);
            result.get(j).setAve(ave);
            result.get(j).setSigma_high(ave + sig);
            result.get(j).setSigma_low(ave - sig);
        }
        return result;
    }

    public List<Float> getCloses(int numOfDataInBar, List<Rates> data){
        // 当面は1時間足のみ
        // ５分間隔でデータ収集なら55 || 54
        // 3分間隔でデータ収集なら57 || 56
        return data.stream().filter(datum -> datum.getDate().getMinute() == 60 - Configs.GLOBAL_ALPHA_VANTAGE_COLLECT_EACH_MINUTES || datum.getDate().getMinute() == 60 - Configs.GLOBAL_ALPHA_VANTAGE_COLLECT_EACH_MINUTES - 1).map(Rates::getAskPrice).toList();
    }

    @Scheduled(cron = "0 */" + Configs.GLOBAL_ALPHA_VANTAGE_COLLECT_EACH_MINUTES + " * * * *", zone = "Asia/Tokyo")
    public void collectCurrentUSD_JPY() {
        LocalDateTime now = LocalDateTime.now();
        if(now.getDayOfWeek()  == DayOfWeek.SUNDAY) return;
        if(now.getDayOfWeek() == DayOfWeek.SATURDAY && now.getHour() >= 5 ) return;
        if(now.getDayOfWeek() == DayOfWeek.MONDAY && now.getHour() <= 5) return;
        Rates currentRate = alphaVantageClient.getCurrentRate();
        ratesRepository.save(currentRate);
        ratesUpdatedPublisher.Updated();
    }
}