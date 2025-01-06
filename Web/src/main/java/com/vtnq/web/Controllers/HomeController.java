package com.vtnq.web.Controllers;

import com.vtnq.web.DTOs.Flight.FlightDto;
import com.vtnq.web.DTOs.Flight.ResultFlightDTO;
import com.vtnq.web.DTOs.Flight.SearchFlightDTO;
import com.vtnq.web.DTOs.Hotel.HotelSearchDTO;
import com.vtnq.web.Entities.Airport;
import com.vtnq.web.Entities.City;
import com.vtnq.web.Repositories.AirportRepository;
import com.vtnq.web.Service.AirlineService;
import com.vtnq.web.Service.FlightService;
import com.vtnq.web.Service.HotelService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
    private AirportRepository airportRepository;
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
    private String formatDate(Instant time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yy")
                .withZone(ZoneId.of("UTC"));
        return formatter.format(time);
    }
    private Instant parseToInstant(String date) {
        LocalDate localDate=LocalDate.parse(date);
        return localDate.atStartOfDay(ZoneId.of("UTC")).toInstant();
    }
    @GetMapping("SearchHotelFlight/{id}")
    public String SearchHotel(@PathVariable("id")int id,ModelMap model,HttpSession session,HttpServletRequest request, @RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size,@RequestParam(value="minPrice",defaultValue = "0")BigDecimal minPrice,@RequestParam(value = "maxPrice",defaultValue = "10000")BigDecimal maxPrice) {
      try {
          session.setAttribute("idRoom",id);
          SearchFlightDTO searchFlightDTO = (SearchFlightDTO) request.getSession().getAttribute("HotelSearch");
          String selectedDateStr = searchFlightDTO.getDepartureTime().trim();
          if (selectedDateStr.endsWith(",")) {
              selectedDateStr = selectedDateStr.substring(0, selectedDateStr.length() - 1);
          }

          model.put("searchFlightDTO", searchFlightDTO);
          String departureTime = searchFlightDTO.getDepartureTime().trim();
          if (departureTime.endsWith(",")) {
              departureTime = departureTime.substring(0, departureTime.length() - 1);
          }

          // Định dạng ngày
          DateTimeFormatter formatterDepartureDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
          LocalDate departureDate = LocalDate.parse(departureTime, formatterDepartureDate);
          session.setAttribute("NumberPeople", searchFlightDTO.getNumberPeopleRight());
          session.setAttribute("searchFlightDTO", searchFlightDTO);

          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
          LocalDate selectedDate = LocalDate.parse(selectedDateStr, formatter);

          // Lấy danh sách chuyến bay
          List<ResultFlightDTO> resultFlightDTOS = flightService.SearchFlight(searchFlightDTO.getDepartureAirport(),
                  searchFlightDTO.getArrivalAirport(), departureDate, searchFlightDTO.getTypeFlight(), searchFlightDTO.getNumberPeopleRight());


          model.put("Flight", resultFlightDTOS);

          model.put("MinPrice",flightService.FindMinPriceDeparture(searchFlightDTO.getDepartureAirport(),searchFlightDTO.getArrivalAirport(),departureDate,searchFlightDTO.getTypeFlight(),searchFlightDTO.getNumberPeopleRight()));
          model.put("MaxPrice",flightService.FindMaxPriceDeparture(searchFlightDTO.getDepartureAirport(),searchFlightDTO.getArrivalAirport(),departureDate, searchFlightDTO.getTypeFlight(),searchFlightDTO.getNumberPeopleRight()));
          // Lấy thông tin hãng hàng không
          model.put("Airline", airlineService.searchAirline(searchFlightDTO.getDepartureAirport(), searchFlightDTO.getArrivalAirport(),
                  departureDate, searchFlightDTO.getTypeFlight()));
            return "User/Flight/Flight";
      }catch (Exception e) {
          e.printStackTrace();
          return null;
      }
    }
    public String SearchHotelFlight(ModelMap model, @ModelAttribute("Search") SearchFlightDTO searchFlightDTO, HttpSession session,
                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "10") int size,@RequestParam(value="minPrice",defaultValue = "0")BigDecimal minPrice,@RequestParam(value = "maxPrice",defaultValue = "10000")BigDecimal maxPrice) {


        minPrice=hotelService.FindMinPriceHotel(searchFlightDTO.getIdCity(), searchFlightDTO.getQuantityRoom());
        maxPrice=hotelService.FindMaxPriceHotel(searchFlightDTO.getIdCity(), searchFlightDTO.getQuantityRoom());
        Instant dateDepartureTime=parseToInstant(searchFlightDTO.getDepartureTime());
        Instant dateCheckInTime=parseToInstant(searchFlightDTO.getCheckInTime());
        String dateCheckIn=formatDate(dateCheckInTime);
        Instant dateCheckOutTime=parseToInstant(searchFlightDTO.getCheckOutTime());
        String dateCheckOut=formatDate(dateCheckOutTime);
        String dateDeparture=formatDate(dateDepartureTime);
        Instant ArrivalTime=parseToInstant(searchFlightDTO.getArrivalTime());
        String dateArrival=formatDate(ArrivalTime);
        String selectedDateStr = searchFlightDTO.getDepartureTime().trim();
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
        model.put("Search",searchFlightDTO);
        List<HotelSearchDTO>allHotel=hotelService.SearchHotels(searchFlightDTO.getIdCity(), searchFlightDTO.getQuantityRoom(),minPrice,maxPrice);
        model.put("Hotel", allHotel);
        model.put("currentPage", page);
        model.put("totalPages", (int) Math.ceil((double) allHotel.size() / size));
        model.put("totalItems", allHotel.size());
        model.put("pageSize", size);
        model.put("MinPrice",hotelService.FindMinPriceHotel(searchFlightDTO.getIdCity(), searchFlightDTO.getQuantityRoom()));
        model.put("MaxPrice",hotelService.FindMaxPriceHotel(searchFlightDTO.getIdCity(), searchFlightDTO.getQuantityRoom()));
        session.setAttribute("HotelSearch", searchFlightDTO);
        model.put("Flight", flightService.FindResultFlightAndHotel(
                searchFlightDTO.getDepartureAirport(),
                searchFlightDTO.getArrivalAirport(),
                departureDate,
                searchFlightDTO.getTypeFlight()
        )!=null?flightService.FindResultFlightAndHotel(
                searchFlightDTO.getDepartureAirport(),
                searchFlightDTO.getArrivalAirport(),
                departureDate,
                searchFlightDTO.getTypeFlight()
        ):new ResultFlightDTO());
        model.put("DepartureDate",dateDeparture);
        model.put("ArrivalDate",dateArrival);
        model.put("CheckIn",dateCheckIn);
        model.put("CheckOut",dateCheckOut);
        model.put("People",searchFlightDTO.getNumberPeopleRight());
        model.put("Room",searchFlightDTO.getQuantityRoom());

        return "User/Hotel/Hotel";
    }
    public String SearchFlight(ModelMap model, @ModelAttribute("Search") SearchFlightDTO searchFlightDTO, HttpSession session
     ) {

        // Xử lý ngày tháng
        String selectedDateStr = searchFlightDTO.getDepartureTime().trim();
        if (selectedDateStr.endsWith(",")) {
            selectedDateStr = selectedDateStr.substring(0, selectedDateStr.length() - 1);
        }

        model.put("searchFlightDTO", searchFlightDTO);
        String departureTime = searchFlightDTO.getDepartureTime().trim();
        if (departureTime.endsWith(",")) {
            departureTime = departureTime.substring(0, departureTime.length() - 1);
        }

        // Định dạng ngày
        DateTimeFormatter formatterDepartureDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate departureDate = LocalDate.parse(departureTime, formatterDepartureDate);
        session.setAttribute("NumberPeople", searchFlightDTO.getNumberPeopleRight());
        session.setAttribute("searchFlightDTO", searchFlightDTO);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate selectedDate = LocalDate.parse(selectedDateStr, formatter);

        // Lấy danh sách chuyến bay
        List<ResultFlightDTO> resultFlightDTOS = flightService.SearchFlight(searchFlightDTO.getDepartureAirport(),
                searchFlightDTO.getArrivalAirport(), departureDate, searchFlightDTO.getTypeFlight(), searchFlightDTO.getNumberPeopleRight());


        model.put("Flight", resultFlightDTOS);
        model.put("MinPrice",flightService.FindMinPriceDeparture(searchFlightDTO.getDepartureAirport(),searchFlightDTO.getArrivalAirport(),departureDate,searchFlightDTO.getTypeFlight(),searchFlightDTO.getNumberPeopleRight()));
        model.put("MaxPrice",flightService.FindMaxPriceDeparture(searchFlightDTO.getDepartureAirport(),searchFlightDTO.getArrivalAirport(),departureDate, searchFlightDTO.getTypeFlight(),searchFlightDTO.getNumberPeopleRight()));
        // Lấy thông tin hãng hàng không
        model.put("Airline", airlineService.searchAirline(searchFlightDTO.getDepartureAirport(), searchFlightDTO.getArrivalAirport(),
                departureDate, searchFlightDTO.getTypeFlight()));

        return "User/Flight/Flight";
    }
    @GetMapping("SearchFlight")
    public String SearchFlight(HttpServletRequest request, ModelMap model,@ModelAttribute("Search")SearchFlightDTO searchFlightDTO,HttpSession session,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "10") int size,
                               @RequestParam(value="minPrice",defaultValue = "0")BigDecimal minPrice,@RequestParam(value = "maxPrice",defaultValue = "10000")BigDecimal maxPrice) {
      if(searchFlightDTO.isSelectedHotel()==false){
          return SearchFlight(model,searchFlightDTO,session);
      }else{
          return SearchHotelFlight(model,searchFlightDTO,session,page,size,minPrice,maxPrice);
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
            Airport airport=airportRepository.findById(searchFlightDTO.getArrivalAirport())
                    .orElseThrow(()->new RuntimeException("Aiport not found"));
            DateTimeFormatter formatterDepartureDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate ArrivalDate = LocalDate.parse(ArrivalTime, formatterDepartureDate);
            model.put("searchFlightDTO",searchFlightDTO);
            model.put("NameArrivalAirport",airport.getCity().getName());
            model.put("flight",flightService.FindByIdFlight(id));
            model.put("flightArrival",flightService.FindArrivalTime(searchFlightDTO.getDepartureAirport(),searchFlightDTO.getArrivalAirport(),ArrivalDate,searchFlightDTO.getTypeFlight(),searchFlightDTO.getNumberPeopleRight()));
            model.put("MinPrice",flightService.FindMinPriceArrivalTime(searchFlightDTO.getDepartureAirport(),searchFlightDTO.getArrivalAirport(),ArrivalDate,searchFlightDTO.getTypeFlight(),searchFlightDTO.getNumberPeopleRight()));
            model.put("MaxPrice",flightService.FindMaxPriceArrivalTime(searchFlightDTO.getDepartureAirport(),searchFlightDTO.getArrivalAirport(),ArrivalDate,searchFlightDTO.getTypeFlight(),searchFlightDTO.getNumberPeopleRight()));
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
            Airport airport=airportRepository.findById(searchFlightDTO.getArrivalAirport())
                    .orElseThrow(()->new RuntimeException("Aiport not found"));
            String ArrivalTime = searchFlightDTO.getArrivalTime().trim();
            if(ArrivalTime.endsWith(",")){
                ArrivalTime = ArrivalTime.substring(0, ArrivalTime.length() - 1);
            }

            DateTimeFormatter formatterDepartureDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate ArrivalDate = LocalDate.parse(ArrivalTime, formatterDepartureDate);
            model.put("searchFlightDTO",searchFlightDTO);
            model.put("NameArrivalAirport",airport.getCity().getName());
            model.put("flight",flightService.FindByIdFlight(id));
            model.put("flightArrival",flightService.FindArrivalTime(searchFlightDTO.getDepartureAirport(),searchFlightDTO.getArrivalAirport(),ArrivalDate,searchFlightDTO.getTypeFlight(),searchFlightDTO.getNumberPeopleRight()));
            return "User/Flight/RoundTripFlight";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
