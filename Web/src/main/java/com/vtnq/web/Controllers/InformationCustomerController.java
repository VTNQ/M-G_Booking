package com.vtnq.web.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"","/"})
public class InformationCustomerController {
    @GetMapping("InformationCustomer")
    public String InformationCustomer() {
        try {
        return "/User/InformationCustomer/InformationCustomer";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("Payment")
    public String Payment() {
        try {
            return "/User/InformationCustomer/Payment";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("Success")
    public String Success() {
        try {
            return "/User/InformationCustomer/Success";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
