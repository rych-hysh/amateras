package com.hryoichi.amateras.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class RunningAlgorithms {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "running_algorithms_seq")
    @SequenceGenerator(name = "running_algorithms_seq", allocationSize = 1, initialValue = 13)
    private Integer id;

    private int algorithmId;

    @Column()
    private Integer simulatorId;

    // TODO:Simulatosと結びついているならアルゴリズムがユーザーと結びつく必要はないのでは
    @Column()
    private String userUuid;

    private boolean isSubscribed;
}
