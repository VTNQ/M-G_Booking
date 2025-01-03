package com.vtnq.web.Repositories;

import com.vtnq.web.Controllers.HistoryOrder;
import com.vtnq.web.DTOs.HistoryOrder.HistoryOrderFlight;
import com.vtnq.web.Entities.BookingFlight;
import com.vtnq.web.Entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingFlightRepository extends JpaRepository<BookingFlight, Integer> {
    @Query("select distinct new com.vtnq.web.DTOs.HistoryOrder.HistoryOrderFlight(a.id,a.flight.flightCode,a.flight.arrivalTime,a.flight.departureTime,a.flight.departureAirport.name,a.flight.arrivalAirport.name) from BookingFlightDetail a where a.bookingFlight.user.id = :id")
    List<HistoryOrderFlight>FindFlightByUser(int id);
    @Query("select a from Seat a join BookingFlightDetail b on a.id=b.seat.id where b.bookingFlight.id = :id")
    List<Seat>FindSeatByBookingFlight(int id);
}