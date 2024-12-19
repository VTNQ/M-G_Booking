package com.vtnq.web.APIs;

import com.vtnq.web.Entities.City;
import com.vtnq.web.Service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/city")
public class CityController {
    @Autowired
    private CityService cityService;
    @GetMapping(value = "SearchHotelByCityOrHotel")
    public ResponseEntity<List<City>> SearchHotel(@RequestParam("name")String name) {
        try {
            return new ResponseEntity<List<City>>(cityService.SearchCityOrCountry(name), HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<City>>(HttpStatus.BAD_REQUEST);
        }
    }
}
