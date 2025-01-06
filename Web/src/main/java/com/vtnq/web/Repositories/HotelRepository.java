package com.vtnq.web.Repositories;

import com.vtnq.web.DTOs.Booking.BookingHotel;
import com.vtnq.web.DTOs.Hotel.*;
import com.vtnq.web.Entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    @Query("select new com.vtnq.web.DTOs.Hotel.HotelListDto(a.id,a.name,a.district.name,c.imageUrl) from Hotel a join HotelsOwner b on a.id=b.hotel.id " +
            "JOIN Picture c on c.hotelId=a.id " +
            "where b.owner.id = :id and c.isMain=true")
    List<HotelListDto> FindHotelByHotelId(@Param("id") int id);

    @Query("select new com.vtnq.web.DTOs.Hotel.HotelUpdateDTO(a.id,a.name,a.address,a.cityId,a.decription,b.owner.id,a.district.id,c.imageUrl) from Hotel a join HotelsOwner b on a.id=b.hotel.id " +
            "join Picture c on c.hotelId=a.id where a.id = :id and c.isMain=true")
    HotelUpdateDTO findHotelById(int id);

    @Query("select new com.vtnq.web.DTOs.Hotel.HotelSearchDTO" +
            "(a.id, a.name, c.name, c.country.name, b.imageUrl) " +
            "from Hotel a " +
            "join Picture b on a.id = b.hotelId " +
            "join City c on a.cityId = c.id " +
            "join Room d on d.hotel.id = a.id " +
            "where a.cityId = :id and d.status=false " +
            "group by a.id, a.name, c.name, c.country.name, b.imageUrl " +
            "having count(d.id) >= :quantityRoom and min(d.price) between  :minPrice and :maxPrice")
    List<HotelSearchDTO> SearchHotel(@Param("id") int id, @Param("quantityRoom") int quantityRoom,@Param("minPrice")BigDecimal minPrice,@Param("maxPrice")BigDecimal maxPrice);
    @Query("SELECT min(d.price) " +
            "FROM Hotel a " +
            "JOIN Picture b ON a.id = b.hotelId " +
            "JOIN City c ON a.cityId = c.id " +
            "JOIN Room d ON d.hotel.id = a.id " +
            "WHERE a.cityId = :id AND d.status = false " +
            "GROUP BY a.id " +
            "HAVING count(d.id) >= :quantityRoom")
    BigDecimal FindMinHotel(@Param("id") int id, @Param("quantityRoom") int quantityRoom);
    @Query("SELECT max(d.price) " +
            "FROM Hotel a " +
            "JOIN Picture b ON a.id = b.hotelId " +
            "JOIN City c ON a.cityId = c.id " +
            "JOIN Room d ON d.hotel.id = a.id " +
            "WHERE a.cityId = :id AND d.status = false " +
            "GROUP BY a.id " +
            "HAVING count(d.id) >= :quantityRoom")
    BigDecimal FindMaxHotel(@Param("id") int id, @Param("quantityRoom") int quantityRoom);
    @Query("select new com.vtnq.web.DTOs.Hotel.ShowDetailHotel(a.id,a.name,a.address,b.name,a.decription,b.country.name,c.price) from Hotel a join City b on a.cityId=b.id join Room c on a.id=c.hotel.id  where a.id = :id order by c.price desc")
    ShowDetailHotel showDetailHotel(@Param("id") int id);

    @Query("select new com.vtnq.web.DTOs.Booking.BookingHotel(c.id,a.name,b.name,c.type.name,d.imageUrl,c.price) " +
            "from Hotel a " +
            "join City b on a.cityId=b.id " +
            "join Room c on a.id=c.hotel.id " +
            "join Picture d on d.hotelId=a.id " +
            "where c.type.id = :id")
    BookingHotel FindBookingHotel(@Param("id") int id);

    @Query("SELECT new com.vtnq.web.DTOs.Hotel.HotelList(a.id, d.name, d.country.name, b.imageUrl, COALESCE(AVG(c.rating), 0), a.name) " +
            "FROM Hotel a " +
            "LEFT JOIN Picture b ON a.id = b.hotelId " +
            "LEFT JOIN Rating c ON c.hotel.id = a.id " +
            "LEFT JOIN City d ON d.id = a.cityId " +
            "WHERE a.id != :id " +
            "GROUP BY a.id, d.name, d.country.name, b.imageUrl, a.name")
    List<HotelList> showHotelList(@Param("id") int id);
}