package com.hryoichi.fxchart.Repositories;

import com.hryoichi.fxchart.Entities.Simulator;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SimulatorRepository extends CrudRepository<Simulator, Integer> {
    @Query(value = "SELECT * FROM simulator WHERE is_running = true", nativeQuery = true, countQuery = "select 1")
    List<Simulator> getRunningSimulators();
}
