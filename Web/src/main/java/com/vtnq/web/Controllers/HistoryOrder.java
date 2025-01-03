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

import java.util.List;

@Controller
@RequestMapping({"","/"})
public class HistoryOrder {
    @Autowired
    private BookingFlightRepository bookingFlightRepository;
    @Autowired
    private BookingService bookingService;
    @GetMapping({"HistoryOrder"})
    public String HistoryOrder(HttpServletRequest request, ModelMap model) {
        try {
            Account account=(Account)request.getSession().getAttribute("currentAccount");
            List<HistoryOrderFlight>historyOrderFlights=bookingService.FindHistoryOrderFlights(account.getId());
            model.put("Flight",historyOrderFlights);
            model.put("Hotel",bookingService.FindHistoryOrderHotels(account.getId()));
            return "User/HistoryOrder/HistoryOrder";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
