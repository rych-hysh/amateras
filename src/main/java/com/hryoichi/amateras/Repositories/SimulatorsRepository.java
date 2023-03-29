package com.hryoichi.amateras.Repositories;

import com.hryoichi.amateras.Entities.Simulators;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimulatorsRepository extends CrudRepository<Simulators, Integer> {
    @Query(value = "SELECT * FROM simulators WHERE is_running = true", nativeQuery = true, countQuery = "select 1")
    List<Simulators> getRunningSimulators();
    @Query(value = "SELECT * FROM simulators WHERE user_uuid = :uuid", nativeQuery = true, countQuery = "select 1")
    List<Simulators> getSimulatorsByUuid(String uuid);
}
