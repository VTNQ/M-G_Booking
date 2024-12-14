package com.vtnq.web.Service;

import com.vtnq.web.DTOs.Airport.AirportDto;
import com.vtnq.web.Entities.Airport;
import com.vtnq.web.Entities.City;
import com.vtnq.web.Repositories.AirportRepository;
import com.vtnq.web.Repositories.CityRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportServiceImplement implements AirportService {
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private AirportRepository airportRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public boolean save(AirportDto airportDto) {
        try{
            City city=cityRepository.findById(airportDto.getIdCity())
                    .orElse(new City());
            Airport airport=modelMapper.map(airportDto,Airport.class);
            airport.setCity(city);
            Airport insertAirport=airportRepository.save(airport);
            return insertAirport!=null && insertAirport.getId()!=null;
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public List<Airport> findAll(int id) {
        try{
            return modelMapper.map(airportRepository.findByCountry(id),new TypeToken<List<Airport>>(){}.getType());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean existByName(String name) {
        return airportRepository.existsByName(name);
    }

    @Override
    public AirportDto findById(int id) {
        try {
            return modelMapper.map(airportRepository.findById(id),new TypeToken<AirportDto>(){}.getType());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Airport> findAll() {
        try {
            return airportRepository.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
