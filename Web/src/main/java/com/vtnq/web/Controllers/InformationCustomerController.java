package com.vtnq.web.Controllers;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.vtnq.web.DTOs.Booking.BookingFlightDTO;
import com.vtnq.web.DTOs.Booking.BookingFlightDetail;
import com.vtnq.web.DTOs.Booking.BookingListFly;
import com.vtnq.web.Entities.Account;
import com.vtnq.web.Entities.Flight;
import com.vtnq.web.Entities.Seat;
import com.vtnq.web.Repositories.FlightRepository;
import com.vtnq.web.Service.BookingService;
import com.vtnq.web.Service.FlightService;
import com.vtnq.web.Service.PayPalService;
import com.vtnq.web.Service.SeatService;
import com.vtnq.web.WebSocket.SeatUpdateWebSocketHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    private SeatUpdateWebSocketHandler seatUpdateWebSocketHandler;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private FlightService flightService;
    private static final String SUCCESS_URL = "payFlight/success";
    private static final String CANCEL_URL = "payFlight/cancel";
    @GetMapping("payFlight")
    public RedirectView payment(@RequestParam(required = true) double amount,
                                @RequestParam(defaultValue = "JPY") String currency, @ModelAttribute BookingFlightDTO bookingFlightDTO, @RequestParam("bookings") String bookingsJson, HttpSession session, HttpServletRequest request) {
        try {
            Account currentAccount = (Account) request.getSession().getAttribute("currentAccount");
            bookingFlightDTO.setUserId(currentAccount.getId());
            bookingFlightDTO.setTotalPrice(BigDecimal.valueOf(amount));
            session.setAttribute("booking", bookingFlightDTO);
            session.setAttribute("bookings", bookingsJson);
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
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, HttpServletRequest request) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);
            BookingFlightDTO bookingFlightDto = (BookingFlightDTO) request.getSession().getAttribute("booking");
            String bookings = (String) request.getSession().getAttribute("bookings");

            if (payment.getState().equals("approved") && bookingService.addBooking(bookingFlightDto, bookings)) {
                return "redirect:/Success";
            }


            try {
                Seat bookedSeat = bookingService.getBookedSeatFromBookings(bookings);
                seatUpdateWebSocketHandler.notifySeatStatus(bookedSeat, false);
            } catch (IOException e) {
                e.printStackTrace();  // Log the error or handle it as needed
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("InformationCustomer/{id}")
    public String InformationCustomer(@PathVariable int id) {
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
            List<Integer>idFlight=(List<Integer>) request.getSession().getAttribute("idFlight");
            List<BookingListFly>FlightBooking=new ArrayList<>();
            List<Flight>flights=new ArrayList<>();
            if(idFlight!=null){
                for (Integer i:idFlight) {
                    boolean existsFlightTab=flights.stream().anyMatch(flight->flight.getId()==i);
                    if(!existsFlightTab){
                        Flight flight=flightRepository.findById(i).get();
                        flights.add(flight);
                    }
                    boolean existBookingListFly=FlightBooking.stream().anyMatch(booking->booking.
                            getId()==i);
                    if(!existBookingListFly){
                        BookingListFly bookingListFly=flightService.getResultPaymentFlight(i);
                        FlightBooking.add(bookingListFly);
                    }

                }
                boolean existFlight=idFlight.stream().anyMatch(flight->flight.equals(id));
                if(!existFlight){
                    idFlight.add(id);
                }

                modelMap.put("idFlight",idFlight);

            }
            boolean existsFlightTab=flights.stream().anyMatch(flight->flight.getId()==id);
            if(!existsFlightTab){
                Flight flight=flightRepository.findById(id).get();
                flights.add(flight);
            }
            boolean existBookingListFly=FlightBooking.stream().anyMatch(booking->booking.
                    getId()==id);
            if(!existBookingListFly){
                FlightBooking.add(flightService.getResultPaymentFlight(id));
            }


            Integer NumberPeople = (Integer) request.getSession().getAttribute("NumberPeople");
            modelMap.put("flight",FlightBooking);
            modelMap.put("flightTab",flights);
            BookingFlightDTO bookingDto=new BookingFlightDTO();
            modelMap.put("payment",bookingDto);
            modelMap.put("number",NumberPeople);
            int paymentTimeOut=20*60;
            modelMap.put("timeout",paymentTimeOut);
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
