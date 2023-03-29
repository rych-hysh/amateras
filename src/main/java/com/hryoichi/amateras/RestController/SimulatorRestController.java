package com.hryoichi.amateras.RestController;

import com.hryoichi.amateras.Entities.Simulators;
import com.hryoichi.amateras.Services.SimulatorService;
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
}
