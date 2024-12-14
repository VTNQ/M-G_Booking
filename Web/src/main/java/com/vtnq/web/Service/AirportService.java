package com.vtnq.web.Service;

import com.vtnq.web.DTOs.Airport.AirportDto;
import com.vtnq.web.Entities.Airport;

import java.util.List;

public interface AirportService {
    public boolean save(AirportDto airportDto);
    public List<Airport>findAll(int id);
    public boolean existByName(String name);
    public AirportDto findById(int id);
}
