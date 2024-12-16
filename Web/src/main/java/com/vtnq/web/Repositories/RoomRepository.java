package com.vtnq.web.Repositories;

import com.vtnq.web.Entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("SELECT a from Room a where a.hotel.id = :id")
    List<Room>findAll(int id);
    @Query("select a from Room a where a.id = :id")
    Room findById(int id);
}