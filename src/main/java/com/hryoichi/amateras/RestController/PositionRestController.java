package com.hryoichi.amateras.RestController;

import com.hryoichi.amateras.Dtos.HistoryDto;
import com.hryoichi.amateras.Dtos.PositionsDto;
import com.hryoichi.amateras.Services.PositionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/positions")
@CrossOrigin
public class PositionRestController {
    @Autowired
    private PositionsService positionsService;
    @GetMapping("/{simulatorId}/unsettled")
    public @ResponseBody List<PositionsDto> getOpenedPositionsBySimulatorId(@PathVariable("simulatorId") int simulatorId){
        return positionsService.getPositionsDtoListOf(positionsService.getOpenedPositionsBySimulatorId(simulatorId));
    }

    @GetMapping("/{simulatorId}/settled")
    public @ResponseBody List<HistoryDto> getClosedPositionsBySimulatorId(@PathVariable("simulatorId") int simulatorId){
        return positionsService.getHistoryDtoListOf(positionsService.getClosedPositionsBySimulatorId(simulatorId));
    }
}
