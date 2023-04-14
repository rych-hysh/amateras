package com.hryoichi.amateras.Services;

import com.hryoichi.amateras.Dtos.AlgorithmDto;
import com.hryoichi.amateras.Dtos.RunningAlgorithmDto;
import com.hryoichi.amateras.Entities.RunningAlgorithms;
import com.hryoichi.amateras.Repositories.RunningAlgorithmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AlgorithmsService {
    private static List<AlgorithmDto> availableAlgorithm = new ArrayList<AlgorithmDto>(Arrays.asList(
            new AlgorithmDto(1, "Sample Algorithm"),
            new AlgorithmDto(2, "Sample 2")
    ));
    @Autowired
    RunningAlgorithmsRepository runningAlgorithmsRepository;
    public List<AlgorithmDto> getAvailableAlgorithmList(){
        return availableAlgorithm;
    }

    public List<AlgorithmDto> getAlgorithmListByAlgorithmIdList(List<Integer> idList) {
        var algorithmList = new ArrayList<AlgorithmDto>();
        idList.forEach(id -> {
            algorithmList.add(getAlgorithmByAlgorithmId(id));
        });
        return algorithmList;
    }

    public AlgorithmDto getAlgorithmByAlgorithmId(int algorithmId){
        return availableAlgorithm.stream().filter(algorithm -> algorithm.getId() == algorithmId).findFirst().orElseThrow();
    }

    //TODO:
    public void addAlgorithm(RunningAlgorithmDto algDto){
        List<RunningAlgorithms> existingAlgorithmList = runningAlgorithmsRepository.getSubscribedAlgorithmsBySimulatorId(algDto.getSimulatorId());
        // 当面は各シミュレータにアルゴリズムは最大一つ
        if(!existingAlgorithmList.isEmpty()){return;}
        // 同じアルゴリズムは一つのみ
        if(existingAlgorithmList.stream().filter(runningAlgorithms -> runningAlgorithms.getAlgorithmId() == algDto.getAlgorithmId()).count() != 0)return;
        var newAlg = new RunningAlgorithms();
        newAlg.setAlgorithmId(algDto.getAlgorithmId());
        newAlg.setSubscribed(algDto.getIsSubscribed());
        newAlg.setSimulatorId(algDto.getSimulatorId());
        newAlg.setUserUuid(algDto.getUserUuid());
        runningAlgorithmsRepository.save(newAlg);
    }
}
