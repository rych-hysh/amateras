package com.hryoichi.amateras.Entities;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Positions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "positions_seq")
    @SequenceGenerator(name = "positions_seq", allocationSize = 1, initialValue = 13)
    private Integer id;

    private String pair;

    private Integer simulatorId;

    private boolean isAsk;

    private float atRate;

    private Integer lots;

    private Integer algorithmId;

    private Date atDate;

    private boolean isSettled;

    public Positions(String pair, Integer simulatorId, boolean isAsk, float atRate, Integer lots, Integer algorithmId, Date atDate, boolean isSettled){
        this.pair = pair;
        this.simulatorId = simulatorId;
        this.isAsk = isAsk;
        this.atRate = atRate;
        this.lots = lots;
        this.algorithmId = algorithmId;
        this.atDate = atDate;
        this.isSettled = isSettled;
    }
}
