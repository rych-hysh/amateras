package com.hryoichi.amateras.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_seq")
  @SequenceGenerator(name = "user_seq", allocationSize = 1)
  int id;
  UUID uuid;
  String name;
  String mail_address;

  public User(String name, String mailAddress, UUID uuid){
    this.name = name;
    this.mail_address = mailAddress;
    this.uuid = uuid;
  }
}
