package com.hryoichi.amateras.Algorythms;

import com.hryoichi.amateras.Models.AlgorithmResult;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractAlgorithm {
    static public int ID;
    private float fund;
    abstract protected void placeTradeNow(int simulatorId, int lots);
    abstract protected void closePositionNow(int simulatorId, int lots);
    abstract public AlgorithmResult checkAlgorithmBySimulatorId(int simulatorId);
}
