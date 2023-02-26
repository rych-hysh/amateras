package com.hryoichi.fxchart.Repositories;


import com.hryoichi.fxchart.Entities.Rates;
import com.hryoichi.fxchart.models.Rate;
import org.springframework.data.repository.CrudRepository;

public interface RateRepository extends CrudRepository<Rates, Integer> {
}
