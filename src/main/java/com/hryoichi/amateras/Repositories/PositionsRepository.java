package com.hryoichi.amateras.Repositories;

import com.hryoichi.amateras.Entities.Positions;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PositionsRepository extends CrudRepository<Positions, Integer> {
    @Query(value = "SELECT * from positions", nativeQuery = true, countQuery = "select 1")
    List<Positions> queryAll();
    @Query(value = "SELECT * FROM positions WHERE simulator_id = :simulatorId", nativeQuery = true, countQuery = "select 1")
    List<Positions> getPositionsBySimulatorId(@Param("simulatorId") int simulatorId);
}
