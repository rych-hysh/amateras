package com.hryoichi.amateras.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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

    @NonNull
    private float gotRate;
    @Nullable
    private float settledRate;

    private Integer lots;

    private Integer algorithmId;

    @NonNull
    private LocalDateTime gotDate;
    private LocalDateTime settledDate;

    private boolean isSettled;

    public Positions(String pair, Integer simulatorId, boolean isAsk, float gotRate, float settledRate, Integer lots, Integer algorithmId, LocalDateTime gotDate, LocalDateTime settledDate, boolean isSettled){

        this.pair = pair;
        this.simulatorId = simulatorId;
        this.isAsk = isAsk;
        this.gotRate = gotRate;
        this.settledRate = settledRate;
        this.lots = lots;
        this.algorithmId = algorithmId;
        this.gotDate = gotDate;
        this.settledDate = settledDate;
        this.isSettled = isSettled;
    }
    public Positions(String pair, Integer simulatorId, boolean isAsk, float gotRate, Integer lots, Integer algorithmId, LocalDateTime gotDate){
        this.pair = pair;
        this.simulatorId = simulatorId;
        this.isAsk = isAsk;
        this.gotRate = gotRate;
        this.lots = lots;
        this.algorithmId = algorithmId;
        this.gotDate = gotDate;
        this.isSettled = false;
    }
}
