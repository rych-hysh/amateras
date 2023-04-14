package com.hryoichi.amateras.RestController;

import com.hryoichi.amateras.Dtos.AlgorithmDto;
import com.hryoichi.amateras.Dtos.RunningAlgorithmDto;
import com.hryoichi.amateras.Entities.RunningAlgorithms;
import com.hryoichi.amateras.Repositories.RunningAlgorithmsRepository;
import com.hryoichi.amateras.Services.AlgorithmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("algorithms")
public class AlgorithmRestController {

    @Autowired
    AlgorithmsService algorithmsService;
    @Autowired
    RunningAlgorithmsRepository runningAlgorithmsRepository;
    @GetMapping("/available")
    @CrossOrigin
    public List<AlgorithmDto> getAvailableAlgorithm(){
        return algorithmsService.getAvailableAlgorithmList();
    }

    @GetMapping("/{simulatorId}")
    @CrossOrigin
    public List<AlgorithmDto> getAlgorithmBySimulatorId(@PathVariable("simulatorId") int simulatorId) {
        var runningAlgorithmList = runningAlgorithmsRepository.getSubscribedAlgorithmsBySimulatorId(simulatorId);
        List<Integer> runningAlgorithmIdList = runningAlgorithmList.stream().map(RunningAlgorithms::getAlgorithmId).collect(Collectors.toList());
        if(runningAlgorithmList.isEmpty()){
            return new ArrayList<>();
        }
        return algorithmsService.getAlgorithmListByAlgorithmIdList(runningAlgorithmIdList);
    }

    @PostMapping("/add")
    @CrossOrigin
    @ResponseBody
    public void addAlgorithm(@RequestBody RunningAlgorithmDto algDto){
        algorithmsService.addAlgorithm(algDto);
    }
}
