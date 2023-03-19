package com.hryoichi.fxchart.RestController;

import com.hryoichi.fxchart.Dtos.PositionsDto;
import com.hryoichi.fxchart.Services.PositionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/positions")
@CrossOrigin
public class PositionRestController {
    @Autowired
    private PositionsService positionsService;
    @GetMapping("/{simulatorId}")
    public List<PositionsDto> getPositionsBySimulatorId(@PathVariable("simulatorId") int simulatorId){
        return positionsService.getPositionsDtoListOf(positionsService.getPositionsBySimulatorId(simulatorId));
    }
}
