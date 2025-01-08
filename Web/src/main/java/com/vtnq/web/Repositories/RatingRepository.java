package com.vtnq.web.Repositories;

import com.vtnq.web.DTOs.Rating.RatingDTO;
import com.vtnq.web.Entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
   @Query("select avg(a.rating) from Rating a where a.hotel.id = :id")
    double AvgRatingByHotel(int id);
   @Query("SELECT new com.vtnq.web.DTOs.Rating.RatingDTO(a.id,b.avatar,b.fullName,a.content,a.rating,a.createdAt) from Rating a join Account b on a.userId=b.id " +
           "where a.hotel.id= :id")
   public List<RatingDTO>findRatingByHotelId(int id);
}