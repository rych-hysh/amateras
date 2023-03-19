package com.hryoichi.fxchart.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Data
public class Simulators {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "simulators_seq")
    @SequenceGenerator(name = "simulators_seq", allocationSize = 1)
    private Integer id;

    private String simulatorName;

    private String userUuid;

    private Boolean isRunning;

}
