package com.hryoichi.amateras.Dtos;

import lombok.Data;
@Data
public class HistoryDto {
    int id;
    String pair;
    String askOrBid;
    float settledRate;
    int lots;
    String algorithmName;
    String settledDate;
    float profits;
}

/*
*
	id: number,
	askOrBid: string,
	settledRate: number,
	lots: number,
	algorithmName: string,
	profits: number,
	settledDate: String,
	pair: String
*
*/