package com.hryoichi.amateras.Services;

import com.hryoichi.amateras.Dtos.PositionsDto;
import com.hryoichi.amateras.Entities.Positions;
import com.hryoichi.amateras.Repositories.PositionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PositionsService {
    @Autowired
    PositionsRepository positionsRepository;
    @Autowired
    RatesService ratesService;

    public List<Positions> getPositionsBySimulatorId(int simulatorId) {
        return positionsRepository.getPositionsBySimulatorId(simulatorId);
    }

    public PositionsDto of(Positions position){
        PositionsDto positionsDto = new PositionsDto();
        positionsDto.id = position.getId();
        positionsDto.pair = position.getPair();
        positionsDto.askOrBid = position.isAsk() ? "ask" : "bid";
        positionsDto.atRate = position.getAtRate();
        positionsDto.lots = position.getLots();
        positionsDto.algorithmName = position.getAlgorithmId().toString(); //TODO: getAlgorigthmNameById
        positionsDto.gotAt = position.getGotAt();
        positionsDto.settledAt = position.getSettledAt();
        positionsDto.profits = (positionsDto.atRate - ratesService.getLatestRate()) * position.getLots() * (position.isAsk() ? 1 : -1);
        positionsDto.isSettled = position.isSettled();
        return positionsDto;
    }

    public List<PositionsDto> getPositionsDtoListOf(List<Positions> positionsList){
        return positionsList.stream().map(this::of).collect(Collectors.toList());
    }
}
