package com.hryoichi.amateras.Algorythms.Algorithms;

import com.hryoichi.amateras.Algorythms.AbstractAlgorithm;
import com.hryoichi.amateras.Entities.Positions;
import com.hryoichi.amateras.Models.AlgorithmResult;
import com.hryoichi.amateras.Repositories.PositionsRepository;
import com.hryoichi.amateras.Services.RatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        // positionsRepository.save(new Positions("USE/JPY", simulatorId, true, ...));
    }
    protected void closePositionNow(int simulatorId, int lots){
    }
    public AlgorithmResult checkAlgorithm(){
        return new AlgorithmResult("USD/JPY", true, true, 0,100f, false, 0);
    }

    public AlgorithmResult checkAlgorithmBySimulatorId(int simulatorId){
        List<Positions> positionList = positionsRepository.getOpenedPositionsBySimulatorId(simulatorId);
        // TODO: AlgorithmResultの返し方を考え直すこと
        if(!positionList.isEmpty()){
            if ( Math.abs(positionList.get(0).getGotRate() - ratesService.getLatestRate()) > 0.05) {
                return new AlgorithmResult("USD/JPY", true, true, 1, ratesService.getLatestRate(), true, positionList.get(0).getId());
            }
        }else{
            return new AlgorithmResult("USD/JPY", true, true, 1, ratesService.getLatestRate(),  false, 0);

        }
        return new AlgorithmResult("", false, true, null, null, true, null);
    }
}
