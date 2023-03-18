package com.hryoichi.fxchart.Services;

import com.hryoichi.fxchart.Algorythms.AbstractAlgorithm;
import com.hryoichi.fxchart.Algorythms.Algorithms.SampleAlgorithm;
import com.hryoichi.fxchart.Enums.AlgorithmEnum;
import com.hryoichi.fxchart.Events.AlgorithmCheck;
import com.hryoichi.fxchart.Repositories.RunningAlgorithmsRepository;
import com.hryoichi.fxchart.Repositories.SimulatorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlgorithmManageService {
    @Autowired
    SimulatorsRepository simulatorsRepository;
    @Autowired
    RunningAlgorithmsRepository runningAlgorithmsRepository;
    @EventListener
    @Async
    public void onRateUpdated(AlgorithmCheck event){
        final var res = event.check();
        List<Integer> runningSimulatorIdList = simulatorsRepository.getRunningSimulatorsId();
        runningSimulatorIdList.forEach(simulatorId ->{
            List<Integer> subscribedAlgorithmIdList = runningAlgorithmsRepository.getSubscribedAlgorithmsBySimulatorId(simulatorId);
            subscribedAlgorithmIdList.forEach(algorithmId ->{
                AbstractAlgorithm algorithm = getAlgorithmById(algorithmId);
                algorithm.checkAlgorithm(simulatorId);
            });
        });
    }

    private AbstractAlgorithm getAlgorithmById(int algorithmId){
        switch (algorithmId){
            case 0:
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
