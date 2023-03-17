package com.hryoichi.fxchart.Events;

import org.springframework.context.ApplicationEvent;

public class AlgorithmCheck extends ApplicationEvent {
    public AlgorithmCheck(Object source){
        super(source);
    }

    public float gotCurrentRate(float rate){
        return rate;
    }
    public String check(){return "success";}
}
