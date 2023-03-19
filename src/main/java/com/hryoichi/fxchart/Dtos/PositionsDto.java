package com.hryoichi.fxchart.Dtos;

import java.util.Date;

public class PositionsDto {
    public int id;
    public String pair;
    public String askOrBid;
    public float atRate;
    public int lots;
    public String algorithmName;
    public Date atDate;
    public float profits;
    public boolean isSettled;
}
