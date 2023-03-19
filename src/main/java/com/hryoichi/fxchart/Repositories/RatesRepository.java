package com.hryoichi.fxchart.Repositories;


import com.hryoichi.fxchart.Entities.Rates;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RatesRepository extends CrudRepository<Rates, Integer> {
    @Query(value = "SELECT * FROM rates order by date desc limit 1", nativeQuery = true, countQuery = "select 1")
    Rates getLatest();
}
