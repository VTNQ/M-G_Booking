package com.vtnq.web.APIs;

import com.vtnq.web.DTOs.Flight.SearchFlightDTO;
import com.vtnq.web.Entities.Flight;
import com.vtnq.web.Service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("api/Flight")
public class FlightAPI {

    @Autowired
    private FlightService flightService;

    @GetMapping("/All")
    public ResponseEntity<Object> getAllFlight(@RequestBody SearchFlightDTO flight) {
        Map<String,Object> response=new LinkedHashMap<>();
        try {
            if(flightService.SearchFlight(flight.getDepartureAirport(),flight.getArrivalAirport(), LocalDate.parse(flight.getDepartureTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")), flight.getTypeFlight())){
                response.put("status",200);
                response.put("message","Success");
                response.put("data",flightService.SearchFlight(flight.getDepartureAirport(),flight.getArrivalAirport(), LocalDate.parse(flight.getDepartureTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd")), flight.getTypeFlight()));
                return ResponseEntity.ok(response);
            }else {
                response.put("status",400);
                response.put("message","Fail");
                return ResponseEntity.badRequest().body(response);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
