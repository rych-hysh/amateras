package com.hryoichi.amateras.Repositories;

import com.hryoichi.amateras.Entities.Positions;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionsRepository extends CrudRepository<Positions, Integer> {
    @Query(value = "SELECT * from positions", nativeQuery = true, countQuery = "select 1")
    List<Positions> queryAll();
    @Query(value = "SELECT * FROM positions WHERE simulator_id = :simulatorId AND is_settled = false", nativeQuery = true, countQuery = "select 1")
    List<Positions> getOpenedPositionsBySimulatorId(@Param("simulatorId") int simulatorId);

    @Query(value = "SELECT * FROM positions WHERE simulator_id = :simulatorId AND is_settled = true", nativeQuery = true, countQuery = "select 1")
    List<Positions> getClosedPositionsBySimulatorId(@Param("simulatorId") int simulatorId);
}
