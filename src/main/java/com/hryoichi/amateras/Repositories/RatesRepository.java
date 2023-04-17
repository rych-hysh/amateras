package com.hryoichi.amateras.Repositories;


import com.hryoichi.amateras.Entities.Rates;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface RatesRepository extends CrudRepository<Rates, Integer> {
    @Query(value = "SELECT * FROM rates order by date desc limit 1", nativeQuery = true, countQuery = "select 1")
    Rates getLatest();

    @Query(value = "SELECT * FROM rates order by date desc limit :limit", nativeQuery = true, countQuery = "select 1")
    List<Rates> getLatest(@Param("limit") int limit);

    @Query(value = "SELECT * FROM rates WHERE date >= :date ORDER BY date ASC LIMIT :limit", nativeQuery = true, countQuery = "select 1")
    List<Rates> getRatesAfterDate(@Param("date") LocalDateTime date, @Param("limit") int limit);

    @Query(value = "SELECT * FROM rates WHERE date <= :date ORDER BY date ASC LIMIT :limit", nativeQuery = true, countQuery = "select 1")
    List<Rates> getRatesBeforeDate(@Param("date") LocalDateTime date, @Param("limit") int limit);
}
