package com.hryoichi.fxchart.Repositories;

import com.hryoichi.fxchart.Entities.RunningAlgorithms;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RunningAlgorithmsRepository extends CrudRepository<RunningAlgorithms, Integer> {
    @Query(value = "SELECT id FROM running_algorithms WHERE simulator_id = :simulatorId AND is_subscribed = true", nativeQuery = true, countQuery = "select 1")
    List<Integer> getSubscribedAlgorithmsIdBySimulatorId(@Param("simulatorId") int simulatorId);

    @Query(value = "SELECT * FROM running_algorithms WHERE user_uuid IS NOT NULL AND is_subscribed = true", nativeQuery = true, countQuery = "select 1")
    List<RunningAlgorithms> getLINESubscribedAlgorithms();

    @Query(value = "SELECT * FROM running_algorithms WHERE is_subscribed = true", nativeQuery = true, countQuery = "select 1")
    List<RunningAlgorithms> getAllSubscribedAlgorithms();
}
