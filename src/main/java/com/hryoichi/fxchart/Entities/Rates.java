package com.hryoichi.fxchart.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Rates {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq")
    @SequenceGenerator(name = "seq", allocationSize = 1)
    private Integer id;

    private LocalDateTime date;

    private float askPrice;
    private float bidPrice;

}
