package com.vtnq.web.Controllers.Admin;

import com.vtnq.web.DTOs.Flight.FlightDto;
import com.vtnq.web.Entities.Account;
import com.vtnq.web.Entities.Flight;
import com.vtnq.web.Service.AirlineService;
import com.vtnq.web.Service.AirportService;
import com.vtnq.web.Service.FlightService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("FlightAdminController")
@RequestMapping({"/Admin"})
public class FlightController {
    @Autowired
    private AirportService airportService;
    @Autowired
    private AirlineService airlineService;
    @Autowired
    private FlightService flightService;
    @GetMapping("Flight/add")
    public String addFlight(ModelMap model, HttpServletRequest request) {
        try {
            Account currentAccount = (Account) request.getSession().getAttribute("currentAccount");
            model.put("AirPort",airportService.findAll(currentAccount.getCountryId()));
            model.put("Airline",airlineService.findAll());
            model.put("flight",new FlightDto());
            return "Admin/Flight/add";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("Flight")
    public String Flight(ModelMap model, HttpServletRequest request) {
        try {
            Account currentAccount = (Account) request.getSession().getAttribute("currentAccount");
            model.put("Flight",flightService.findAllByCountry(currentAccount.getCountryId()));
            return "Admin/Flight/Flight";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @PostMapping("Flight/add")
    public String addFlight(@ModelAttribute("flight")FlightDto flightDto, ModelMap model, RedirectAttributes redirectAttributes
    , BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                StringBuilder errorMessages = new StringBuilder("Validation errors: ");
                bindingResult.getFieldErrors().forEach(error ->
                        errorMessages.append(String.format("Field : %s. ", error.getDefaultMessage()))
                );
                redirectAttributes.addFlashAttribute("message", errorMessages.toString());
                redirectAttributes.addFlashAttribute("messageType", "error");
                return "redirect:/Admin/Flight/add";
            }
            if(flightService.save(flightDto)) {
                redirectAttributes.addFlashAttribute("message", "Flight added successfully");
                redirectAttributes.addFlashAttribute("messageType", "success");
                return "redirect:/Admin/Flight/add";
            }else{
                redirectAttributes.addFlashAttribute("message", "Flight added failed");
                redirectAttributes.addFlashAttribute("messageType", "error");
                return "redirect:/Admin/Flight/add";
            }
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
