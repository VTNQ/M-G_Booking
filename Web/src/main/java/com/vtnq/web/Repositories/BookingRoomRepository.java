package com.vtnq.web.Repositories;

import com.vtnq.web.DTOs.HistoryOrder.HistoryOrderHotel;
import com.vtnq.web.Entities.BookingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRoomRepository extends JpaRepository<BookingRoom, Integer> {
    @Query("SELECT new com.vtnq.web.DTOs.HistoryOrder.HistoryOrderHotel(a.id,b.room.hotel.name,b.room.type.name,b.checkInDate,b.checkOutDate) from BookingRoom a join BookingRoomDetail b on a.id=b.bookingRoom.id where a.user.id = :id")
    List<HistoryOrderHotel> FindHotelById(@Param("id") int id);
    @Query("select count(a) from BookingRoomDetail a where a.bookingRoom.id = :bookingRoomId")
    int CountBookingRoomByRoomId(@Param("bookingRoomId") int bookingRoomId);
}