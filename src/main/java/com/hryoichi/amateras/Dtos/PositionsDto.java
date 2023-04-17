package com.hryoichi.amateras.Dtos;

import java.util.Date;

public class PositionsDto {
    public int id;
    public String pair;
    public String askOrBid;
    public float atRate;
    public int lots;
    public String algorithmName;
    public Date gotAt;
    public Date settledAt;
    public float profits;
    public boolean isSettled;
}
