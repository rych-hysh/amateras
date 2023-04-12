package com.hryoichi.amateras.RestController;

import com.hryoichi.amateras.Dtos.SimulatorsDto;
import com.hryoichi.amateras.Entities.Simulators;
import com.hryoichi.amateras.Services.SimulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/update")
    @CrossOrigin
    @ResponseBody
    private ResponseEntity<String> addSimulator(@RequestBody SimulatorsDto simDto){
        final var a = simDto;
        simulatorService.updateSimulator(simDto);
        return new ResponseEntity<String>("text", HttpStatus.OK);
    }
}
