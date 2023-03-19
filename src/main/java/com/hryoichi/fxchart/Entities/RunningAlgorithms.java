package com.hryoichi.fxchart.Entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.lang.Nullable;

@Entity
@Data
public class RunningAlgorithms {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "running_algorithms_seq")
    @SequenceGenerator(name = "running_algorithms_seq", allocationSize = 1, initialValue = 13)
    private Integer id;

    private int algorithmId;

    @Column(nullable = true)
    private Integer simulatorId;

    @Column(nullable = true)
    private String userUuid;

    private boolean isSubscribed;
}
