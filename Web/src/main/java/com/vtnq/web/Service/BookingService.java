package com.vtnq.web.Service;

import com.vtnq.web.DTOs.Booking.BookingFlightDTO;
import com.vtnq.web.DTOs.Booking.BookingHotelDTO;
import com.vtnq.web.Entities.Seat;

import java.math.BigDecimal;

public interface BookingService {
    public boolean addBooking(BookingFlightDTO bookingFlightDTO,String bookings);
   public Seat getBookedSeatFromBookings(String bookings);
   public boolean addBookingHotel(BookingHotelDTO bookingHotelDTO, int QuantityRoom, BookingFlightDTO bookingFlightDTO, String bookings, BigDecimal amount);
}
