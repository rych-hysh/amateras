package com.hryoichi.fxchart.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Simulator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private int simulatorName;

    private String userUuid;

    private boolean isRunning;

}
