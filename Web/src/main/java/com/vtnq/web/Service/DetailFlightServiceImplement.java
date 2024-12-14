package com.vtnq.web.Service;

import com.vtnq.web.DTOs.Flight.DetailFlightDTO;
import com.vtnq.web.Entities.DetailFlight;
import com.vtnq.web.Entities.Flight;
import com.vtnq.web.Repositories.DetailFlightRepository;
import com.vtnq.web.Repositories.FlightRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DetailFlightServiceImplement implements DetailFlightService{
    @Autowired
    private DetailFlightRepository detailFlightRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<DetailFlightDTO> findAll(int id) {
     try {
         return modelMapper.map(detailFlightRepository.findByFlightId(id),new TypeToken<List<DetailFlightDTO>>(){}.getType());
     }catch (Exception e){
         e.printStackTrace();
         return null;
     }
    }

    @Override
    public boolean addDetailFlight(DetailFlightDTO detailFlight) {
        try {
            DetailFlight flight = modelMapper.map(detailFlight,DetailFlight.class);
            detailFlightRepository.save(flight);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean existByType(String type, int idFlight) {
      try {
          return detailFlightRepository.existsByTypeAndFlightId(type,idFlight);
      }catch (Exception e){
          e.printStackTrace();
          return false;
      }
    }

    @Override
    public boolean updateDetailFlight(List<DetailFlightDTO> detailFlight) {
        try {
            for (DetailFlightDTO detailFlightDTO : detailFlight) {
                Flight flight = flightRepository.findById(detailFlightDTO.getIdFlight())
                        .orElseThrow(()-> new RuntimeException("Flight not found"));
                DetailFlight flightDTO=modelMapper.map(detailFlightDTO,DetailFlight.class);
                flightDTO.setIdFlight(flight);
                detailFlightRepository.save(flightDTO);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
