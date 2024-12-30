package com.vtnq.web.Service;

import com.vtnq.web.DTOs.Booking.BookingFlightDTO;
import com.vtnq.web.Entities.Seat;

public interface BookingService {
    public boolean addBooking(BookingFlightDTO bookingFlightDTO,String bookings);
   public Seat getBookedSeatFromBookings(String bookings);
}
