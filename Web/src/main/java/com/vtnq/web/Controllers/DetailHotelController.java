package com.vtnq.web.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"","/"})
public class DetailHotelController {
    @GetMapping("DetailHotel")
    public String DetailController() {
        try {
            return "User/DetailHotel/DetailHotel";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
