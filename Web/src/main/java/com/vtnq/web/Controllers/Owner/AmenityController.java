package com.vtnq.web.Controllers.Owner;

import ch.qos.logback.core.model.Model;
import com.vtnq.web.DTOs.AmenityDto;
import com.vtnq.web.DTOs.Hotel.HotelListDto;
import com.vtnq.web.Entities.Amenity;
import com.vtnq.web.Service.AmenitiesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller("AmenitiesOwnerController")
@RequestMapping({"/Owner"})
public class AmenityController {
    @Autowired
    private AmenitiesService amenitiesService;
    @GetMapping("Amenities/{id}")
    public String Amenities(@PathVariable int id, ModelMap model, HttpSession session,@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "")String name) {
        try {
            session.setAttribute("id", id);
            List<Amenity>amenities=amenitiesService.findAll(id);
                List<Amenity>filteredAmenities=amenities.stream().filter(hotels->
                    hotels.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
            int start = (page - 1) * size;
            int end = Math.min(start + size, filteredAmenities.size());
            List<Amenity>paginatedAmenities=filteredAmenities.subList(start, end);
            model.put("amenity",paginatedAmenities);
            model.put("totalPages", (int) Math.ceil((double) filteredAmenities.size() / size));
            model.put("currentPage", page);
            model.put("id",id);
            return "Owner/Amenities/Amenities";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("Amenities/delete/{id}")
    public String delete(@PathVariable int id, ModelMap model,RedirectAttributes redirectAttributes,HttpSession session) {
        try {
            Integer idHotel=(Integer)session.getAttribute("id");
            if(amenitiesService.delete(id)){
                redirectAttributes.addFlashAttribute("message","Amenity deleted successfully");
                redirectAttributes.addFlashAttribute("messageType","success");
                return "redirect:/Owner/Amenities/"+idHotel;
            }else{
                redirectAttributes.addFlashAttribute("message","Amenity not deleted successfully");
                redirectAttributes.addFlashAttribute("messageType","error");
                return "redirect:/Owner/Amenities/"+idHotel;
            }
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @PostMapping("Amenities/update")
    public String update(@ModelAttribute("amenity")AmenityDto amenityDto, RedirectAttributes redirectAttributes) {
        try {
            if(amenitiesService.update(amenityDto)) {
                redirectAttributes.addFlashAttribute("message", "Amenity updated successfully");
                redirectAttributes.addFlashAttribute("messageType", "success");
                return "redirect:/Owner/Amenities/edit/" + amenityDto.getId();
            }else{
                redirectAttributes.addFlashAttribute("message", "Amenity update failed");
                redirectAttributes.addFlashAttribute("messageType", "error");
                return "redirect:/Owner/Amenities/edit/" + amenityDto.getId();
            }
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("Amenities/edit/{id}")
    public String edit(@PathVariable int id, ModelMap model, HttpSession session) {
        try {
            Integer idHotel = (Integer) session.getAttribute("id");
            model.put("amenity",amenitiesService.findById(id));
            model.put("idHotel",idHotel);
            return "Owner/Amenities/edit";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @PostMapping("Amenities/add")
    public String add(@ModelAttribute("amenity")AmenityDto amenity, ModelMap model, RedirectAttributes redirectAttributes) {
        try {
            if(amenitiesService.addAmenity(amenity)) {
                redirectAttributes.addFlashAttribute("message", "Amenity added successfully");
                redirectAttributes.addFlashAttribute("messageType", "success");
                return "redirect:/Owner/Amenities/add";
            }else{
                redirectAttributes.addFlashAttribute("message", "Amenity not added successfully");
                redirectAttributes.addFlashAttribute("messageType", "error");
                return "redirect:/Owner/Amenities/add";
            }
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("Amenities/add")
    public String AmenitiesAdd(ModelMap model, HttpSession session) {
        try {
            Integer id = (Integer) session.getAttribute("id");
            AmenityDto amenityDto = new AmenityDto();
            amenityDto.setRoom_id(id);
            model.put("amenity", amenityDto);
            return "Owner/Amenities/add";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}