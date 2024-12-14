package com.vtnq.web.Repositories;

import com.vtnq.web.DTOs.Flight.FlightListDTO;
import com.vtnq.web.Entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
@Query("select new com.vtnq.web.DTOs.Flight.FlightListDTO(f.id,f.airline.name,f.departureAirport.name,f.arrivalAirport.name,f.departureTime,f.arrivalTime)" +
        " from Flight f where f.airline.country.id = :id")
    List<FlightListDTO> findFlightListDTO(@Param("id") int id);
}