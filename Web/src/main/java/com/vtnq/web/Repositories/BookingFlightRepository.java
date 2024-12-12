package com.vtnq.web.Repositories;

import com.vtnq.web.Entities.BookingFlight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingFlightRepository extends JpaRepository<BookingFlight, Integer> {
}