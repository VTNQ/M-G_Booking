package com.vtnq.web.Configurations;

import com.vtnq.web.DTOs.Airport.AirportDto;
import com.vtnq.web.DTOs.Room.RoomDTO;
import com.vtnq.web.DTOs.Service.ServiceDTO;
import com.vtnq.web.Entities.Airport;
import com.vtnq.web.Entities.Room;
import com.vtnq.web.Entities.Service;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapConfiguration {
@Bean
public ModelMapper modelMap() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.typeMap(Airport.class, AirportDto.class).addMappings(mapping -> {
            mapping.map(airport->airport.getCity().getId(),AirportDto::setIdCity);
    });
    modelMapper.typeMap(Service.class, ServiceDTO.class).addMappings(mapping -> {
        mapping.map(service->service.getHotel().getId(),ServiceDTO::setHotelId);
    });
    modelMapper.typeMap(Room.class, RoomDTO.class).addMappings(mapping -> {
        mapping.map(rooms->rooms.getHotel().getId(),RoomDTO::setIdHotel);
    });
return modelMapper;
}
}
