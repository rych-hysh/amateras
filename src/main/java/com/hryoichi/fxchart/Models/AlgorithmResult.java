package com.hryoichi.fxchart.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AlgorithmResult {
    private String pair = "USD/JPY";
    private boolean doOrder;
    private boolean isAsk;
    private Integer lots;
    private boolean isSettle;
    private Integer settlePositionId;
    // rate;
}
