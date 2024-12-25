package com.vtnq.web.Controllers;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.vtnq.web.Service.PayPalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping({"","/"})
public class InformationCustomerController {
    @Autowired
    private PayPalService payPalService;
    private static final String SUCCESS_URL = "pay/success";
    private static final String CANCEL_URL = "pay/cancel";
    @GetMapping("pay")
    public RedirectView payment() {
        try {
            Payment payment = payPalService.createPayment(100.00, "JPY", "paypal",
                    "sale", "Test payment", "http://localhost:8686/" + CANCEL_URL,
                    "http://localhost:8686/" + SUCCESS_URL);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return new RedirectView(link.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return new RedirectView("/");
    }
    @GetMapping("InformationCustomer")
    public String InformationCustomer() {
        try {
        return "/User/InformationCustomer/InformationCustomer";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("InformationFly")
    public String InformationFly() {
        try {
            return "/User/InformationCustomer/InformationFly";
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
