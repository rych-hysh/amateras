package com.hryoichi.amateras.Services;

import com.hryoichi.amateras.Dtos.HistoryDto;
import com.hryoichi.amateras.Dtos.PositionsDto;
import com.hryoichi.amateras.Entities.Positions;
import com.hryoichi.amateras.Repositories.PositionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PositionsService {
    @Autowired
    PositionsRepository positionsRepository;
    @Autowired
    RatesService ratesService;

    public List<Positions> getOpenedPositionsBySimulatorId(int simulatorId) {
        List<Positions> openedPositions =positionsRepository.getOpenedPositionsBySimulatorId(simulatorId);
        Collections.reverse(openedPositions);
        return openedPositions;
    }

    public List<Positions> getClosedPositionsBySimulatorId(int simulatorId) {
        List<Positions> closedPositions = positionsRepository.getClosedPositionsBySimulatorId(simulatorId);
        Collections.reverse(closedPositions);
        return closedPositions;
    }
    public PositionsDto getPositionsDtoOf(Positions position){
        PositionsDto positionsDto = new PositionsDto();
        positionsDto.id = position.getId();
        positionsDto.pair = position.getPair();
        positionsDto.askOrBid = position.isAsk() ? "ask" : "bid";
        positionsDto.gotRate = position.getGotRate();
        positionsDto.lots = position.getLots();
        positionsDto.algorithmName = position.getAlgorithmId().toString(); //TODO: getAlgorigthmNameById
        positionsDto.gotDate = position.getGotDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        positionsDto.profits = (positionsDto.gotRate - ratesService.getLatestRate()) * position.getLots() * (position.isAsk() ? 1 : -1);

        return positionsDto;
    }

    public HistoryDto getHistoryDtoOf(Positions position){
        HistoryDto historyDto = new HistoryDto();
        historyDto.setId(position.getId());
        historyDto.setAskOrBid(position.isAsk() ? "ask" : "bid");
        historyDto.setSettledRate(position.getSettledRate());
        historyDto.setLots(position.getLots());
        historyDto.setAlgorithmName(position.getAlgorithmId().toString());
        historyDto.setProfits((position.getSettledRate() - position.getGotRate()) * position.getLots());
        historyDto.setSettledDate(position.getSettledDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        historyDto.setPair(position.getPair());
        return historyDto;
    }

    public List<PositionsDto> getPositionsDtoListOf(List<Positions> positionsList){
        return positionsList.stream().map(this::getPositionsDtoOf).collect(Collectors.toList());
    }

    public List<HistoryDto> getHistoryDtoListOf(List<Positions> positionsList){
        return positionsList.stream().map(this::getHistoryDtoOf).collect(Collectors.toList());
    }
}
