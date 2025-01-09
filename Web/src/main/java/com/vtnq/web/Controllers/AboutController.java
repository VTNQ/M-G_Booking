package com.vtnq.web.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("AboutController")
@RequestMapping({"","/"})
public class AboutController {
    @GetMapping("About")
    public String About() {
        try {
            return "User/AboutUs/AboutUs";

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
