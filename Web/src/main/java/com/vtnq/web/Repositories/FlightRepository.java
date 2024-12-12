package com.vtnq.web.Repositories;

import com.vtnq.web.Entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
}