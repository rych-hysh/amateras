package com.hryoichi.amateras.Models;

import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class RateForCandleChart {
    LocalDateTime date;

    float low;

    float open;

    float close;

    float high;

    float ave;

    float sigma_high;

    float sigma_low;

}
