package com.vtnq.web.Repositories;

import com.vtnq.web.Entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
}