package com.vtnq.web.Controllers.Owner;

import com.vtnq.web.DTOs.Hotel.HotelDto;
import com.vtnq.web.Service.HotelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("HotelAdminController")
@RequestMapping({"/Owner"})
public class HotelController {
    @Autowired
    private HotelService hotelService;
    @GetMapping("Hotel/add")
    public String add(ModelMap model) {
        try {
            model.put("hotel", new HotelDto());
            return "Owner/Hotel/add";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @PostMapping("Hotel/add")
    public String add(@ModelAttribute("hotel")@Valid HotelDto hotel, BindingResult result, ModelMap model,
                      RedirectAttributes redirectAttributes) {
        try {
            if(result.hasErrors()) {
                StringBuilder errorMessages = new StringBuilder("Validation errors: ");
                result.getFieldErrors().forEach(error ->
                        errorMessages.append(String.format("Field : %s. ",  error.getDefaultMessage()))
                );
                redirectAttributes.addFlashAttribute("message", errorMessages.toString());
                redirectAttributes.addFlashAttribute("messageType", "error");
                return "redirect:/Owner/Hotel/add";
            }
            if(hotelService.addHotel(hotel)) {
                redirectAttributes.addFlashAttribute("message", "Hotel added successfully");
                redirectAttributes.addFlashAttribute("messageType", "success");
                return "redirect:/Owner/Hotel/add";
            }else{
                redirectAttributes.addFlashAttribute("message", "Hotel add failed");
                redirectAttributes.addFlashAttribute("messageType", "error");
                return "redirect:/Owner/Hotel/add";
            }
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
