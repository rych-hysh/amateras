package com.hryoichi.amateras.Repositories;

import com.hryoichi.amateras.Entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
  @Query(value = "SELECT * FROM user WHERE id = :id", nativeQuery = true, countQuery = "select 1")
  String getEmailById(int id);
  @Query(value = "INSERT INTO user(name, mail_address, uuid) VALUES (:name, :mailAddress, :uuid)", nativeQuery = true, countQuery = "select 1")
  String addUser(String name, String mailAddress, UUID uuid);

}
