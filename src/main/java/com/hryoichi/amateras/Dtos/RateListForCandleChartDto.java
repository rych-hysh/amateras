package com.hryoichi.amateras.Dtos;

import com.hryoichi.amateras.Models.RateForCandleChart;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
public class RateListForCandleChartDto {
    // 	["day", "low", "open", "close", "high", "ave", "sigmah", "sigmal"],
    List<RateForCandleChart> data;
}
