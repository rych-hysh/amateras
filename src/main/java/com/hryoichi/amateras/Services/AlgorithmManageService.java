package com.hryoichi.amateras.Services;

import com.hryoichi.amateras.Algorythms.AbstractAlgorithm;
import com.hryoichi.amateras.Algorythms.Algorithms.SampleAlgorithm;
import com.hryoichi.amateras.Entities.Positions;
import com.hryoichi.amateras.Entities.RunningAlgorithms;
import com.hryoichi.amateras.Entities.Simulators;
import com.hryoichi.amateras.Events.AlgorithmCheck;
import com.hryoichi.amateras.Models.AlgorithmResult;
import com.hryoichi.amateras.Repositories.PositionsRepository;
import com.hryoichi.amateras.Repositories.RunningAlgorithmsRepository;
import com.hryoichi.amateras.Repositories.SimulatorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

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
        // TODO: Repositoryを直接叩かずServiceを利用する
        List<Simulators> runningSimulatorList = simulatorsRepository.getRunningSimulators();
        runningSimulatorList.forEach(simulator ->{
            List<Integer> subscribedAlgorithmIdList = runningAlgorithmsRepository.getSubscribedAlgorithmsBySimulatorId(simulator.getId()).stream().map(RunningAlgorithms::getId).toList();
            subscribedAlgorithmIdList.forEach(algorithmId ->{
                AbstractAlgorithm algorithm = getAlgorithmById(algorithmId);
                if(Objects.isNull(algorithm))return;
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
        return switch (algorithmId) {
            case SampleAlgorithm.ID -> new SampleAlgorithm();
            case 1 -> sampleAlgorithm;
            case 2 -> sampleAlgorithm;
            case 3 -> sampleAlgorithm;
            case 4 -> sampleAlgorithm;
            case 5 -> sampleAlgorithm;
            case 6 -> sampleAlgorithm;
            default -> null;
        };
    }
}
