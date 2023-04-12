package com.hryoichi.amateras.RestController;

import com.hryoichi.amateras.Models.Algorithm;
import com.hryoichi.amateras.Services.AlgorithmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("algorithms")
public class AlgorithmRestController {

    @Autowired
    AlgorithmsService algorithmsService;
    @GetMapping("available")
    @CrossOrigin
    public List<Algorithm> getAvailableAlgorithm(){
        return algorithmsService.getAlgorithmList();
    }
}
