package com.vtnq.web.Repositories;

import com.vtnq.web.Entities.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AmenityRepository extends JpaRepository<Amenity, Integer> {
    @Query("select a from Amenity  a join RoomAmenity b on a.id=b.amenity.id where b.room.id= :roomId")
    List<Amenity>findAllById(int roomId);
}