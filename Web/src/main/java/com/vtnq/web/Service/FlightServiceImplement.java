package com.vtnq.web.Service;

import com.vtnq.web.DTOs.Flight.DetailFlightDTO;
import com.vtnq.web.DTOs.Flight.FlightDto;
import com.vtnq.web.DTOs.Flight.FlightListDTO;
import com.vtnq.web.Entities.Airline;
import com.vtnq.web.Entities.Airport;
import com.vtnq.web.Entities.DetailFlight;
import com.vtnq.web.Entities.Flight;
import com.vtnq.web.Repositories.AirlineRepository;
import com.vtnq.web.Repositories.AirportRepository;
import com.vtnq.web.Repositories.DetailFlightRepository;
import com.vtnq.web.Repositories.FlightRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImplement implements FlightService{
    @Autowired
    private AirlineRepository airlineRepository;
    @Autowired
    private AirportRepository airportRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private DetailFlightRepository detailFlightRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public boolean save(FlightDto flightDto) {
        try {
            Airline airline=airlineRepository.findById(flightDto.getAirline_id())
                    .orElseThrow(() -> new Exception("Airline not found"));
            Airport depature_AirPort=airportRepository.findById(flightDto.getDeparture_airport())
                    .orElseThrow(() -> new Exception("Airport not found"));
            Airport arrival_AirPort=airportRepository.findById(flightDto.getArrival_airport())
                    .orElseThrow(() -> new Exception("Airport not found"));
            Flight flight=modelMapper.map(flightDto, Flight.class);
            flight.setArrivalTime(flightDto.getArrivalInstant());
            flight.setDepartureTime(flightDto.getDepartureInstant());
            flight.setAirline(airline);
            flight.setDepartureAirport(depature_AirPort);
            flight.setArrivalAirport(arrival_AirPort);
            Flight insertFlight=flightRepository.save(flight);
            DetailFlight detailFlight=new DetailFlight();
            for (DetailFlightDTO detailFlightDTO:flightDto.getDetailFlights()){
                try {
                detailFlight.setType(detailFlightDTO.getType());
                detailFlight.setIdFlight(insertFlight);
                detailFlight.setPrice(detailFlightDTO.getPrice());
                detailFlight.setQuantity(detailFlightDTO.getQuantity());
                detailFlightRepository.save(detailFlight);
                detailFlight=new DetailFlight();
                }catch (Exception e){
                    e.printStackTrace();

                }
            }
            return insertFlight!=null && insertFlight.getId()>0 ;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<FlightListDTO> findAllByCountry(int id) {
        return flightRepository.findFlightListDTO(id);
    }
}
