package com.hryoichi.amateras.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    public List<RatesForCandleChartDto> getRateForCandleChartDtoList(LocalDateTime endingDate, int numOfBar, int numOfDataInBar){ return getRateForCandleChartDtoList(endingDate, numOfBar, numOfDataInBar, 20);}

    public List<RatesForCandleChartDto> getRateForCandleChartDtoList(LocalDateTime endingDate, int numOfBar, int numOfDataInBar, int nForSigma){
        // TODO: nForSigmaがnumOfBarより小さい場合の例外処理
        // TODO: nForSigma, numOfBar, numOfDataInBar の関係を調べて例外処理
        // sigmaの計算のためにnForSigma本前のデータから取得する。
        int numOfData = numOfDataInBar * (numOfBar + nForSigma - 1);
        List<Rates> ratesFromRepository = ratesRepository.getRatesBeforeDate(endingDate, numOfData);
        //ratesRepository.getRatesAfterDate()
        //TODO: ratesFromRepository is possibly null.
        int gotMinutes =ratesFromRepository.stream().findFirst().get().getDate().getMinute();
        // 当面は1時間足のみ
        // データの先頭を時間足の先頭にする処理
//        while(!(gotMinutes == 0 || gotMinutes == 59)){
//            ratesFromRepository.remove(0);
//            gotMinutes = ratesFromRepository.stream().findFirst().get().getDate().getMinute();
//        }

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
            Float close = askRates.get(numOfDataInBar - 1);
            closes.add(close);
            Float high = askRates.stream().max(Comparator.naturalOrder()).orElse((float) 0);
            Float low = askRates.stream().min(Comparator.naturalOrder()).orElse((float) 0);
            RatesForCandleChartDto rateForCandleChartDto = new RatesForCandleChartDto();
            rateForCandleChartDto.setOpen(open);
            rateForCandleChartDto.setClose(close);
            rateForCandleChartDto.setHigh(high);
            rateForCandleChartDto.setLow(low);
            // TODO: Add date
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

    // TODO: Move to Alpha Vantage Client(not created yet)
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