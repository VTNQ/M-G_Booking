package com.vtnq.web.Controllers.SuperAdmin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller("superAdminHomeController")
@RequestMapping({"/SuperAdmin"})
public class HomeController {

    @GetMapping("Home")
    public String Home(){
        return "SuperAdmin/Home/index";
    }

}
