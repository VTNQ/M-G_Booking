package com.vtnq.web.Repositories;

import com.vtnq.web.DTOs.Hotel.HotelListDto;
import com.vtnq.web.DTOs.Hotel.HotelSearchDTO;
import com.vtnq.web.DTOs.Hotel.HotelUpdateDTO;
import com.vtnq.web.Entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
@Query("select new com.vtnq.web.DTOs.Hotel.HotelListDto(a.id,a.name,a.district.name,c.imageUrl) from Hotel a join HotelsOwner b on a.id=b.hotel.id " +
        "JOIN Picture c on c.hotelId=a.id " +
        "where b.owner.id = :id and c.isMain=true")
    List<HotelListDto>FindHotelByHotelId(@Param("id")int id);
@Query("select new com.vtnq.web.DTOs.Hotel.HotelUpdateDTO(a.id,a.name,a.address,a.cityId,a.decription,b.owner.id,a.district.id,c.imageUrl) from Hotel a join HotelsOwner b on a.id=b.hotel.id " +
        "join Picture c on c.hotelId=a.id where a.id = :id and c.isMain=true")
HotelUpdateDTO findHotelById(int id);
@Query("select new com.vtnq.web.DTOs.Hotel.HotelSearchDTO" +
        "(a.id,a.name,c.name,c.country.name,b.imageUrl) from Hotel a join Picture b on a.id=b.hotelId join  City c on a.cityId=c.id where a.cityId = :id")
    List<HotelSearchDTO>SearchHotel(@Param("id") int id);
}