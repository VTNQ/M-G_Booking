package com.vtnq.web.Repositories;

import com.vtnq.web.Entities.BookingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRoomRepository extends JpaRepository<BookingRoom, Integer> {
}