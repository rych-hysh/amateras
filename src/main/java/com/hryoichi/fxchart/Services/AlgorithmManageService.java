package com.hryoichi.fxchart.Services;

import com.hryoichi.fxchart.Events.AlgorithmCheck;
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
    @EventListener
    @Async
    public void onRateUpdated(AlgorithmCheck event){
        final var res = event.check();
        List<Simulator> runningSimulators = simulatorRepository.getRunningSimulators();
        runningSimulators.forEach(item -> item.getUserUuid());

    }
}
