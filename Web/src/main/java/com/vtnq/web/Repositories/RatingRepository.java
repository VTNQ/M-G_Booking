package com.vtnq.web.Repositories;

import com.vtnq.web.Entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
   @Query("select avg(a.rating) from Rating a where a.hotel.id = :id")
    double AvgRatingByHotel(int id);
}