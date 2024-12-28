package com.vtnq.web.Controllers;

import com.vtnq.web.DTOs.Flight.SearchFlightDTO;
import com.vtnq.web.Service.AirlineService;
import com.vtnq.web.Service.FlightService;
import com.vtnq.web.Service.HotelService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping({"","/"})
public class HomeController {
    @Autowired
    private FlightService flightService;
    @Autowired
    private AirlineService airlineService;
    @Autowired
    private HotelService hotelService;
    @GetMapping("Home")
    public String Home(HttpServletRequest request, ModelMap model) {
       try {
           model.put("Search",new SearchFlightDTO());
           return "User/Home/Home";
       }catch (Exception e) {
           e.printStackTrace();
           return null;
       }
    }
    public String SearchHotel(ModelMap model,@ModelAttribute("Search")SearchFlightDTO search) {
        try {
            String sanitizedDepartureTime = search.getDepartureTime().replaceAll("[^\\d-]", "");

            model.put("Hotel",hotelService.SearchHotels(search.getIdCity(),search.getQuantityRoom()));
            DateTimeFormatter formatterDepartureDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate departureDate = LocalDate.parse(sanitizedDepartureTime, formatterDepartureDate);

            model.put("Flight",flightService.FindResultFlightAndHotel(search.getDepartureAirport(), search.getArrivalAirport(),departureDate,search.getTypeFlight()));
            return "User/Hotel/Hotel";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String SearchFlight(ModelMap model, @ModelAttribute("Search")SearchFlightDTO searchFlightDTO, HttpSession session) {

        String selectedDateStr=searchFlightDTO.getDepartureTime().trim();
        if (selectedDateStr.endsWith(",")) {
            selectedDateStr = selectedDateStr.substring(0, selectedDateStr.length() - 1);
        }
        searchFlightDTO.setDepartureTime(selectedDateStr);
        if(!searchFlightDTO.getArrivalTime().isEmpty()){
            searchFlightDTO.setArrivalTime(searchFlightDTO.getArrivalTime().substring(0,searchFlightDTO.getArrivalTime().length()-1));

        }
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate selectedDate = LocalDate.parse(selectedDateStr, formatter);
        List<Map<String, String>> dateList = new ArrayList<>();
        for (int i=0;i<=4;i++){
            LocalDate nextDate = selectedDate.plusDays(i);
            String nextDateStr = nextDate.format(formatter);
            BigDecimal minPrice=flightService.FindPrice(searchFlightDTO.getDepartureAirport(),
                    searchFlightDTO.getArrivalAirport(),nextDate,
                    searchFlightDTO.getTypeFlight(),searchFlightDTO.getNumberPeopleRight());
            Map<String, String> dateMap = new HashMap<>();
            dateMap.put("day", String.valueOf(nextDate.getDayOfMonth()));
            dateMap.put("month", String.valueOf(nextDate.getMonthValue()));
            dateMap.put("minprice",String.valueOf(minPrice));
            if(searchFlightDTO.getArrivalTime().isEmpty()){
                model.put("Flight"+i,flightService.SearchFlight(searchFlightDTO.getDepartureAirport(),searchFlightDTO.getArrivalAirport(),
                        nextDate,searchFlightDTO.getTypeFlight(),searchFlightDTO.getNumberPeopleRight()));
            }else{
                String departureTime = searchFlightDTO.getDepartureTime().trim();
                if(departureTime.endsWith(",")){
                    departureTime = departureTime.substring(0, departureTime.length() - 1);
                }
                String ArrivatureTime=searchFlightDTO.getArrivalTime();
                if(ArrivatureTime.endsWith(",")){
                    ArrivatureTime = ArrivatureTime.substring(0, ArrivatureTime.length() - 1);
                }
                DateTimeFormatter formatterDepartureDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate departureDate = LocalDate.parse(departureTime, formatterDepartureDate);
                LocalDate ArrivatureTimeDate = LocalDate.parse(ArrivatureTime, formatterDepartureDate);
                model.put("Flight"+i,flightService.SearchFlightAllDto(searchFlightDTO.getDepartureAirport(),searchFlightDTO.getArrivalAirport(),departureDate,ArrivatureTimeDate,searchFlightDTO.getTypeFlight()));
            }
            dateList.add(dateMap);
        }
        model.put("dateList", dateList);
        model.put("searchFlightDTO",searchFlightDTO);
        String departureTime = searchFlightDTO.getDepartureTime().trim();
        if(departureTime.endsWith(",")){
            departureTime = departureTime.substring(0, departureTime.length() - 1);
        }
        DateTimeFormatter formatterDepartureDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate departureDate = LocalDate.parse(departureTime, formatterDepartureDate);
        session.setAttribute("NumberPeople",searchFlightDTO.getNumberPeopleRight());
        model.put("Airline",airlineService.searchAirline(searchFlightDTO.getDepartureAirport(),searchFlightDTO.getArrivalAirport(),departureDate,searchFlightDTO.getTypeFlight()));
        return "User/Flight/Flight";
    }
    @GetMapping("SearchFlight")
    public String SearchFlight(HttpServletRequest request, ModelMap model,@ModelAttribute("Search")SearchFlightDTO searchFlightDTO,HttpSession session) {
      if(searchFlightDTO.isSelectedHotel()==false){
          return SearchFlight(model,searchFlightDTO,session);
      }else{
          return SearchHotel(model,searchFlightDTO);
      }
    }
}
