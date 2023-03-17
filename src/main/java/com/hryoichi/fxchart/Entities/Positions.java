package com.hryoichi.fxchart.Entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.jpa.repository.Query;

@Entity
@Getter
public class Positions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String pair;

    private int SimulatorId;

    private boolean ask;

    private float atRate;

    private int lots;

    private Date atDate;

    private boolean isSettled;

}
