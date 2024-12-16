package com.vtnq.web.APIs;

import com.vtnq.web.DTOs.Airport.CountryAiportDTO;
import com.vtnq.web.Service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("AirPortHomeController")
@RequestMapping("api/AirPort")
public class AirPortController {
    @Autowired
    private AirportService airportService;

    @GetMapping(value = "/SearchAirPort")
    public ResponseEntity<List<CountryAiportDTO>> SearchAirPort(@RequestParam String search) {
        try {
            return new ResponseEntity<List<CountryAiportDTO>>(airportService.SearchAirport(search), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<CountryAiportDTO>>(HttpStatus.BAD_REQUEST);
        }
    }
}
