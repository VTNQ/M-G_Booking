package com.vtnq.web.Repositories;

import com.vtnq.web.DTOs.Flight.FlightListDTO;
import com.vtnq.web.DTOs.Flight.ResultFlightDTO;
import com.vtnq.web.Entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
@Query("select new com.vtnq.web.DTOs.Flight.FlightListDTO(f.id,f.airline.name,f.departureAirport.name,f.arrivalAirport.name,f.departureTime,f.arrivalTime)" +
        " from Flight f where f.airline.country.id = :id")
    List<FlightListDTO> findFlightListDTO(@Param("id") int id);
    @Query("SELECT MIN(G.price) FROM Flight f Join DetailFlight G on f.id=G.idFlight.id where  DATE(f.departureTime) = :departureTime")
    public BigDecimal FindPrice(@Param("departureTime") LocalDate departureTime);
    @Query("SELECT new com.vtnq.web.DTOs.Flight.ResultFlightDTO(f.id,d.imageUrl,f.arrivalAirport.city.name,f.arrivalTime,f.departureTime," +
            "G.price,c.name,f.departureTime,f.arrivalTime,f.departureTime,f.arrivalTime,f.departureAirport.name,f.airline.id) FROM Flight f " +
            "JOIN Airport a on a.id=f.departureAirport.id " +
            "JOIN Airport b on b.id=f.arrivalAirport.id " +
            "JOIN Airline c on c.id=f.airline.id "+
            "JOIN Picture d on c.id=d.airlineId "+
            "Join DetailFlight G on f.id=G.idFlight.id "+
            "join City e on e.id=b.city.id "+
            "WHERE f.departureAirport.id= :departureAirport " +
            "AND f.arrivalAirport.id = :arrivalAirport " +
            "AND DATE(f.departureTime) = :departureTime "+
            "And G.type = :TypeFlight")
    List<ResultFlightDTO> findFlightsByAirportsAndDepartureTime(
            @Param("departureAirport") int departureAirport,
            @Param("arrivalAirport") int arrivalAirport,
            @Param("departureTime") LocalDate departureTime,
            @Param("TypeFlight") String TypeFlight );
    @Query("SELECT new com.vtnq.web.DTOs.Flight.ResultFlightDTO(f.id,d.imageUrl,f.arrivalAirport.city.name,f.arrivalTime,f.departureTime," +
            "G.price,c.name,f.departureTime,f.arrivalTime,f.departureTime,f.arrivalTime,f.departureAirport.name,f.airline.id) FROM Flight f " +
            "JOIN Airport a on a.id=f.departureAirport.id " +
            "JOIN Airport b on b.id=f.arrivalAirport.id " +
            "JOIN Airline c on c.id=f.airline.id "+
            "JOIN Picture d on c.id=d.airlineId "+
            "Join DetailFlight G on f.id=G.idFlight.id "+
            "join City e on e.id=b.city.id "+
            "WHERE f.departureAirport.id= :departureAirport " +
            "AND f.arrivalAirport.id = :arrivalAirport " +
            "AND DATE(f.departureTime) = :departureTime "+
            "And G.type = :TypeFlight "+
            "And Date(f.arrivalTime) = :arrivalTime")
    List<ResultFlightDTO>SearchFindFlightAll( @Param("departureAirport") int departureAirport,
                                              @Param("arrivalAirport") int arrivalAirport,
                                              @Param("departureTime") LocalDate departureTime,
                                              @Param("arrivalTime")LocalDate arrivalTime,
                                              @Param("TypeFlight") String TypeFlight);
}