package com.hryoichi.amateras.Models;

import com.hryoichi.amateras.Entities.Rates;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
public class RateForCandleChart {
    Date date;

    float low;

    float open;

    float close;

    float high;

    float ave;

    float sigma_high;

    float sigma_low;

}
