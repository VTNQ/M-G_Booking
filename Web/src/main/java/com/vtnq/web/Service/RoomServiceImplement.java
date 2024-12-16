package com.vtnq.web.Service;

import com.vtnq.web.DTOs.Room.RoomDTO;
import com.vtnq.web.Entities.Hotel;
import com.vtnq.web.Entities.Room;
import com.vtnq.web.Repositories.HotelRepository;
import com.vtnq.web.Repositories.RoomRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
public class RoomServiceImplement implements RoomService{
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public boolean addRoom(List<String> roomTypes, List<BigDecimal> roomPrices, List<Integer> roomCapacities, int idHotel) {
        try {
            Hotel hotel=hotelRepository.findById(idHotel)
                    .orElseThrow(() -> new Exception("Hotel Not Found"));
            for (int i = 0; i < roomTypes.size(); i++) {
                Room room=new Room();
                room.setType(roomTypes.get(i));
                room.setPrice(roomPrices.get(i));
                room.setOccupancy(roomCapacities.get(i));
                room.setHotel(hotel);
                roomRepository.save(room);
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Room> findAll(int id) {
        try {
            return roomRepository.findAll(id);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public RoomDTO findById(int id) {
        try {
            return modelMapper.map(roomRepository.findById(id), new TypeToken<RoomDTO>(){}.getType());
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(RoomDTO roomDTO) {
        try {
            Hotel hotel=hotelRepository.findById(roomDTO.getIdHotel())
                    .orElseThrow(() -> new Exception("Hotel Not Found"));
            Room room=modelMapper.map(roomDTO, Room.class);
            room.setHotel(hotel);
            roomRepository.save(room);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            roomRepository.deleteById(id);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
