package com.hryoichi.amateras.Services;

import com.hryoichi.amateras.Algorythms.AbstractAlgorithm;
import com.hryoichi.amateras.Algorythms.Algorithms.SampleAlgorithm;
import com.hryoichi.amateras.Entities.Funds;
import com.hryoichi.amateras.Entities.Positions;
import com.hryoichi.amateras.Entities.RunningAlgorithms;
import com.hryoichi.amateras.Entities.Simulators;
import com.hryoichi.amateras.Events.AlgorithmCheck;
import com.hryoichi.amateras.Models.AlgorithmResult;
import com.hryoichi.amateras.Repositories.FundsRepository;
import com.hryoichi.amateras.Repositories.PositionsRepository;
import com.hryoichi.amateras.Repositories.RunningAlgorithmsRepository;
import com.hryoichi.amateras.Repositories.SimulatorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class OrderingService {
    @Autowired
    SimulatorsRepository simulatorsRepository;
    @Autowired
    RunningAlgorithmsRepository runningAlgorithmsRepository;
    @Autowired
    PositionsRepository positionsRepository;
    @Autowired
    FundsRepository fundsRepository;
    @Autowired
    RatesService ratesService;
    @Autowired
    SampleAlgorithm sampleAlgorithm;

    @EventListener
    public void onRateUpdated(AlgorithmCheck event){
        final var res = event.check();
        // TODO: Repositoryを直接叩かずServiceを利用する
        List<Simulators> runningSimulatorList = simulatorsRepository.getRunningSimulators();
        runningSimulatorList.forEach(simulator ->{
            List<Integer> subscribedAlgorithmIdList = runningAlgorithmsRepository.getSubscribedAlgorithmsBySimulatorId(simulator.getId()).stream().map(RunningAlgorithms::getAlgorithmId).toList();
            subscribedAlgorithmIdList.forEach(algorithmId ->{
                AbstractAlgorithm algorithm = getAlgorithmById(algorithmId);
                if(Objects.isNull(algorithm))return;
                AlgorithmResult result = algorithm.checkAlgorithmBySimulatorId(simulator.getId());
                if(!result.isDoOrder()){
                    return;
                }
                Positions order;
                float currentFunds = fundsRepository.getCurrentFundsBySimulatorId(simulator.getId()).orElse(1000000f);
                Funds updatedFunds;
                if(result.isSettle()){
                    float gotRate = positionsRepository.findById(result.getSettlePositionId()).orElseThrow().getGotRate();
                    order = new Positions(result.getSettlePositionId(), "USD/JPY", simulator.getId(), result.isAsk(), ratesService.getLatestRate(),ratesService.getLatestRate(),  result.getLots(), algorithmId, LocalDateTime.now(), LocalDateTime.now(), true);
                    updatedFunds = new Funds(simulator.getId(), algorithmId, LocalDateTime.now(), currentFunds + gotRate + (ratesService.getLatestRate() - gotRate) * (float) result.getLots() * 1000f * (result.isAsk() ? 1f : -1f));
                }else{
                    // 新規注文
                    // TODO: Method にまとめる
                    order = new Positions("USD/JPY", simulator.getId(), result.isAsk(), ratesService.getLatestRate(), result.getLots(), algorithmId, LocalDateTime.now());
                    updatedFunds = new Funds(simulator.getId(), algorithmId, LocalDateTime.now(), currentFunds - (ratesService.getLatestRate() * (float) result.getLots()));
                }
                positionsRepository.save(order);
                fundsRepository.save(updatedFunds);
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
