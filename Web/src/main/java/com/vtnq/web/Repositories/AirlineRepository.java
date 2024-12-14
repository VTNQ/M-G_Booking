package com.vtnq.web.Repositories;

import com.vtnq.web.DTOs.Airline.ListAirlineDto;
import com.vtnq.web.DTOs.Airline.UpdateAirlineDTO;
import com.vtnq.web.Entities.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AirlineRepository extends JpaRepository<Airline, Integer>{
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM Airline a WHERE a.name = :name")
    public boolean IsExistName(@Param("name")String name);
    @Query("select new com.vtnq.web.DTOs.Airline.ListAirlineDto(a.id,a.name,i.imageUrl,a.country.name) "+
            "from Airline a "
            + "join Picture i On a.id =i.airlineId order by a.id desc")
    List<ListAirlineDto> ShowAll();

    @Query("select new com.vtnq.web.DTOs.Airline.UpdateAirlineDTO(a.id,a.name,a.country.id,i.imageUrl) "+
            "From Airline a "+
            "join Picture i on a.id=i.airlineId "+
            "where a.id = :id")
    UpdateAirlineDTO FindById(@Param("id") Integer id);
}