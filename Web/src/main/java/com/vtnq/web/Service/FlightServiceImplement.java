package com.vtnq.web.Service;

import com.vtnq.web.DTOs.Booking.BookingListFly;
import com.vtnq.web.DTOs.Flight.FlightDto;
import com.vtnq.web.DTOs.Flight.FlightListDTO;
import com.vtnq.web.DTOs.Flight.ResultFlightDTO;
import com.vtnq.web.DTOs.Seat.SeatDTO;
import com.vtnq.web.Entities.*;
import com.vtnq.web.Repositories.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class FlightServiceImplement implements FlightService{
    @Autowired
    private AirlineRepository airlineRepository;
    @Autowired
    private AirportRepository airportRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public boolean save(FlightDto flightDto) {
        try {
            Airline airline=airlineRepository.findById(flightDto.getAirline_id())
                    .orElseThrow(() -> new Exception("Airline not found"));
            Airport depature_AirPort=airportRepository.findById(flightDto.getDeparture_airport())
                    .orElseThrow(() -> new Exception("Airport not found"));
            Airport arrival_AirPort=airportRepository.findById(flightDto.getArrival_airport())
                    .orElseThrow(() -> new Exception("Airport not found"));
            Flight flight=modelMapper.map(flightDto, Flight.class);
            flight.setArrivalTime(flightDto.getArrivalInstant());
            flight.setDepartureTime(flightDto.getDepartureInstant());
            flight.setAirline(airline);
            flight.setDepartureAirport(depature_AirPort);
            flight.setArrivalAirport(arrival_AirPort);
            Flight insertFlight=flightRepository.save(flight);


            return insertFlight!=null && insertFlight.getId()>0 ;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<FlightListDTO> findAllByCountry(int id) {
        return flightRepository.findFlightListDTO(id);
    }

    @Override
    public FlightDto findById(int id) {
        try {
            return modelMapper.map(flightRepository.findById(id), new TypeToken<FlightDto>() {}.getType());
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean UpdateFlightDto(FlightDto flightDto) {
        try {
        Airline airline=airlineRepository.findById(flightDto.getAirline_id())
                .orElseThrow(() -> new Exception("Airline not found"));
        Airport departure_airport=airportRepository.findById(flightDto.getDeparture_airport())
                .orElseThrow(() -> new Exception("Departure Airport not found"));
        Airport arrival_airport=airportRepository.findById(flightDto.getArrival_airport())
                .orElseThrow(() -> new Exception("Arrival Airport not found"));
        Flight flight=modelMapper.map(flightDto, Flight.class);
        flight.setArrivalTime(flightDto.getArrivalInstant());
        flight.setDepartureTime(flightDto.getDepartureInstant());
        flight.setAirline(airline);
        flight.setDepartureAirport(departure_airport);
        flight.setArrivalAirport(arrival_airport);
        Flight updateFlight=flightRepository.save(flight);
        return updateFlight!=null && updateFlight.getId()>0;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public BigDecimal FindPrice(LocalDate departureTime) {
        try {
            return modelMapper.map(flightRepository.FindPrice(departureTime), new TypeToken<BigDecimal>(){}.getType());
        }catch (Exception e) {
            e.printStackTrace();
            return BigDecimal.ZERO;
        }
    }

    @Override
    public List<ResultFlightDTO> SearchFlight(int departureAirport, int arrivalAirport, LocalDate departureTime, String TypeFlight) {
        try {
            return flightRepository.findFlightsByAirportsAndDepartureTime(departureAirport,arrivalAirport,departureTime,TypeFlight);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }

    }

    @Override
    public List<ResultFlightDTO> SearchFlightAllDto(int departureAirport, int arrivalAirport, LocalDate departureTime, LocalDate ArrivalTime, String TypeFlight) {
        try {
            List<ResultFlightDTO> flight=flightRepository.SearchFindFlightAll(departureAirport,arrivalAirport,departureTime,ArrivalTime,TypeFlight);

            return modelMapper.map(flight,new TypeToken<List<ResultFlightDTO>>(){}.getType());
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ResultFlightDTO FindResultFlightAndHotel(int departureAirport, int arrivalAirport, LocalDate departureTime, String TypeFlight) {
        try {
            return flightRepository.findResulFlightAndHotel(departureAirport,arrivalAirport,departureTime,TypeFlight);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override

    public boolean CreateSeat(SeatDTO seatDTO) {
        try {
            int seatsPerRow = 6;

            // Lấy tổng số ghế cho mỗi hạng
            int totalFirstClassSeats = seatDTO.getFirstClassSeat();
            int totalBusinessClassSeats = seatDTO.getBusinessClassSeat();
            int totalEconomyClassSeats = seatDTO.getEconomyClassSeat();

            ExecutorService executor = Executors.newFixedThreadPool(3); // Sử dụng 3 threads

            CompletableFuture<List<Seat>> firstClassFuture = CompletableFuture.supplyAsync(() -> {
                return createSeatsForClass(1, totalFirstClassSeats, "First Class", seatDTO.getIdFlight(), seatDTO.getPriceClassSeat());
            }, executor);

            CompletableFuture<List<Seat>> businessClassFuture = CompletableFuture.supplyAsync(() -> {
                int startSeat = totalFirstClassSeats + 1;
                int endSeat = startSeat + totalBusinessClassSeats - 1;
                return createSeatsForClass(startSeat, endSeat, "Business Class", seatDTO.getIdFlight(), seatDTO.getPriceBusinessClassSeat());
            }, executor);

            CompletableFuture<List<Seat>> economyClassFuture = CompletableFuture.supplyAsync(() -> {
                int startSeat = totalFirstClassSeats + totalBusinessClassSeats + 1;
                int endSeat = startSeat + totalEconomyClassSeats - 1;
                return createSeatsForClass(startSeat, endSeat, "Economy Class", seatDTO.getIdFlight(), seatDTO.getPriceEconomyClassSeat());
            }, executor);

            CompletableFuture.allOf(firstClassFuture, businessClassFuture, economyClassFuture).join();

            List<Seat> allSeats = new ArrayList<>();
            allSeats.addAll(firstClassFuture.join());
            allSeats.addAll(businessClassFuture.join());
            allSeats.addAll(economyClassFuture.join());

            seatRepository.saveAll(allSeats);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private List<Seat> createSeatsForClass(int startSeatIndex, int endSeatIndex, String seatClass, int flightId, BigDecimal price) {
        List<Seat> seats = new ArrayList<>();
        int seatsPerRow = 6;
        int currentRow = (int) Math.ceil((double) startSeatIndex / seatsPerRow);
        int seatNumberInRow = (startSeatIndex - 1) % seatsPerRow;
        char currentColumn = (char) ('A' + seatNumberInRow);

        for (int i = startSeatIndex; i <= endSeatIndex; i++) {
            Flight flight = flightRepository.findById(flightId).orElse(null);
            seats.add(new Seat(currentColumn + Integer.toString(currentRow), seatClass, flight, price));

            currentColumn++;
            if (currentColumn > 'F') {
                currentColumn = 'A';
                currentRow++;
            }
        }
        return seats;
    }
    @Override
    public Flight getFlight(int id) {
        return flightRepository.findById(id).get();
    }

    @Override
    public BookingListFly getResultPaymentFlight(int id) {
        try {
            return flightRepository.findFlightsByAirportsAndDepartureTime(id);
        }catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


}
