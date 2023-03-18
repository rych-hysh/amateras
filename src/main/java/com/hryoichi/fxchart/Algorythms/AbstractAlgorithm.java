package com.hryoichi.fxchart.Algorythms;

public abstract class AbstractAlgorithm {
    private float fund;
    abstract protected void placeTradeNow(int lots);
    abstract protected void closePositionNow(int lots);
    abstract public void checkAlgorithm(int simulatorId);
}
