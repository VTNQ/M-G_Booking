package com.vtnq.web.Controllers;

import com.vtnq.web.Entities.Account;
import com.vtnq.web.Repositories.BookingFlightRepository;
import com.vtnq.web.Service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping({"","/"})
public class HistoryOrder {
    @Autowired
    private BookingFlightRepository bookingFlightRepository;
    @Autowired
    private BookingService bookingService;
    @GetMapping({"HistoryBooking"})
    public String HistoryBooking(ModelMap model,HttpServletRequest request,@RequestParam(value = "flightPage",defaultValue = "0")int flightPage,@RequestParam(required = false)String BookingCode) {
        try {
            Account account=(Account)request.getSession().getAttribute("currentAccount");
            if(account==null){
                return "redirect:/Login";
            }
            int pageSize=5;
            model.put("current",flightPage);
            model.put("BookingCode",BookingCode);
            model.put("Booking",bookingService.FindHistoryBookings(account.getId(),flightPage,pageSize,BookingCode));
            return "User/HistoryOrder/HistoryBooking";
        }catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }
    @GetMapping({"HistoryOrderHotel/{id}"})
    public String HistoryOrderHotel(ModelMap model,HttpServletRequest request,@PathVariable("id") int id) {
        try {
            Account account=(Account)request.getSession().getAttribute("currentAccount");
            if(account==null){
                return "redirect:/Login";
            }
            model.put("id",id);

            model.put("Hotel",bookingService.FindHistoryOrderHotels(id));
            return "User/HistoryOrder/HistoryOrderHotel";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping({"HistoryOrderFlight/{id}"})
    public String HistoryOrderFlight(@PathVariable("id")int id, @RequestParam(value = "flightPage",defaultValue = "0")int flightPage, HttpServletRequest request, ModelMap model,
                               @RequestParam(required = false)String flightCode, @RequestParam(required = false)String departureTime, @RequestParam(required = false)String ArrivalTime) {
        try {
            Account account=(Account)request.getSession().getAttribute("currentAccount");
            if(account==null){
                return "redirect:/Login";
            }
            int pageSize=5;

            model.put("currentFlightPage",flightPage);
            LocalDate parseDepartureDate=departureTime!=null?LocalDate.parse(departureTime,DateTimeFormatter.ISO_DATE):null;
            LocalDate parseArrivalDate=ArrivalTime!=null?LocalDate.parse(ArrivalTime,DateTimeFormatter.ISO_DATE):null;
            model.put("Flight",bookingService.FindHistoryOrderFlights(id,flightPage,pageSize,flightCode,parseDepartureDate,parseArrivalDate));

            return "User/HistoryOrder/HistoryOrderFlight";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
