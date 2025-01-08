package com.vtnq.web.Controllers;

import com.vtnq.web.DTOs.HistoryOrder.HistoryOrderFlight;
import com.vtnq.web.Entities.Account;
import com.vtnq.web.Repositories.BookingFlightRepository;
import com.vtnq.web.Service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping({"","/"})
public class HistoryOrder {
    @Autowired
    private BookingFlightRepository bookingFlightRepository;
    @Autowired
    private BookingService bookingService;
    @GetMapping({"HistoryOrder"})
    public String HistoryOrder(@RequestParam(value = "flightPage",defaultValue = "0")int flightPage,@RequestParam(value = "hotelPage",defaultValue = "0")int hotelPage , HttpServletRequest request, ModelMap model,
                               @RequestParam(required = false)String  hotelName,@RequestParam(required = false)String checkInDate,@RequestParam(required = false)String checkOutDate,
                               @RequestParam(required = false)String flightCode,@RequestParam(required = false)String departureTime,@RequestParam(required = false)String ArrivalTime) {
        try {
            Account account=(Account)request.getSession().getAttribute("currentAccount");
            if(account==null){
                return "redirect:/Login";
            }
            int pageSize=5;

            model.put("currentFlightPage",flightPage);
            model.put("currentHotelPage",hotelPage);
            LocalDate parseDepartureDate=departureTime!=null?LocalDate.parse(departureTime,DateTimeFormatter.ISO_DATE):null;
            LocalDate parseArrivalDate=ArrivalTime!=null?LocalDate.parse(ArrivalTime,DateTimeFormatter.ISO_DATE):null;
            LocalDate parsedCheckInDate = checkInDate != null ? LocalDate.parse(checkInDate, DateTimeFormatter.ISO_DATE) : null;
            LocalDate parsedCheckOutDate = checkOutDate != null ? LocalDate.parse(checkOutDate, DateTimeFormatter.ISO_DATE) : null;
            model.put("Hotel",bookingService.FindHistoryOrderHotels(account.getId(),hotelPage,pageSize,hotelName,parsedCheckInDate,parsedCheckOutDate));
            model.put("Flight",bookingService.FindHistoryOrderFlights(account.getId(),flightPage,pageSize,flightCode,parseDepartureDate,parseArrivalDate));
            model.put("hotelName",hotelName);
            model.put("flightCode",flightCode);
            model.put("departureTime",departureTime);
            model.put("arrivalTime",ArrivalTime);
            model.put("checkInDate",checkInDate);
            model.put("checkOutDate",checkOutDate);
            return "User/HistoryOrder/HistoryOrder";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
