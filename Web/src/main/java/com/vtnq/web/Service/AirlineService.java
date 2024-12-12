package com.vtnq.web.Service;

import com.vtnq.web.DTOs.Airline.AirlineDto;
import com.vtnq.web.DTOs.Airline.ListAirlineDto;
import com.vtnq.web.DTOs.Airline.UpdateAirlineDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AirlineService {
    public boolean addAirline(AirlineDto airlineDto);
    public boolean existName(String airlineName);
    public List<ListAirlineDto>findAll();
    public UpdateAirlineDTO findAirlineById(int id);
    public boolean updateArline(UpdateAirlineDTO updateAirlineDTO, MultipartFile file);
}
