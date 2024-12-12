package com.vtnq.web.Repositories;

import com.vtnq.web.Entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Integer> {
}