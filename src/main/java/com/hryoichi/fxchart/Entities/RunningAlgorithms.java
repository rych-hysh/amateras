package com.hryoichi.fxchart.Entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class RunningAlgorithms {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "running_algorithms_seq")
    @SequenceGenerator(name = "running_algorithms_seq", allocationSize = 1)
    private Integer id;

    private int algorithmId;

    @Nullable
    private int simulatorId;

    @Nullable
    private String userUuid;

    private boolean isSubscribed;
}
