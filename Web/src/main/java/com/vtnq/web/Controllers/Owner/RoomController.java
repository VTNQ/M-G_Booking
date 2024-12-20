package com.vtnq.web.Controllers.Owner;

import ch.qos.logback.core.model.Model;
import com.vtnq.web.DTOs.Room.RoomDTO;
import com.vtnq.web.Entities.Room;
import com.vtnq.web.Service.RoomService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller("RoomOwnerController")
@RequestMapping({"/Owner"})
public class RoomController {
    @Autowired
    private RoomService roomService;
    @GetMapping("Room/{id}")
    public String Room(@PathVariable int id, ModelMap model, HttpSession session,
                       @RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "")String name) {
        try {
            session.setAttribute("id", id);
            List<Room>rooms=roomService.findAll(id);
            List<Room>filteredRooms=rooms.stream().filter(room ->
                    room.getType().toLowerCase().contains(name.toLowerCase())
                    ).collect(Collectors.toList());
            int start = (page - 1) * size;
            int end = Math.min(start + size, filteredRooms.size());
            List<Room>paginatedRooms=filteredRooms.subList(start, end);
            model.put("room",paginatedRooms);
            model.put("totalPages", (int) Math.ceil((double) filteredRooms.size() / size));
            model.put("currentPage", page);
            model.put("id",id);
            return "Owner/Room/Room";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @PostMapping("Room/add")
    public String add(@RequestParam List<String>roomTypes, @RequestParam List<BigDecimal>roomPrices,@RequestParam List<Integer>roomCapacities,@RequestParam int IdHotel,
                      RedirectAttributes redirectAttributes) {
        try {
        if(roomService.addRoom(roomTypes,roomPrices,roomCapacities,IdHotel)){
        redirectAttributes.addFlashAttribute("message", "Room added successfully");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/Owner/Room/add";
        }else{
            redirectAttributes.addFlashAttribute("message", "Room added failed");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/Owner/Room/add";
        }
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("Room/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes,HttpSession session) {
        try {
            Integer idHotel=(Integer) session.getAttribute("id");
            if(roomService.delete(id)){
                redirectAttributes.addFlashAttribute("message", "Room deleted successfully");
                redirectAttributes.addFlashAttribute("messageType", "success");
                return "redirect:/Owner/Room/"+idHotel;
            }else{
                redirectAttributes.addFlashAttribute("message", "Room deleted failed");
                redirectAttributes.addFlashAttribute("messageType", "error");
                return "redirect:/Owner/Room/"+idHotel;
            }
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @PostMapping("Room/update")
    public String update(@ModelAttribute("room")@Valid RoomDTO roomDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        try {
            if (result.hasErrors()) {
                StringBuilder errorMessages = new StringBuilder("Validation errors: ");
               result.getFieldErrors().forEach(error ->
                        errorMessages.append(String.format("Field : %s. ", error.getDefaultMessage()))
                );
                redirectAttributes.addFlashAttribute("message", errorMessages.toString());
                redirectAttributes.addFlashAttribute("messageType", "error");
                return "redirect:/Owner/Room/edit/"+roomDTO.getId();
            }
            if(roomService.update(roomDTO)){
                redirectAttributes.addFlashAttribute("message", "Room updated successfully");
                redirectAttributes.addFlashAttribute("messageType", "success");
                return "redirect:/Owner/Room/edit/"+roomDTO.getId();
            }else{
                redirectAttributes.addFlashAttribute("message", "Room updated failed");
                redirectAttributes.addFlashAttribute("messageType", "error");
                return "redirect:/Owner/Room/edit/"+roomDTO.getId();
            }
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("Room/edit/{id}")
    public String edit(@PathVariable int id, ModelMap model, HttpSession session) {
        try {
            model.put("room",roomService.findById(id));
            return "Owner/Room/edit";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("Room/add")
    public String add(ModelMap modelMap,HttpSession session){
        try {
            Integer id=(Integer) session.getAttribute("id");
            modelMap.put("id",id);
            return "Owner/Room/add";

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
