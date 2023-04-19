package com.hryoichi.amateras.Services;

import com.hryoichi.amateras.Dtos.FundsDto;
import com.hryoichi.amateras.Entities.Funds;
import com.hryoichi.amateras.Repositories.FundsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FundsService {
  @Autowired
  FundsRepository fundsRepository;
  public List<FundsDto> getFundsHistoryBySimulatorId(int simulatorId, LocalDateTime startDate){
    List<FundsDto> fundsDtoList= new ArrayList<>();
    LocalDateTime tmpDate = startDate;
    while(tmpDate.isBefore(LocalDateTime.now().minusMinutes(1))){
      fundsDtoList.add(new FundsDto(tmpDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), 0));
      tmpDate = tmpDate.plusHours(1);
    }
    List<Funds> fundHistory = fundsRepository.getFundUpdateHistoryBySimulatorId(simulatorId, startDate);

    DateTimeFormatter dtFt = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    fundHistory.forEach(fund -> {
      FundsDto newFundsDto =
          fundsDtoList.stream().filter(fundsDto -> LocalDateTime.parse(fundsDto.getDate(), dtFt).isAfter(fund.getUpdateDate())).toList().stream().findFirst().orElse(new FundsDto(LocalDateTime.now().format(dtFt), 0));
      int index = fundsDtoList.indexOf(newFundsDto);
      newFundsDto.setFunds(fund.getFunds());
      if(index != -1) {
        fundsDtoList.set(index, newFundsDto);
      }else{
        fundsDtoList.add(newFundsDto);
      }

    });
    for (int i = 0; i< fundsDtoList.size() - 1; i++){
      float funds =fundsDtoList.get(i).getFunds();
      if( funds == 0 )continue;
      if(fundsDtoList.get(i+1).getFunds() == 0) fundsDtoList.get(i + 1).setFunds(funds);
    }
    return fundsDtoList.stream().filter(fundsDto -> fundsDto.getFunds() != 0).toList();
  }
}
