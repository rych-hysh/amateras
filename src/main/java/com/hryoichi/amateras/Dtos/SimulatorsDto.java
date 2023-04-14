package com.hryoichi.amateras.Dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimulatorsDto {
    private Integer id;

    private String simulatorName;

    private String userUuid;

    private Boolean isRunning;
}
