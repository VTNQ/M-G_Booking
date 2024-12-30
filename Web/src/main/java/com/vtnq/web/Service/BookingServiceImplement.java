package com.vtnq.web.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vtnq.web.DTOs.Booking.BookingFlightDTO;
import com.vtnq.web.DTOs.Booking.BookingFlightDetail;
import com.vtnq.web.DTOs.BookingFlightDto;
import com.vtnq.web.Entities.*;
import com.vtnq.web.Repositories.*;
import com.vtnq.web.WebSocket.SeatUpdateWebSocketHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    private SeatUpdateWebSocketHandler seatUpdateWebSocketHandler;
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
            List<BookingFlightDetail>bookingsDetail=objectMapper.readValue(bookings,objectMapper.getTypeFactory().constructCollectionType(List.class,BookingFlightDetail.class));
            for (BookingFlightDetail bookingFlightDetail : bookingsDetail) {
                Seat seat=seatRepository.findById(bookingFlightDetail.getId())
                        .orElseThrow(()->new RuntimeException("Seat Not Found"));
                if(seat.getStatus()!=null && seat.getStatus()==1){
                    seatUpdateWebSocketHandler.notifySeatStatus(seat,false);
                    return false;
                }
            }
            Account account=accountRepository.findById(bookingFlightDTO.getUserId()).orElseThrow(()->new RuntimeException("Account Not Found"));

            booking.setUser(account);

            booking.setTotalPrice(bookingFlightDTO.getTotalPrice());
            BookingFlight saveBookingFlight=bookingFlightRepository.save(booking);
            for (BookingFlightDetail bookingFlightDetail : bookingsDetail) {
                com.vtnq.web.Entities.BookingFlightDetail bookingFlightDetailEntity=new com.vtnq.web.Entities.BookingFlightDetail();
                BookingFlight bookingFlight=bookingFlightRepository.findById(saveBookingFlight.getId())
                                .orElseThrow(()->new RuntimeException("Booking Flight not found"));
                Flight flight=flightRepository.findById(bookingFlightDetail.getFlightId())
                                .orElseThrow(()->new RuntimeException("Flight not Round"));
                bookingFlightDetailEntity.setFlight(flight);
                bookingFlightDetailEntity.setBookingFlight(bookingFlight);
                bookingFlightDetailEntity.setTotalPrice(bookingFlightDetail.getTotalPrice());
                Seat seat=seatRepository.findById(bookingFlightDetail.getId())
                                .orElseThrow(()->new RuntimeException("Seat Not Found"));

                seat.setStatus(1);
                seatRepository.save(seat);
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

    @Override
    public Seat getBookedSeatFromBookings(String bookings) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<BookingFlightDetail> bookingsDetail = objectMapper.readValue(bookings,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, BookingFlightDetail.class));
            if (!bookingsDetail.isEmpty()) {
                int seatId = bookingsDetail.get(0).getId();  // Assuming 'id' in BookingFlightDetail corresponds to seat id
                Seat seat = seatRepository.findById(seatId).orElse(null);
                return seat;// Assuming the first seat in the booking details is the one you're interested in
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
