package com.vtnq.web.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("ContactController")
@RequestMapping({"","/"})
public class ContactController {
    @GetMapping("Contact")
    public String contact(){
    try {
        return "User/Contact/Contact";
    }catch (Exception e) {
        e.printStackTrace();
        return null;
    }
    }
}
