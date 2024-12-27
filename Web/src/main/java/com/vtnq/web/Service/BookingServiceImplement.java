package com.vtnq.web.Service;

import com.vtnq.web.DTOs.Booking.BookingFlightDTO;
import com.vtnq.web.DTOs.BookingFlightDto;
import com.vtnq.web.Entities.*;
import com.vtnq.web.Repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImplement implements BookingService {
    @Autowired
    private BookingFlightRepository bookingRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public boolean addBooking(BookingFlightDTO bookingFlightDTO) {
        try {
            BookingFlight booking=new BookingFlight();
            Flight flight=flightRepository.findById(bookingFlightDTO.getFlightId()).orElseThrow(()->new RuntimeException("Flight not found"));
            Seat seat=seatRepository.findById(bookingFlightDTO.getSeatId()).orElseThrow(()->new RuntimeException("Seat Not Found"));
            Account account=accountRepository.findById(bookingFlightDTO.getUserId()).orElseThrow(()->new RuntimeException("Account Not Found"));
            booking.setSeatTrip(seat);
            booking.setUser(account);
            booking.setTotalPrice(bookingFlightDTO.getTotalPrice());
            booking.setFlight(flight);
            booking.setFlightIdTrip(flight);
            bookingRepository.save(booking);
            return true;

        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
