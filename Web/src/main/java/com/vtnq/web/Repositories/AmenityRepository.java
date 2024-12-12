package com.vtnq.web.Repositories;

import com.vtnq.web.Entities.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity, Integer> {
}