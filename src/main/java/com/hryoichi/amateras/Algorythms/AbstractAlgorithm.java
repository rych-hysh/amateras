package com.hryoichi.amateras.Algorythms;

import com.hryoichi.amateras.Models.AlgorithmResult;
import com.hryoichi.amateras.Repositories.PositionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractAlgorithm {
    @Autowired
    PositionsRepository positionsRepository;
    private float fund;
    abstract protected void placeTradeNow(int simulatorId, int lots);
    abstract protected void closePositionNow(int simulatorId, int lots);
    abstract public AlgorithmResult checkAlgorithm();
    abstract public AlgorithmResult checkAlgorithmBySimulatorId(int simulatorId);
}
