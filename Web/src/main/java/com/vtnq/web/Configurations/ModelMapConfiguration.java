package com.vtnq.web.Configurations;

import com.vtnq.web.DTOs.Account.RegisterUser;
import com.vtnq.web.DTOs.Airport.AirportDto;
import com.vtnq.web.Entities.Account;
import com.vtnq.web.Entities.Airport;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;

@Configuration
public class ModelMapConfiguration {
@Bean
public ModelMapper modelMap() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.typeMap(Airport.class, AirportDto.class).addMappings(mapping -> {
            mapping.map(airport->airport.getCity().getId(),AirportDto::setIdCity);
    });
return modelMapper;
}
}
