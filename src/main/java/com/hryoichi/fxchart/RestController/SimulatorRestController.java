package com.hryoichi.fxchart.RestController;

import com.hryoichi.fxchart.Entities.Simulators;
import com.hryoichi.fxchart.Services.SimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("simulators")
@CrossOrigin
public class SimulatorRestController {
    @Autowired
    SimulatorService simulatorService;
    @GetMapping("/{uuid}")
    @CrossOrigin
    private List<Simulators> getSimulatorsByUuid(@PathVariable("uuid") String uuid){
        return simulatorService.getSimulatorsByUuid(uuid);
    }

    @PostMapping("/update}")
    @CrossOrigin
    private String updateSimulator(@RequestBody Simulators simulators){
        simulatorService.updateSimulator(simulators);
        return "success";
    }
}
