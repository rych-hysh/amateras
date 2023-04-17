package com.hryoichi.amateras.Dtos;

import lombok.Setter;

import java.util.Date;

@Setter
public class RatesForCandleChartDto {
    Date date;

    float low;

    float open;

    float close;

    float high;

    float ave;

    float sigma_high;

    float sigma_low;
}
