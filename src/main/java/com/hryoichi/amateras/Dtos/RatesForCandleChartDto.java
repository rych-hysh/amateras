package com.hryoichi.amateras.Dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RatesForCandleChartDto {
    LocalDateTime date;

    float low;

    float open;

    float close;

    float high;

    float ave;

    float sigma_high;

    float sigma_low;
}
