package com.hryoichi.fxchart.Algorythms;

import com.hryoichi.fxchart.Models.AlgorithmResult;
import com.hryoichi.fxchart.Repositories.PositionsRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractAlgorithm {
    @Autowired
    PositionsRepository positionsRepository;
    private float fund;
    abstract protected void placeTradeNow(int simulatorId, int lots);
    abstract protected void closePositionNow(int simulatorId, int lots);
    abstract public AlgorithmResult checkAlgorithm();
    abstract public AlgorithmResult checkAlgorithmBySimulatorId(int simulatorId);
}
