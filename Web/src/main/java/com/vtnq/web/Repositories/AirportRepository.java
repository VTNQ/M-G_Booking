package com.vtnq.web.Repositories;

import com.vtnq.web.Entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AirportRepository extends JpaRepository<Airport, Integer> {
    @Query("SELECT a from Airport a where a.city.country.id = :id order by a.id desc ")
    List<Airport> findByCountry(@Param("id") int id);
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM Airport a WHERE a.name = :name")
    boolean existsByName(@Param("name") String name);

}