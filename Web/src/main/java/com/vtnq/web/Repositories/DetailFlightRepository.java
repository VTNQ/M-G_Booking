package com.vtnq.web.Repositories;

import com.vtnq.web.Entities.DetailFlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DetailFlightRepository extends JpaRepository<DetailFlight, Integer> {
    @Query("SELECT d from DetailFlight d where d.idFlight.id = :flightId")
    public List<DetailFlight> findByFlightId(int flightId);
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END " +
            "FROM DetailFlight a " +
            "WHERE a.type = :type AND a.idFlight.id = :flightId")
    boolean existsByTypeAndFlightId(@Param("type") String type, @Param("flightId") int flightId);

}