package com.hryoichi.amateras.Dtos;

import java.time.LocalDateTime;

public class PositionsDto {
    public int id;
    public String pair;
    public String askOrBid;
    public float atRate;
    public int lots;
    public String algorithmName;
    public LocalDateTime atDate;
    public float profits;
    public boolean isSettled;
}
