package com.hryoichi.amateras.Dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
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
