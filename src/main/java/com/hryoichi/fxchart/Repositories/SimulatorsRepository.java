package com.hryoichi.fxchart.Repositories;

import com.hryoichi.fxchart.Entities.Simulators;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SimulatorsRepository extends CrudRepository<Simulators, Integer> {
    @Query(value = "SELECT id FROM simulator WHERE is_running = true", nativeQuery = true, countQuery = "select 1")
    List<Integer> getRunningSimulatorsId();
}
