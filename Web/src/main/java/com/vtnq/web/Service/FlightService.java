package com.vtnq.web.Service;

import com.vtnq.web.DTOs.Flight.FlightDto;
import com.vtnq.web.DTOs.Flight.FlightListDTO;

import java.util.List;

public interface FlightService  {
    public boolean save(FlightDto flightDto);
    public List<FlightListDTO>findAllByCountry(int id);
}
