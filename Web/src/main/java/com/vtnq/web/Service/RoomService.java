package com.vtnq.web.Service;

import com.vtnq.web.DTOs.Room.RoomDTO;
import com.vtnq.web.Entities.Room;

import java.math.BigDecimal;
import java.util.List;

public interface RoomService {
    public boolean addRoom(List<String>roomTypes, List<BigDecimal>roomPrices, List<Integer>roomCapacities, int idHotel);
    public List<Room>findAll(int id);
    public RoomDTO findById(int id);
    public boolean update(RoomDTO roomDTO);
    public boolean delete(int id);
}
