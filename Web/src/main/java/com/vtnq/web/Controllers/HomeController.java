package com.vtnq.web.Controllers;

import com.vtnq.web.DTOs.Flight.FlightDto;
import com.vtnq.web.DTOs.Flight.ResultFlightDTO;
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
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("SearchHotel/{id}")
    public String SearchHotel(@PathVariable("id")int id,ModelMap model,HttpSession session,HttpServletRequest request) {
        try {
           SearchFlightDTO resultFlightDTO=(SearchFlightDTO) request.getSession().getAttribute("searchFlightDTO");
            List<Integer>idFlight=(List<Integer>) request.getSession().getAttribute("idFlight");
            if(idFlight!=null){
                boolean existsFlightTab=idFlight.stream().anyMatch(flight->flight==id);
                if(!existsFlightTab){
                    idFlight.add(id);
                }

            }else{
                idFlight=new ArrayList<>();
                idFlight.add(id);
                session.setAttribute("idFlight",idFlight);
            }
            String selectedDateStr = resultFlightDTO.getDepartureTime().trim();
            // Kiểm tra nếu selectedDateStr rỗng hoặc null
            if (selectedDateStr == null || selectedDateStr.isEmpty()) {
                throw new IllegalArgumentException("Departure time is required.");
            }

            // Xóa dấu phẩy cuối nếu có
            if (selectedDateStr.endsWith(",")) {
                selectedDateStr = selectedDateStr.substring(0, selectedDateStr.length() - 1);
            }

            // Định dạng và parse ngày
            DateTimeFormatter formatterDepartureDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate departureDate = LocalDate.parse(selectedDateStr, formatterDepartureDate);

            // Thêm dữ liệu vào model
            model.put("Search",resultFlightDTO);
            model.put("Hotel", hotelService.SearchHotels(resultFlightDTO.getIdCity(), resultFlightDTO.getQuantityRoom()));
            session.setAttribute("HotelSearch", resultFlightDTO);
            model.put("Flight", flightService.FindResultFlightAndHotel(
                    resultFlightDTO.getDepartureAirport(),
                    resultFlightDTO.getArrivalAirport(),
                    departureDate,
                    resultFlightDTO.getTypeFlight()
            )!=null?flightService.FindResultFlightAndHotel(
                    resultFlightDTO.getDepartureAirport(),
                    resultFlightDTO.getArrivalAirport(),
                    departureDate,
                    resultFlightDTO.getTypeFlight()
            ):new ResultFlightDTO());

            return "User/Hotel/Hotel";
        } catch (IllegalArgumentException e) {
            model.put("error", "Invalid departure time: " + e.getMessage());
            e.printStackTrace();
            return "User/ErrorPage"; // Đưa người dùng tới trang lỗi
        } catch (Exception e) {
            e.printStackTrace();
            model.put("error", "An unexpected error occurred.");
            return "User/ErrorPage"; // Đưa người dùng tới trang lỗi
        }
    }
    public String SearchHotelFlight(ModelMap model, @ModelAttribute("Search") SearchFlightDTO searchFlightDTO,HttpSession session) {

        String selectedDateStr=searchFlightDTO.getDepartureTime().trim();
        if (selectedDateStr.endsWith(",")) {
            selectedDateStr = selectedDateStr.substring(0, selectedDateStr.length() - 1);
        }
        searchFlightDTO.setDepartureTime(selectedDateStr);

        model.put("Hotel", hotelService.SearchHotels(searchFlightDTO.getIdCity(), searchFlightDTO.getQuantityRoom()));
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
            model.put("Flight"+i,flightService.SearchFlight(searchFlightDTO.getDepartureAirport(),searchFlightDTO.getArrivalAirport(),
                    nextDate,searchFlightDTO.getTypeFlight(),searchFlightDTO.getNumberPeopleRight()));
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
        session.setAttribute("searchFlightDTO",searchFlightDTO);
        model.put("Airline",airlineService.searchAirline(searchFlightDTO.getDepartureAirport(),searchFlightDTO.getArrivalAirport(),departureDate,searchFlightDTO.getTypeFlight()));

        return "User/Flight/Flight";
    }
    public String SearchFlight(ModelMap model, @ModelAttribute("Search")SearchFlightDTO searchFlightDTO, HttpSession session) {

        String selectedDateStr=searchFlightDTO.getDepartureTime().trim();
        if (selectedDateStr.endsWith(",")) {
            selectedDateStr = selectedDateStr.substring(0, selectedDateStr.length() - 1);
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
            model.put("Flight"+i,flightService.SearchFlight(searchFlightDTO.getDepartureAirport(),searchFlightDTO.getArrivalAirport(),
                    nextDate,searchFlightDTO.getTypeFlight(),searchFlightDTO.getNumberPeopleRight()));
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
        session.setAttribute("searchFlightDTO",searchFlightDTO);
        model.put("Airline",airlineService.searchAirline(searchFlightDTO.getDepartureAirport(),searchFlightDTO.getArrivalAirport(),departureDate,searchFlightDTO.getTypeFlight()));

        return "User/Flight/Flight";
    }
    @GetMapping("SearchFlight")
    public String SearchFlight(HttpServletRequest request, ModelMap model,@ModelAttribute("Search")SearchFlightDTO searchFlightDTO,HttpSession session) {
      if(searchFlightDTO.isSelectedHotel()==false){
          return SearchFlight(model,searchFlightDTO,session);
      }else{
          return SearchHotelFlight(model,searchFlightDTO,session);
      }
    }
    @GetMapping("RoundTrip/{id}")
    public String RoundTrip(@PathVariable("id") int id, ModelMap model, HttpServletRequest request,HttpSession session) {
        try {
            List<Integer>idFlight=new ArrayList<>();
            idFlight.add(id);
            session.setAttribute("idFlight",idFlight);
            SearchFlightDTO searchFlightDTO = (SearchFlightDTO) request.getSession().getAttribute("searchFlightDTO");
            String ArrivalTime = searchFlightDTO.getArrivalTime().trim();
            if(ArrivalTime.endsWith(",")){
                ArrivalTime = ArrivalTime.substring(0, ArrivalTime.length() - 1);
            }

            DateTimeFormatter formatterDepartureDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate ArrivalDate = LocalDate.parse(ArrivalTime, formatterDepartureDate);
            model.put("searchFlightDTO",searchFlightDTO);
            model.put("flight",flightService.FindByIdFlight(id));
            model.put("flightArrival",flightService.FindArrivalTime(searchFlightDTO.getDepartureAirport(),searchFlightDTO.getArrivalAirport(),ArrivalDate,searchFlightDTO.getTypeFlight()));
            return "User/Flight/RoundTripFlight";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("RoundTripHotel/{id}")
    public String RoundTripHotel(@PathVariable("id") int id, ModelMap model, HttpServletRequest request,HttpSession session) {
        try {
            List<Integer>idFlight=new ArrayList<>();
            idFlight.add(id);
            session.setAttribute("idFlight",idFlight);
            SearchFlightDTO searchFlightDTO = (SearchFlightDTO) request.getSession().getAttribute("searchFlightDTO");
            String ArrivalTime = searchFlightDTO.getArrivalTime().trim();
            if(ArrivalTime.endsWith(",")){
                ArrivalTime = ArrivalTime.substring(0, ArrivalTime.length() - 1);
            }

            DateTimeFormatter formatterDepartureDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate ArrivalDate = LocalDate.parse(ArrivalTime, formatterDepartureDate);
            model.put("searchFlightDTO",searchFlightDTO);
            model.put("flight",flightService.FindByIdFlight(id));
            model.put("flightArrival",flightService.FindArrivalTime(searchFlightDTO.getDepartureAirport(),searchFlightDTO.getArrivalAirport(),ArrivalDate,searchFlightDTO.getTypeFlight()));
            return "User/Flight/RoundTripFlight";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
