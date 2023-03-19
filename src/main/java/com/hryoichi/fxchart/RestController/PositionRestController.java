package com.hryoichi.fxchart.RestController;

import com.hryoichi.fxchart.Entities.Positions;
import com.hryoichi.fxchart.Services.PositionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/positions")
public class PositionRestController {
    @Autowired
    private PositionsService positionsService;
    @GetMapping("/{simulatorId}")
    public @ResponseBody Iterable<Positions> getPositionsBySimulatorId(@PathVariable("simulatorId") int simulatorId){
        return positionsService.getPositionsBySimulatorId(simulatorId);
    }
}
