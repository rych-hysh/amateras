package com.hryoichi.amateras.Services;

import com.hryoichi.amateras.Entities.Simulators;
import com.hryoichi.amateras.Repositories.SimulatorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimulatorService {
    @Autowired
    SimulatorsRepository simulatorsRepository;

    public List<Simulators> getSimulatorsByUuid(String uuid) {
        return simulatorsRepository.getSimulatorsByUuid(uuid);
    }
}
