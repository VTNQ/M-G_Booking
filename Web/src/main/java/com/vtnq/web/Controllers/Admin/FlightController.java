package com.vtnq.web.Controllers.Admin;

import com.vtnq.web.DTOs.Flight.DetailFlightDTO;
import com.vtnq.web.DTOs.Flight.FlightDto;
import com.vtnq.web.DTOs.Flight.FlightListDTO;
import com.vtnq.web.Entities.Account;
import com.vtnq.web.Entities.DetailFlight;
import com.vtnq.web.Entities.Flight;
import com.vtnq.web.Repositories.FlightRepository;
import com.vtnq.web.Service.AirlineService;
import com.vtnq.web.Service.AirportService;
import com.vtnq.web.Service.DetailFlightService;
import com.vtnq.web.Service.FlightService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller("FlightAdminController")
@RequestMapping({"/Admin"})
public class FlightController {
    @Autowired
    private AirportService airportService;
    @Autowired
    private AirlineService airlineService;
    @Autowired
    private FlightService flightService;

    @Autowired
    private DetailFlightService detailFlightService;

    @GetMapping("Flight/add")
    public String addFlight(ModelMap model, HttpServletRequest request) {
        try {
            Account currentAccount = (Account) request.getSession().getAttribute("currentAccount");
            model.put("DepartureAirPort", airportService.findAll(currentAccount.getCountryId()));
            model.put("ArrivalAirPort", airportService.findAll());
            model.put("Airline", airlineService.findAll());
            model.put("flight", new FlightDto());
            return "Admin/Flight/add";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("Flight/edit/{id}")
    public String editFlight(ModelMap model, @PathVariable int id, HttpServletRequest request) {
        try {
            Account currentAccount = (Account) request.getSession().getAttribute("currentAccount");
            model.put("flight", flightService.findById(id));
            model.put("detailFlight",detailFlightService.findAll(id));
            model.put("DepartureAirPort", airportService.findAll(currentAccount.getCountryId()));
            model.put("ArrivalAirPort", airportService.findAll());
            model.put("Airline", airlineService.findAll());
            DetailFlightDTO detailFlight=new DetailFlightDTO();
            detailFlight.setIdFlight(id);
            model.put("detailFlightAdd",detailFlight);
            return "Admin/Flight/Edit";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("Flight")
    public String Flight(ModelMap model, HttpServletRequest request, @RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "") String name) {
        try {
            Account currentAccount = (Account) request.getSession().getAttribute("currentAccount");
            List<FlightListDTO> flights = flightService.findAllByCountry(currentAccount.getCountryId());
            List<FlightListDTO> filterFlights = flights.stream().filter(flight ->
                    flight.getNameAirline().contains(name)).collect(Collectors.toList());
            int start = (page - 1) * size;
            int end = Math.min(start + size, filterFlights.size());
            List<FlightListDTO> paginatedFlights = flights.subList(start, end);
            model.put("Flight", paginatedFlights);
            model.put("totalPages", (int) Math.ceil((double) filterFlights.size() / size));
            model.put("currentPage", page);
            return "Admin/Flight/Flight";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @PostMapping("Flight/UpdateFlight")
    public String UpdateFlight(@ModelAttribute("flight") @Valid FlightDto flightDto,BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (bindingResult.hasErrors()) {
                StringBuilder errorMessages = new StringBuilder("Validation errors: ");
                bindingResult.getFieldErrors().forEach(error ->
                        errorMessages.append(String.format("Field : %s. ", error.getDefaultMessage()))
                );
                redirectAttributes.addFlashAttribute("message", errorMessages.toString());
                redirectAttributes.addFlashAttribute("messageType", "error");
                return "redirect:/Admin/Flight/edit/"+flightDto.getId();
            }
            if(flightService.UpdateFlightDto(flightDto)) {
                redirectAttributes.addFlashAttribute("messageType", "success");
                redirectAttributes.addFlashAttribute("message", "Flight updated successfully");
                return "redirect:/Admin/Flight/edit/"+flightDto.getId();
            }else{
                redirectAttributes.addFlashAttribute("messageType", "error");
                redirectAttributes.addFlashAttribute("message", "Flight update failed");
                return "redirect:/Admin/Flight/edit/"+flightDto.getId();
            }
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @PostMapping("Flight/AddDetailFlight")
    public String addDetailFlight(@ModelAttribute("detailFlightAdd")@Valid DetailFlightDTO detailFlightDTO, BindingResult result, RedirectAttributes redirectAttributes) {
      try {
          if (result.hasErrors()) {
              StringBuilder errorMessages = new StringBuilder("Validation errors: ");
              result.getFieldErrors().forEach(error ->
                      errorMessages.append(String.format("Field : %s. ", error.getDefaultMessage()))
              );
              redirectAttributes.addFlashAttribute("message", errorMessages.toString());
              redirectAttributes.addFlashAttribute("messageType", "error");
              return "redirect:/Admin/Flight/edit/"+detailFlightDTO.getIdFlight();
          }
          if(detailFlightService.existByType(detailFlightDTO.getType(),detailFlightDTO.getIdFlight())){
              redirectAttributes.addFlashAttribute("messageType", "error");
              redirectAttributes.addFlashAttribute("message", "This Type is already exists");
              return "redirect:/Admin/Flight/edit/"+detailFlightDTO.getIdFlight();
          }
          if(detailFlightService.addDetailFlight(detailFlightDTO)){
              redirectAttributes.addFlashAttribute("messageType", "success");
              redirectAttributes.addFlashAttribute("message", "Add Detail Flight Success");
              return "redirect:/Admin/Flight/edit/"+detailFlightDTO.getIdFlight();
          }else{
              redirectAttributes.addFlashAttribute("messageType", "error");
              redirectAttributes.addFlashAttribute("message", "Add Detail Flight Failed");
              return "redirect:/Admin/Flight/edit/"+detailFlightDTO.getIdFlight();
          }
      }catch (Exception e) {
          e.printStackTrace();
          return null;
      }
    }
    @PostMapping("Flight/add")
    public String addFlight(@ModelAttribute("flight") @Valid FlightDto flightDto,BindingResult bindingResult, ModelMap model, RedirectAttributes redirectAttributes
            ) {
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
            if (flightService.save(flightDto)) {
                redirectAttributes.addFlashAttribute("message", "Flight added successfully");
                redirectAttributes.addFlashAttribute("messageType", "success");
                return "redirect:/Admin/Flight/add";
            } else {
                redirectAttributes.addFlashAttribute("message", "Flight added failed");
                redirectAttributes.addFlashAttribute("messageType", "error");
                return "redirect:/Admin/Flight/add";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
