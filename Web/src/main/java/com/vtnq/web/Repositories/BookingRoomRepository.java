package com.vtnq.web.Repositories;

import com.vtnq.web.DTOs.HistoryOrder.HistoryOrderHotel;
import com.vtnq.web.Entities.BookingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRoomRepository extends JpaRepository<BookingRoom, Integer> {
    @Query(value = "SELECT a.id AS id, b.room.hotel.name AS hotelName, b.room.type.name AS roomType, " +
            "b.checkInDate AS checkInDate, b.checkOutDate AS checkOutDate " +
            "FROM BookingRoom a " +
            "JOIN BookingRoomDetail b ON a.id = b.bookingRoom.id " +
            "WHERE a.user.id = :id " +
            "And (:hotelName IS NULL OR b.room.hotel.name like %:hotelName%) " +
            "and (:checkInDate IS NULL or b.checkInDate >= :checkInDate) " +
            "and (:checkOutDate IS NULL or b.checkOutDate <= :checkOutDate)" +
            "ORDER BY a.id ASC " +
            "LIMIT  :size OFFSET :offset")
    List<Object[]> FindHotelById(@Param("id") int id,@Param("size")int size,@Param("offset")int offset,@Param("hotelName")String hotelName,
                                 @Param("checkInDate")LocalDate checkInDate,@Param("checkOutDate") LocalDate checkOutDate);
    @Query("select count(a) from BookingRoomDetail a where a.bookingRoom.id = :bookingRoomId")
    int CountBookingRoomByRoomId(@Param("bookingRoomId") int bookingRoomId);
}