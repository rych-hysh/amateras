package com.hryoichi.amateras.Services;

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
    public Iterable<Rates> getAllRates() {
        return ratesRepository.findAll();
    }
    public float getLatestRate(){return ratesRepository.getLatest().getAskPrice();}

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
        HashMap<String, List<Float>> averageAndSigmas = calcAveragesAndSigmas(nForSigma, numOfDataInBar, closes);
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

    HashMap<String, List<Float>> calcAveragesAndSigmas(int n, int numOfDataInBar, List<Float> closes){
        List<Float> sigmas = new ArrayList<>();
        List<Float> averages = new ArrayList<>();
        for(int i = n - 1; i < closes.size(); i++){
            // subList not includes index of (i + 1)
            List<Float> target = closes.subList(i-(n-1), i + 1);

            Float average = (float) target.stream().mapToDouble(rate -> rate).average().orElse(0);
            Float sigma = (float) Math.sqrt(( n * target.stream().map(rate -> Math.pow(rate, 2)).mapToDouble(powedRate -> powedRate).sum() - Math.pow(target.stream().mapToDouble(rate -> rate).sum(), 2))/(n * (n - 1)));

            averages.add(average);
            sigmas.add(sigma);
        }
        HashMap<String, List<Float>> resultHashMap = new HashMap<>();
        resultHashMap.put("averages", averages);
        resultHashMap.put("sigmas", sigmas);
        return resultHashMap;
    }

    @Scheduled(cron = "0 */5 * * * *", zone = "Asia/Tokyo")
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