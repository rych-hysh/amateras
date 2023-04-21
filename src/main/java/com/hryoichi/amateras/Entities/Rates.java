package com.hryoichi.amateras.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Rates {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "rates_seq")
    @SequenceGenerator(name = "rates_seq", allocationSize = 1)
    private Integer id;

    private LocalDateTime date;

    private float askPrice;
    private float bidPrice;
}
