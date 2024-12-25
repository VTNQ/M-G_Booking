package com.vtnq.web.Service;

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
            List<Seat> seats = new ArrayList<>();
            int seatsPerRow = 6; // Mỗi dãy có 6 ghế (A, B, C, D, E, F)

            // Lấy tổng số ghế cho mỗi hạng
            int totalFirstClassSeats = seatDTO.getFirstClassSeat();
            int totalBusinessClassSeats = seatDTO.getBusinessClassSeat();
            int totalEconomyClassSeats = seatDTO.getEconomyClassSeat();

            int currentSeatIndex = 1; // Chỉ số ghế bắt đầu từ 1
            int currentRow = 1; // Dùng để theo dõi dãy hiện tại
            char currentColumn = 'A'; // Cột bắt đầu
            int SeatsInRow=0;
            // Tạo ghế cho Hạng Nhất
            int firstClassRowCount = (int) Math.ceil((double) totalFirstClassSeats / seatsPerRow);
            for (int row = 1; row <= firstClassRowCount; row++) {
                for (char letter = currentColumn; letter <= 'F'; letter++) {
                    if (currentSeatIndex > totalFirstClassSeats) {
                        currentColumn = (letter == 'F') ? (char) letter: (char) (letter + 1); // Ghi nhớ cột tiếp theo
                        break; // Nếu ghế đã đủ cho Hạng Nhất, thoát vòng lặp
                    }
                    Flight flight = flightRepository.findById(seatDTO.getIdFlight())
                            .orElseThrow(() -> new Exception("Flight not found"));
                    seats.add(new Seat(letter + Integer.toString(currentRow), "First Class", flight,seatDTO.getPriceClassSeat()));
                    currentSeatIndex++;
                    SeatsInRow++;// Tăng chỉ số ghế sau mỗi lần tạo ghế
                }
                if (SeatsInRow == 6) {
                    currentRow++;
                   SeatsInRow = 0;
                    currentColumn = 'A';
                }

            }

            // Tạo ghế cho Hạng Thương Gia
            int businessClassStartIndex = currentSeatIndex;
            int CurrenRowBusiness=currentRow;
            int businessClassRowCount = (int) Math.ceil((double) totalBusinessClassSeats / seatsPerRow);
            for (int row = currentRow; row <= CurrenRowBusiness + businessClassRowCount; row++) {
                for (char letter = currentColumn; letter <= 'F'; letter++) {
                    if (currentSeatIndex > businessClassStartIndex + totalBusinessClassSeats - 1) {
                        currentColumn = (letter == 'F') ? (char) letter:  (char) (letter + 1); // Ghi nhớ cột tiếp theo
                        break; // Nếu ghế đã đủ cho Hạng Thương Gia, thoát vòng lặp
                    }
                    Flight flight = flightRepository.findById(seatDTO.getIdFlight())
                            .orElseThrow(() -> new Exception("Flight not found"));
                    seats.add(new Seat(letter + Integer.toString(row), "Business Class", flight,seatDTO.getPriceBusinessClassSeat()));
                    currentSeatIndex++;
                    SeatsInRow++;
                }
                if (SeatsInRow == 6) {
                    currentRow++;
                    SeatsInRow = 0;
                    currentColumn = 'A';
                }

            }


            // Tạo ghế cho Hạng Phổ Thông
            int economyClassStartIndex = currentSeatIndex;
            int CurrenRowEconomy=currentRow;
            int economyClassRowCount = (int) Math.ceil((double) totalEconomyClassSeats / seatsPerRow);
            for (int row = currentRow; row <= CurrenRowEconomy + economyClassRowCount; row++) {
                for (char letter = currentColumn; letter <= 'F'; letter++) {
                    if (currentSeatIndex > economyClassStartIndex + totalEconomyClassSeats - 1) {
                        currentColumn = (letter == 'F') ? (char) letter: (char) (letter + 1); // Ghi nhớ cột tiếp theo
                        break; // Nếu ghế đã đủ cho Hạng Phổ Thông, thoát vòng lặp
                    }
                    Flight flight = flightRepository.findById(seatDTO.getIdFlight())
                            .orElseThrow(() -> new Exception("Flight not found"));
                    seats.add(new Seat(letter + Integer.toString(row), "Economy Class", flight,seatDTO.getPriceEconomyClassSeat()));
                    currentSeatIndex++;
                    SeatsInRow++;
                }
                if (SeatsInRow == 6) {
                    currentRow++;
                    SeatsInRow = 0;
                    currentColumn = 'A';
                }
            }

            // Lưu tất cả ghế vào repository
            seatRepository.saveAll(seats);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }



}
