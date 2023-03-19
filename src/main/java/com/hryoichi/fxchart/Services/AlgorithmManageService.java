package com.hryoichi.fxchart.Services;

import com.hryoichi.fxchart.Algorythms.AbstractAlgorithm;
import com.hryoichi.fxchart.Algorythms.Algorithms.SampleAlgorithm;
import com.hryoichi.fxchart.Entities.Positions;
import com.hryoichi.fxchart.Entities.RunningAlgorithms;
import com.hryoichi.fxchart.Entities.Simulators;
import com.hryoichi.fxchart.Events.AlgorithmCheck;
import com.hryoichi.fxchart.Models.AlgorithmResult;
import com.hryoichi.fxchart.Repositories.PositionsRepository;
import com.hryoichi.fxchart.Repositories.RatesRepository;
import com.hryoichi.fxchart.Repositories.RunningAlgorithmsRepository;
import com.hryoichi.fxchart.Repositories.SimulatorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AlgorithmManageService {
    @Autowired
    SimulatorsRepository simulatorsRepository;
    @Autowired
    RunningAlgorithmsRepository runningAlgorithmsRepository;
    @Autowired
    PositionsRepository positionsRepository;
    @Autowired
    RatesService ratesService;
    @Autowired
    SampleAlgorithm sampleAlgorithm;

    @EventListener
    @Async
    public void onRateUpdated(AlgorithmCheck event){
        final var res = event.check();
        // TODO: Repositoryを直接叩かずServiceを利用する
        List<Simulators> runningSimulatorList = simulatorsRepository.getRunningSimulators();
        runningSimulatorList.forEach(simulator ->{
            List<Integer> subscribedAlgorithmIdList = runningAlgorithmsRepository.getSubscribedAlgorithmsIdBySimulatorId(simulator.getId());
            subscribedAlgorithmIdList.forEach(algorithmId ->{
                AbstractAlgorithm algorithm = getAlgorithmById(algorithmId);
                AlgorithmResult result = algorithm.checkAlgorithmBySimulatorId(simulator.getId());
                if(!result.isDoOrder()){
                    return;
                }
                Positions order;
                if(result.isSettle()){
                    order = new Positions(result.getSettlePositionId(), "USD/JPY", simulator.getId(), result.isAsk(), ratesService.getLatestRate(), result.getLots(), algorithmId, new Date(), true);
                }else{
                    order = new Positions("USD/JPY", simulator.getId(), result.isAsk(), ratesService.getLatestRate(), result.getLots(), algorithmId, new Date(), false);
                }
                positionsRepository.save(order);
//                if(simulatorId.isRequireNotice){
//                    LINEMessage.send( to simulator.getUserUuid)
//                }
            });
        });


    }

    private AbstractAlgorithm getAlgorithmById(int algorithmId){
        switch (algorithmId){
            case SampleAlgorithm.ID:
                return sampleAlgorithm;
            case 1:
                return sampleAlgorithm;
            case 2:
                return sampleAlgorithm;
            case 3:
                return sampleAlgorithm;
            case 4:
                return sampleAlgorithm;
            case 5:
                return sampleAlgorithm;
            case 6:
                return sampleAlgorithm;
            default:
                return null;
        }
    }
}
