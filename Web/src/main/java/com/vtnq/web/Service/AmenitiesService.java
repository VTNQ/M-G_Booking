package com.vtnq.web.Service;

import com.vtnq.web.DTOs.AmenityDto;
import com.vtnq.web.Entities.Amenity;

import java.util.List;


public interface AmenitiesService {
    public boolean addAmenity(AmenityDto amenity);
    public List<Amenity>findAll(int id);
    public AmenityDto findById(int id);
    public boolean update(AmenityDto amenity);
    public boolean delete(int id);
}
