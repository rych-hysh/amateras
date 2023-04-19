package com.hryoichi.amateras.Repositories;

import com.hryoichi.amateras.Entities.Funds;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FundsRepository extends CrudRepository<Funds, Integer> {
  @Query(value = "SELECT funds from funds WHERE simulator_id = :simulatorId ORDER BY update_date DESC limit 1", nativeQuery = true, countQuery = "select 1")
  Optional<Float> getCurrentFundsBySimulatorId(@Param("simulatorId") int simulatorId);
  @Query(value = "SELECT * from funds WHERE simulator_id = :simulatorId ORDER BY update_date ASC", nativeQuery = true, countQuery = "select 1")
  List<Funds> getFundUpdateHistoryBySimulatorId(@Param("simulatorId") int simulatorId);
}
