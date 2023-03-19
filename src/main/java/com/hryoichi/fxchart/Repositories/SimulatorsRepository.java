package com.hryoichi.fxchart.Repositories;

import com.hryoichi.fxchart.Entities.Simulators;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SimulatorsRepository extends CrudRepository<Simulators, Integer> {
    @Query(value = "SELECT * FROM simulators WHERE is_running = true", nativeQuery = true, countQuery = "select 1")
    List<Simulators> getRunningSimulators();
    @Query(value = "SELECT * FROM simulators WHERE user_uuid = :uuid", nativeQuery = true, countQuery = "select 1")
    List<Simulators> getSimulatorsByUuid(String uuid);
}
