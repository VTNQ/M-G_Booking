package com.vtnq.web.Service;

import com.vtnq.web.DTOs.Booking.BookingFlightDTO;
import com.vtnq.web.DTOs.Booking.BookingHotelDTO;
import com.vtnq.web.Entities.Booking;
import com.vtnq.web.Entities.BookingFlightDetail;
import com.vtnq.web.Entities.BookingRoomDetail;
import com.vtnq.web.Entities.Seat;

import java.math.BigDecimal;
import java.util.List;

public interface BookingService {
    public boolean addBooking(BookingFlightDTO bookingFlightDTO,String bookings);
   public Seat getBookedSeatFromBookings(String bookings);
   public boolean addBookingHotel(BookingHotelDTO bookingHotelDTO, int QuantityRoom, BookingFlightDTO bookingFlightDTO, String bookings, BigDecimal amount);
   public List<Booking>FindBookings(int id);
   public List<BookingFlightDetail>findBookingFlights(int id);
   public BigDecimal getTotalPrice(int id);
   public List<BookingRoomDetail>getBookingRooms(int id);
   public BigDecimal GetTotalPriceHotel(int id);
   public int CountBookings(int id);
}
