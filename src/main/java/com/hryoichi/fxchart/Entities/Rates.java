package com.hryoichi.fxchart.Entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Rates {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Date date;

    private float rate;

    public Integer getId(){ return id; }

    public Date getDate(){ return date; }

    public float getRate(){ return rate; }
}
