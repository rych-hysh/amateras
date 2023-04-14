package com.hryoichi.amateras.Dtos;

import lombok.Getter;

@Getter
public class RunningAlgorithmDto {
    private int algorithmId;

    private Integer simulatorId;

    // TODO:Simulatosと結びついているならアルゴリズムがユーザーと結びつく必要はないのでは
    private String userUuid;

    private Boolean isSubscribed;
}
