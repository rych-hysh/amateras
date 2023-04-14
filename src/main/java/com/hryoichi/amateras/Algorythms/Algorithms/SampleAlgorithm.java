package com.hryoichi.amateras.Algorythms.Algorithms;

import com.hryoichi.amateras.Algorythms.AbstractAlgorithm;
import com.hryoichi.amateras.Entities.Positions;
import com.hryoichi.amateras.Entities.Simulators;
import com.hryoichi.amateras.Models.AlgorithmResult;
import com.hryoichi.amateras.Repositories.PositionsRepository;
import com.hryoichi.amateras.Repositories.RatesRepository;
import com.hryoichi.amateras.Services.RatesService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class SampleAlgorithm extends AbstractAlgorithm {
    @Autowired
    PositionsRepository positionsRepository;
    @Autowired
    RatesService ratesService;
    static public final int ID = 0;
    static public final String name = "Sample Algorithm";
    protected void placeTradeNow(int simulatorId, int lots){
        float latestRate = ratesService.getLatestRate();
        positionsRepository.save(new Positions("USD/JPY", 0, true, latestRate, lots, ID, new Date(), false));
    };
    protected void closePositionNow(int simulatorId, int lots){
    };
    public AlgorithmResult checkAlgorithm(){
        return new AlgorithmResult("USD/JPY", true, true, 0, false, 0);
    };

    public AlgorithmResult checkAlgorithmBySimulatorId(int simulatorId){
        List<Positions> positionList = positionsRepository.getPositionsBySimulatorId(simulatorId);
        if(positionList.isEmpty()){
            return new AlgorithmResult("USD/JPY", true, true, 1, false, 0);
        }else{
            return new AlgorithmResult("USD/JPY", true, true, 1, true, positionList.get(0).getId());
        }
    };
}
