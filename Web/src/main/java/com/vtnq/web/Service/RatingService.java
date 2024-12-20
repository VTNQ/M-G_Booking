package com.vtnq.web.Service;

import com.vtnq.web.Entities.Rating;

public interface RatingService {
    public boolean addRating(Rating rating);
    public double getAverageRating(int id);
}
