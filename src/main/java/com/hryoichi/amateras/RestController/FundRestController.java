package com.hryoichi.amateras.RestController;

import com.hryoichi.amateras.Dtos.FundsDto;
import com.hryoichi.amateras.Services.FundsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("funds")
public class FundRestController {
  @Autowired
  FundsService fundsService;
  @GetMapping("/{simulatorId}")
  @CrossOrigin
  List<FundsDto> getFundsHistoryBySimulatorId(@PathVariable("simulatorId") int simulatorId){
    return fundsService.getFundsHistoryBySimulatorId(simulatorId, LocalDateTime.now().minusHours(24));
  }

}
