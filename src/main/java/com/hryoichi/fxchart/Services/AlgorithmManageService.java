package com.hryoichi.fxchart.Services;

import com.hryoichi.fxchart.Entities.Simulator;
import com.hryoichi.fxchart.Events.AlgorithmCheck;
import com.hryoichi.fxchart.Repositories.SimulatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlgorithmManageService implements ApplicationListener<AlgorithmCheck> {
    @Autowired
    SimulatorRepository simulatorRepository;
    @Override
    public void onApplicationEvent(AlgorithmCheck event){
        final var res = event.check();
        List<Simulator> runningSimulators = simulatorRepository.getRunningSimulators();
        runningSimulators.forEach(item -> item.getUserUuid());

    }
}
