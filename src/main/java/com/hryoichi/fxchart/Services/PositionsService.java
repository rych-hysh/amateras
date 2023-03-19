package com.hryoichi.fxchart.Services;

import com.hryoichi.fxchart.Dtos.PositionsDto;
import com.hryoichi.fxchart.Entities.Positions;
import com.hryoichi.fxchart.Repositories.PositionsRepository;
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
        positionsDto.pair = position.getPair();
        positionsDto.isAsk = position.isAsk();
        positionsDto.atRate = position.getAtRate();
        positionsDto.lots = position.getLots();
        positionsDto.algorithmId = position.getAlgorithmId();
        positionsDto.atDate = position.getAtDate();
        positionsDto.profits = (positionsDto.atRate - ratesService.getLatestRate()) * position.getLots() * (position.isAsk() ? 1 : -1);
        return positionsDto;
    }

    public List<PositionsDto> getPositionsDtoListOf(List<Positions> positionsList){
        List<PositionsDto> positionsDtoList = positionsList.stream().map(this::of).collect(Collectors.toList());
        return positionsDtoList;
    }
}
