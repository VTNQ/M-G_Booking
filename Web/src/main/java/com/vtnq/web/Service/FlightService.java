package com.vtnq.web.Service;

import com.vtnq.web.DTOs.Booking.BookingListFly;
import com.vtnq.web.DTOs.Flight.FlightDto;
import com.vtnq.web.DTOs.Flight.FlightListDTO;
import com.vtnq.web.DTOs.Flight.ResultFlightDTO;
import com.vtnq.web.DTOs.Seat.SeatDTO;
import com.vtnq.web.Entities.Flight;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FlightService  {
    public boolean save(FlightDto flightDto);
    public List<FlightListDTO>findAllByCountry(int id);
    public FlightDto findById(int id);
    public boolean UpdateFlightDto(FlightDto flightDto);
    public BigDecimal FindPrice(int departureAirport,int arrivalAirport
            ,LocalDate departureTime,String TypeFlight,int totalPeople  );
    public List<ResultFlightDTO>SearchFlight(int departureAirport, int arrivalAirport, LocalDate departureTime, String TypeFlight, int totalPeople, LocalDateTime currentTime);

    public List<ResultFlightDTO>SearchFlightAllDto(int departureAirport, int arrivalAirport, LocalDate departureTime,LocalDate ArrivalTime,String TypeFlight);
    public ResultFlightDTO FindResultFlightAndHotel(int departureAirport, int arrivalAirport, LocalDate departureTime, String TypeFlight);
    public boolean CreateSeat(SeatDTO seatDTO);
    public BigDecimal FindMinPriceDeparture(int departureAirport, int arrivalAirport, LocalDate departureTime, String TypeFlight,int totalPeople);
    public BigDecimal FindMaxPriceDeparture(int departureAirport, int arrivalAirport, LocalDate departureTime, String TypeFlight,int totalPeople);
    public BookingListFly getResultPaymentFlight(int id);
    public ResultFlightDTO FindByIdFlight(int id);
    public List<ResultFlightDTO>FindArrivalTime(int departureAirport,int arrivalAirport,LocalDate arrivalTime,String TypeFlight,int totalPeople);
    public BigDecimal FindMinPriceArrivalTime(int departureAirport,int arrivalAirport,LocalDate arrivalTime,String TypeFlight,int totalPeople);
    public int CountFlight(int id);
    public BigDecimal FindMaxPriceArrivalTime(int departureAirport,int arrivalAirport,LocalDate arrivalTime,String TypeFlight,int totalPeople);
 }
