package com.hryoichi.amateras.Services;

import com.hryoichi.amateras.Models.Algorithm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlgorithmsService {
    public List<Algorithm> getAlgorithmList(){
        var n = new ArrayList<Algorithm>();
        n.add(new Algorithm(1, "Sample Algorithm"));
        n.add(new Algorithm(2, "Sample 2"));
        return n;
    }
}
