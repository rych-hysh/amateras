package com.hryoichi.fxchart.Services;

import com.hryoichi.fxchart.Algorythms.AbstractAlgorithm;
import com.hryoichi.fxchart.Algorythms.Algorithms.SampleAlgorithm;
import com.hryoichi.fxchart.Entities.Positions;
import com.hryoichi.fxchart.Entities.RunningAlgorithms;
import com.hryoichi.fxchart.Entities.Simulators;
import com.hryoichi.fxchart.Events.AlgorithmCheck;
import com.hryoichi.fxchart.Models.AlgorithmResult;
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
    RatesRepository ratesRepository;
    @EventListener
    @Async
    public void onRateUpdated(AlgorithmCheck event){
        final var res = event.check();
        List<Simulators> runningSimulatorList = simulatorsRepository.getRunningSimulators();
        runningSimulatorList.forEach(simulator ->{
            List<Integer> subscribedAlgorithmIdList = runningAlgorithmsRepository.getSubscribedAlgorithmsIdBySimulatorId(simulator.getId());
            subscribedAlgorithmIdList.forEach(algorithmId ->{
                AbstractAlgorithm algorithm = getAlgorithmById(algorithmId);
                AlgorithmResult result = algorithm.checkAlgorithmBySimulatorId(simulator.getId());
                if(!result.isDoOrder()){
                    return;
                }
                Positions order = new Positions("USD/JPY", simulator.getId(), result.isAsk(), ratesRepository.getLatest().getAskPrice(), result.getLots(), algorithmId, new Date(), false);
//                if(simulatorId.isRequireNotice){
//                    LINEMessage.send( to simulator.getUserUuid)
//                }
            });
        });


    }

    private AbstractAlgorithm getAlgorithmById(int algorithmId){
        switch (algorithmId){
            case SampleAlgorithm.ID:
                return new SampleAlgorithm();
            case 1:
                return new SampleAlgorithm();
            case 2:
                return new SampleAlgorithm();
            case 3:
                return new SampleAlgorithm();
            case 4:
                return new SampleAlgorithm();
            case 5:
                return new SampleAlgorithm();
            case 6:
                return new SampleAlgorithm();
            default:
                return null;
        }
    }
}
