package com.hryoichi.fxchart.RestController;

import com.hryoichi.fxchart.Entities.Positions;
import com.hryoichi.fxchart.Repositories.PositionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/positions")
public class PositionRestController {
    @Autowired
    private PositionsRepository positionsRepository;
    // 下例では、わかりやすさのために Controller で使用しているが、実際は Service で使用したほうがいいらしい
    @GetMapping("/simulator")
    public @ResponseBody Iterable<Positions> getPositionsBySimulatorId(@RequestParam("simulatorId") int simulatorId){
        return positionsRepository.getPositionsBySimulatorId(simulatorId);
    }
}
