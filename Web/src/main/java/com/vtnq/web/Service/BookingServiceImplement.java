package com.vtnq.web.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtnq.web.DTOs.Booking.BookingFlightDTO;
import com.vtnq.web.DTOs.Booking.BookingFlightDetail;
import com.vtnq.web.DTOs.BookingFlightDto;
import com.vtnq.web.Entities.*;
import com.vtnq.web.Repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class BookingServiceImplement implements BookingService {
    @Autowired
    private BookingFlightRepository bookingFlightRepository;
    @Autowired
    private BookingFlightDetailRepository bookingFlightDetailRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public boolean addBooking(BookingFlightDTO bookingFlightDTO,String bookings) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BookingFlight booking=new BookingFlight();
            Flight flight=flightRepository.findById(bookingFlightDTO.getFlightId()).orElseThrow(()->new RuntimeException("Flight not found"));

            Account account=accountRepository.findById(bookingFlightDTO.getUserId()).orElseThrow(()->new RuntimeException("Account Not Found"));
            List<BookingFlightDetail>bookingsDetail=objectMapper.readValue(bookings,objectMapper.getTypeFactory().constructCollectionType(List.class,BookingFlightDetail.class));
            booking.setUser(account);
            booking.setFlight(flight);
            booking.setTotalPrice(bookingFlightDTO.getTotalPrice());
            BookingFlight saveBookingFlight=bookingFlightRepository.save(booking);
            for (BookingFlightDetail bookingFlightDetail : bookingsDetail) {
                com.vtnq.web.Entities.BookingFlightDetail bookingFlightDetailEntity=new com.vtnq.web.Entities.BookingFlightDetail();
                BookingFlight bookingFlight=bookingFlightRepository.findById(saveBookingFlight.getId())
                                .orElseThrow(()->new RuntimeException("Booking Flight not found"));
                bookingFlightDetailEntity.setBookingFlight(bookingFlight);
                bookingFlightDetailEntity.setTotalPrice(bookingFlightDetail.getTotalPrice());
                Seat seat=seatRepository.findById(bookingFlightDetail.getId())
                                .orElseThrow(()->new RuntimeException("Seat Not Found"));
                bookingFlightDetailEntity.setSeat(seat);
                bookingFlightDetailRepository.save(bookingFlightDetailEntity);
            }
            Booking bookingFlight=new Booking();
            BookingFlight bookingFlightEntity=bookingFlightRepository.findById(saveBookingFlight.getId())
                    .orElseThrow(()->new RuntimeException("Booking Flight not found"));
            bookingFlight.setBookingFlight(bookingFlightEntity);
            bookingFlight.setCreatedAt(Instant.now());
            bookingRepository.save(bookingFlight);
            return true;

        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
