package com.hryoichi.amateras.Algorythms.Algorithms;

import com.hryoichi.amateras.Algorythms.AbstractAlgorithm;
import com.hryoichi.amateras.Models.AlgorithmResult;
import com.hryoichi.amateras.Services.AnalyzeService;
import com.hryoichi.amateras.Services.PositionsService;
import com.hryoichi.amateras.Services.RatesService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OandaShortTermDayTradingAlgorithm extends AbstractAlgorithm {

  @Autowired
  AnalyzeService analyzeService;
  @Autowired
  RatesService ratesService;
  @Autowired
  PositionsService positionsService;
  @Override
  protected void placeTradeNow(int simulatorId, int lots) {

  }

  @Override
  protected void closePositionNow(int simulatorId, int lots) {

  }

  @Override
  public AlgorithmResult checkAlgorithmBySimulatorId(int simulatorId) {
    int averageWindow = 20;
    int wantAverageCount = 20;
    //　終値の取得をしたい

    List<Float> latestRates = ratesService.getLatestRate(averageWindow + wantAverageCount);
    // latestRatesじゃなくて終値を渡したい
    List<Float> averages = analyzeService.calcAverages(averageWindow, latestRates);
    if(positionsService.getOpenedPositionsBySimulatorId(simulatorId).isEmpty()){

    }

    return null;
  }
}
