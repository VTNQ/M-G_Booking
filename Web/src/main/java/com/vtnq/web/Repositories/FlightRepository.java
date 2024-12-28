package com.vtnq.web.Repositories;

import com.vtnq.web.DTOs.Booking.BookingListFly;
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
        " from Flight f where f.airline.countryId = :id")
    List<FlightListDTO> findFlightListDTO(@Param("id") int id);
    @Query("SELECT MIN(G.price) FROM Flight f Join Seat G on f.id=G.idFlight.id " +
            "where  DATE(f.departureTime) = :departureTime " +
            "and f.departureAirport.id = :departureAirport " +
            "and f.arrivalAirport.id = :arrivalAirport " +
            "and G.type = :TypeFlight" +
            " and (SELECT COUNT(seat) FROM Seat seat WHERE seat.idFlight.id = f.id AND seat.type = :TypeFlight) >= :totalPeople")
    public BigDecimal FindPrice(@Param("departureAirport") int departureAirport,
                                @Param("arrivalAirport") int arrivalAirport,
                                @Param("departureTime") LocalDate departureTime,
                                @Param("TypeFlight") String TypeFlight,
                                @Param("totalPeople")int totalPeople);
    @Query("SELECT distinct new com.vtnq.web.DTOs.Flight.ResultFlightDTO(f.id,d.imageUrl,f.arrivalAirport.city.name,f.arrivalTime,f.departureTime," +
            "G.price,c.name,f.departureTime,f.arrivalTime,f.departureTime,f.arrivalTime,f.departureAirport.name,f.airline.id,f.arrivalAirport.name) FROM Flight f " +
            "JOIN Airport a on a.id=f.departureAirport.id " +
            "JOIN Airport b on b.id=f.arrivalAirport.id " +
            "JOIN Airline c on c.id=f.airline.id "+
            "JOIN Picture d on c.id=d.airlineId "+
            "Join Seat G on f.id=G.idFlight.id "+
            "join City e on e.id=b.city.id "+
            "WHERE f.departureAirport.id= :departureAirport " +
            "AND f.arrivalAirport.id = :arrivalAirport " +
            "AND DATE(f.departureTime) = :departureTime "+
            "And G.type = :TypeFlight " +
            "order by G.price ASC")
    ResultFlightDTO findResulFlightAndHotel(@Param("departureAirport") int departureAirport,
                                            @Param("arrivalAirport") int arrivalAirport,
                                            @Param("departureTime") LocalDate departureTime,
                                            @Param("TypeFlight") String TypeFlight);
    @Query("SELECT distinct new com.vtnq.web.DTOs.Flight.ResultFlightDTO(f.id, d.imageUrl, f.arrivalAirport.city.name, f.arrivalTime, f.departureTime, " +
            "G.price, c.name, f.departureTime, f.arrivalTime, f.departureTime, f.arrivalTime, f.departureAirport.name, f.airline.id, f.arrivalAirport.name) " +
            "FROM Flight f " +
            "JOIN Airport a ON a.id = f.departureAirport.id " +
            "JOIN Airport b ON b.id = f.arrivalAirport.id " +
            "JOIN Airline c ON c.id = f.airline.id " +
            "JOIN Picture d ON c.id = d.airlineId " +
            "LEFT JOIN Seat G ON f.id = G.idFlight.id " +
            "JOIN City e ON e.id = b.city.id " +
            "WHERE f.departureAirport.id = :departureAirport " +
            "AND f.arrivalAirport.id = :arrivalAirport " +
            "AND DATE(f.departureTime) = :departureTime " +
            "AND G.type = :TypeFlight " +
            "AND (SELECT COUNT(seat) FROM Seat seat WHERE seat.idFlight.id = f.id AND seat.type = :TypeFlight) >= :totalPeople")
    List<ResultFlightDTO> findFlightsByAirportsAndDepartureTime(
            @Param("departureAirport") int departureAirport,
            @Param("arrivalAirport") int arrivalAirport,
            @Param("departureTime") LocalDate departureTime,
            @Param("TypeFlight") String TypeFlight ,@Param("totalPeople")int totalPeople);
    @Query("SELECT distinct  new com.vtnq.web.DTOs.Flight.ResultFlightDTO(f.id,d.imageUrl,f.arrivalAirport.city.name,f.arrivalTime,f.departureTime," +
            "G.price,c.name,f.departureTime,f.arrivalTime,f.departureTime,f.arrivalTime,f.departureAirport.name,f.airline.id,f.arrivalAirport.name) FROM Flight f " +
            "JOIN Airport a on a.id=f.departureAirport.id " +
            "JOIN Airport b on b.id=f.arrivalAirport.id " +
            "JOIN Airline c on c.id=f.airline.id "+
            "JOIN Picture d on c.id=d.airlineId "+
            "left Join Seat G on f.id=G.idFlight.id "+
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
    @Query("SELECT  new com.vtnq.web.DTOs.Booking.BookingListFly(f.id,d.imageUrl,a.name,c.name,f.departureTime,f.arrivalTime,f.airline.name,f.arrivalTime,f.departureTime,f.departureAirport.city.name,f.arrivalAirport.city.name) FROM Flight f " +
            "JOIN Airport a on a.id=f.departureAirport.id " +
            "JOIN Airport b on b.id=f.arrivalAirport.id " +
            "JOIN Airline c on c.id=f.airline.id "+
            "JOIN Picture d on c.id=d.airlineId "+
            "WHERE f.id = :id")
    BookingListFly findFlightsByAirportsAndDepartureTime(@Param("id")int id);
}