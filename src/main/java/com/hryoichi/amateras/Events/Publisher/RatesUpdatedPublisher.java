package com.hryoichi.amateras.Events.Publisher;

import com.hryoichi.amateras.Events.AlgorithmCheck;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RatesUpdatedPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void Updated(){
        applicationEventPublisher.publishEvent(new AlgorithmCheck(this));
    }
}
