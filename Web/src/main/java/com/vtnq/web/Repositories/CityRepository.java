package com.vtnq.web.Repositories;

import com.vtnq.web.Entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Integer> {
}