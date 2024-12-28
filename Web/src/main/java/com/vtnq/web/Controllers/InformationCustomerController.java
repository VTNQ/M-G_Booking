package com.vtnq.web.Controllers;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.vtnq.web.DTOs.Booking.BookingFlightDTO;
import com.vtnq.web.Entities.Account;
import com.vtnq.web.Service.BookingService;
import com.vtnq.web.Service.FlightService;
import com.vtnq.web.Service.PayPalService;
import com.vtnq.web.Service.SeatService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;

@Controller
@RequestMapping({"","/"})
public class InformationCustomerController {
    @Autowired
    private PayPalService payPalService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private SeatService seatService;
    @Autowired
    private FlightService flightService;
    private static final String SUCCESS_URL = "payFlight/success";
    private static final String CANCEL_URL = "payFlight/cancel";
    @GetMapping("payFlight")
    public RedirectView payment(@RequestParam(required = true) double amount,
                                @RequestParam(defaultValue = "JPY") String currency, @ModelAttribute BookingFlightDTO bookingFlightDTO, HttpSession session, HttpServletRequest request) {
        try {
            Account currentAccount = (Account) request.getSession().getAttribute("currentAccount");
            bookingFlightDTO.setUserId(currentAccount.getId());
            bookingFlightDTO.setTotalPrice(BigDecimal.valueOf(amount));
            session.setAttribute("booking", bookingFlightDTO);
            Payment payment = payPalService.createPayment(amount, currency, "paypal",
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
    @GetMapping(SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,HttpServletRequest request) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);
            BookingFlightDTO bookingFlightDto=(BookingFlightDTO) request.getSession().getAttribute("booking");
            if (payment.getState().equals("approved") && bookingService.addBooking(bookingFlightDto)) {
                return "redirect:/Success";
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/";
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
    @GetMapping("InformationFly/{id}")
    public String InformationFly(@PathVariable int id, ModelMap modelMap,HttpServletRequest request) {
        try {
            Integer NumberPeople = (Integer) request.getSession().getAttribute("NumberPeople");
            modelMap.put("flight",flightService.getResultPaymentFlight(id));
            modelMap.put("seat",seatService.FindSeatByFlight(id));
            BookingFlightDTO bookingDto=new BookingFlightDTO();
            bookingDto.setFlightId(id);
            modelMap.put("payment",bookingDto);
            modelMap.put("number",NumberPeople);
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
