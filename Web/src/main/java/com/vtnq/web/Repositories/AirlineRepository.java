package com.vtnq.web.Repositories;

import com.vtnq.web.Entities.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineRepository extends JpaRepository<Airline, Integer>{
}