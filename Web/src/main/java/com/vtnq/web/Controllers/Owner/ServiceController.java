package com.vtnq.web.Controllers.Owner;

import com.vtnq.web.DTOs.Service.ServiceDTO;
import com.vtnq.web.Service.ServiceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/Owner")
public class ServiceController {
    @Autowired
    private ServiceService serviceService;
    @PostMapping("service/add")
    public String add(@ModelAttribute("service")ServiceDTO serviceDTO, RedirectAttributes redirectAttributes) {
        try {
            if(serviceService.addService(serviceDTO)) {
                redirectAttributes.addFlashAttribute("message", "Service Added");
                redirectAttributes.addFlashAttribute("messageType", "success");
                return "redirect:/Owner/service/add";
            }else{
                redirectAttributes.addFlashAttribute("message", "Service Not Added");
                redirectAttributes.addFlashAttribute("messageType", "error");
                return "redirect:/Owner/service/add";
            }
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("service/add")
    public String add(ModelMap model,HttpSession session) {
        try {
            Integer id = (Integer) session.getAttribute("id");
            ServiceDTO serviceDTO = new ServiceDTO();
            if(id!=null){
                serviceDTO.setHotelId(id);
            }
            model.put("service",serviceDTO);
            return "Owner/Service/add";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("service/{id}")
    public String service(ModelMap model, @PathVariable int id, HttpSession session) {
        try {
            session.setAttribute("id",id);
            model.put("service",serviceService.findAll(id));
            return "Owner/Service/Service";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
