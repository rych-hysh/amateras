package com.hryoichi.amateras.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Funds {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "funds_seq")
  @SequenceGenerator(name = "funds_seq", allocationSize = 1, initialValue = 13)
  private Integer id;

  private int simulatorId;

  private  int algorithmId;

  private LocalDateTime updateDate;

  private float fund;

}
