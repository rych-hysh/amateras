package com.hryoichi.fxchart.Services;

import com.hryoichi.fxchart.Entities.Positions;
import com.hryoichi.fxchart.Repositories.PositionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionsService {
    @Autowired
    PositionsRepository positionsRepository;

    public List<Positions> getPositionsBySimulatorId(int simulatorId) {
        return positionsRepository.getPositionsBySimulatorId(simulatorId);
    }
}
