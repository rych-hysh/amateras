package com.hryoichi.amateras.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AlgorithmResult {
    private String pair;
    private boolean doOrder;
    private boolean isAsk;
    private Integer lots;
    private Float atRate;
    private boolean isSettle;
    private Integer settlePositionId;
    // rate;
}
