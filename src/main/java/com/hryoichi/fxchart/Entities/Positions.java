package com.hryoichi.fxchart.Entities;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Positions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "positions_seq")
    @SequenceGenerator(name = "positions_seq", allocationSize = 1)
    private Integer id;

    private String pair;

    private int SimulatorId;

    private boolean ask;

    private float atRate;

    private int lots;

    private int algorithmId;

    private Date atDate;

    private boolean isSettled;

}
