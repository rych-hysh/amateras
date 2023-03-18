package com.hryoichi.fxchart.Enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AlgorithmEnum {
    SAMPLE_ALGORITHM("SampleAlgorithm", 1);

    private String name;
    private int id;
    public static String getNameById(int id){
        for(AlgorithmEnum e: values()){
            if(e.id == id) return e.name;
        }
        return null;
    }
}
