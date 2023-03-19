package com.hryoichi.fxchart.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AlgorithmResult {
    private boolean doOrder;
    private boolean isAsk;
    private Integer lots;
    private String pair = "USD/JPY";
    // rate;
}
