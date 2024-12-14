package com.vtnq.web.Controllers.Owner;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("OwnerHomeController")
@RequestMapping("/Owner")
public class HomeController {
    @GetMapping({"","/"})
    public String Home(){
        return "Owner/Home/Home";
    }
}
