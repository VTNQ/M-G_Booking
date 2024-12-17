package com.vtnq.web.Controllers.Owner;

import com.vtnq.web.DTOs.Hotel.HotelDto;
import com.vtnq.web.DTOs.Hotel.HotelListDto;
import com.vtnq.web.DTOs.Hotel.HotelUpdateDTO;
import com.vtnq.web.Entities.Account;
import com.vtnq.web.Entities.City;
import com.vtnq.web.Entities.District;
import com.vtnq.web.Service.CityService;
import com.vtnq.web.Service.DistrictService;
import com.vtnq.web.Service.HotelService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller("HotelAdminController")
@RequestMapping({"/Owner"})
public class HotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private CityService cityService;
    @Autowired
    private DistrictService districtService;
    @GetMapping("Hotel/Detail/{id}")
    public String DetailHotel(@PathVariable("id")int id, ModelMap model) {
        try {
            model.put("Image",hotelService.findImage(id));
            return "Owner/Hotel/detail";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("Hotel/add")
    public String add(ModelMap model, HttpServletRequest request) {
        try {
            Account currentAccount = (Account) request.getSession().getAttribute("currentAccount");
            List<City> cities = cityService.findCityAll(currentAccount.getCountryId());
            HotelDto hotelDto = new HotelDto();
            hotelDto.setOwnerId(currentAccount.getId());
            model.put("hotel", hotelDto);
            model.put("City", cities);
            return "Owner/Hotel/add";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    @PostMapping("Hotel/update")
    public String update(@ModelAttribute("hotel") HotelUpdateDTO hotelUpdateDTO, @RequestParam(value = "imageForm", required = false) MultipartFile imageForm,
                         BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (hotelService.UpdateHotel(hotelUpdateDTO, imageForm)) {
                redirectAttributes.addFlashAttribute("message", "Hotel updated successfully");
                redirectAttributes.addFlashAttribute("messageType", "success");
                return "redirect:/Owner/Hotel/edit/" + hotelUpdateDTO.getId();
            } else {
                redirectAttributes.addFlashAttribute("message", "Hotel update failed");
                redirectAttributes.addFlashAttribute("messageType", "error");
                return "redirect:/Owner/Hotel/edit/" + hotelUpdateDTO.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("Hotel/edit/{id}")
    public String edit(@PathVariable("id") int id, ModelMap model, HttpServletRequest request) {
        try {
            Account currentAccount = (Account) request.getSession().getAttribute("currentAccount");
            List<District> districts = districtService.findDistrict(currentAccount.getCityId());
            model.put("hotel", hotelService.findHotels(id));
            model.put("ImageList", hotelService.findImage(id));
            model.put("District", districts);
            return "Owner/Hotel/edit";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("Hotel")
    public String hotel(ModelMap model, HttpServletRequest request,@RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "")String name) {
        try {
            Account currentAccount = (Account) request.getSession().getAttribute("currentAccount");
            List<HotelListDto>hotel=hotelService.FindHotelsByOwner(currentAccount.getId());
            List<HotelListDto>filteredHotel=hotel.stream().filter(hotels->
                    hotels.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
            int start = (page - 1) * size;
            int end = Math.min(start + size, filteredHotel.size());
            List<HotelListDto>paginatedHotel=filteredHotel.subList(start, end);
            model.put("hotel",paginatedHotel);
            model.put("totalPages", (int) Math.ceil((double) filteredHotel.size() / size));
            model.put("currentPage", page);
            return "Owner/Hotel/Hotel";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("Hotel/add")
    public String add(@ModelAttribute("hotel") @Valid HotelDto hotel, BindingResult result, ModelMap model,
                      RedirectAttributes redirectAttributes) {
        try {
            if (result.hasErrors()) {
                StringBuilder errorMessages = new StringBuilder("Validation errors: ");
                result.getFieldErrors().forEach(error ->
                        errorMessages.append(String.format("Field : %s. ", error.getDefaultMessage()))
                );
                redirectAttributes.addFlashAttribute("message", errorMessages.toString());
                redirectAttributes.addFlashAttribute("messageType", "error");
                return "redirect:/Owner/Hotel/add";
            }
            if (hotelService.addHotel(hotel)) {
                redirectAttributes.addFlashAttribute("message", "Hotel added successfully");
                redirectAttributes.addFlashAttribute("messageType", "success");
                return "redirect:/Owner/Hotel/add";
            } else {
                redirectAttributes.addFlashAttribute("message", "Hotel add failed");
                redirectAttributes.addFlashAttribute("messageType", "error");
                return "redirect:/Owner/Hotel/add";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}