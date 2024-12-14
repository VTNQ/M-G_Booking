package com.vtnq.web.Controllers.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("AdminHomeController")
@RequestMapping({"/Admin"})
public class HomeController {
    @GetMapping("Home")
    public String Home() {
        return "Admin/Home/index";
    }
}
