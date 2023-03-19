package com.hryoichi.fxchart.Algorythms.Algorithms;

import com.hryoichi.fxchart.Algorythms.AbstractAlgorithm;
import com.hryoichi.fxchart.Entities.Positions;
import com.hryoichi.fxchart.Entities.Simulators;
import com.hryoichi.fxchart.Models.AlgorithmResult;
import com.hryoichi.fxchart.Repositories.PositionsRepository;
import com.hryoichi.fxchart.Repositories.RatesRepository;
import com.hryoichi.fxchart.Services.RatesService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class SampleAlgorithm extends AbstractAlgorithm {
    @Autowired
    PositionsRepository positionsRepository;
    @Autowired
    RatesService ratesService;
    static public final int ID = 0;
    protected void placeTradeNow(int simulatorId, int lots){
        float latestRate = ratesService.getLatestRate();
        positionsRepository.save(new Positions("USD/JPY", 0, true, latestRate, lots, ID, new Date(), false));
    };
    protected void closePositionNow(int simulatorId, int lots){
    };
    public AlgorithmResult checkAlgorithm(){
        return new AlgorithmResult(true, true, 0, "USD/JPY");
    };

    public AlgorithmResult checkAlgorithmBySimulatorId(int simulatorId){
        List<Positions> positionList = positionsRepository.getPositionsBySimulatorId(simulatorId);
        if(positionList.isEmpty()){
            placeTradeNow(0, 1);
        }else{
            closePositionNow(0, 1);
        }
        return new AlgorithmResult(false, false, 0, "USD/JPY");
    };
}
