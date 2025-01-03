package com.vtnq.web.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.vtnq.web.DTOs.Booking.*;
import com.vtnq.web.DTOs.Booking.BookingFlightDetail;
import com.vtnq.web.DTOs.Flight.SearchFlightDTO;
import com.vtnq.web.Entities.*;
import com.vtnq.web.Repositories.BookingRepository;
import com.vtnq.web.Repositories.FlightRepository;
import com.vtnq.web.Repositories.SeatRepository;
import com.vtnq.web.Service.*;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping({"","/"})
public class InformationCustomerController {
    @Autowired
    private PayPalService payPalService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private SeatService seatService;
    @Autowired
    private SeatUpdateWebSocketHandler seatUpdateWebSocketHandler;

    @Autowired
    private FlightService flightService;
    private static final String SUCCESS_URL = "payFlight/success";
    private static final String CANCEL_URL = "payFlight/cancel";
    private static final String SuccessHotel="payHotelFlight/success";
   @GetMapping("payHotelFlight")
   public RedirectView paymentHotelFlight(@RequestParam(required = true) double amount,
                                          @RequestParam(defaultValue = "JPY") String currency, @ModelAttribute BookingHotelDTO bookingHotelDTO,@ModelAttribute BookingFlightDTO bookingFlightDTO,HttpServletRequest request,
                                          HttpSession session,@RequestParam("bookings") String bookingsJson) {
       try {
           Account currentAccount = (Account) request.getSession().getAttribute("currentAccount");
           bookingHotelDTO.setUserId(currentAccount.getId());
           SearchFlightDTO resultFlightDTO = (SearchFlightDTO) request.getSession().getAttribute("searchFlightDTO");
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
           LocalDate CheckInDate = LocalDate.parse(resultFlightDTO.getCheckInTime(), formatter);
           LocalDate CheckOutDate = LocalDate.parse(resultFlightDTO.getCheckOutTime(), formatter);
           bookingHotelDTO.setCheckInDate(CheckInDate);
           bookingHotelDTO.setCheckOutDate(CheckOutDate);
           bookingFlightDTO.setUserId(currentAccount.getId());
           session.setAttribute("amount", BigDecimal.valueOf(amount));
           session.setAttribute("Hotel",bookingHotelDTO);
           session.setAttribute("booking", bookingFlightDTO);
           session.setAttribute("bookings", bookingsJson);
           Payment payment = payPalService.createPayment(amount, currency, "paypal",
                   "sale", "Test payment", "http://localhost:8686/" + CANCEL_URL,
                   "http://localhost:8686/" + SuccessHotel);
           for (Links link : payment.getLinks()) {
               if (link.getRel().equals("approval_url")) {
                   return new RedirectView(link.getHref());
               }
           }
       }catch (Exception e){
           e.printStackTrace();
       }
       return new RedirectView("/");
   }
   @GetMapping("InformationFlightHotel/{id}")
   public String InformationFlightHotel(ModelMap modelMap,@PathVariable int id,HttpServletRequest request) {
       try {
           SearchFlightDTO resultFlightDTO=(SearchFlightDTO) request.getSession().getAttribute("searchFlightDTO");
           List<Integer>idFlight=(List<Integer>) request.getSession().getAttribute("idFlight");

           List<BookingListFly>FlightBooking=new ArrayList<>();
           List<Flight>flights=new ArrayList<>();
           if(idFlight!=null){

               for (Integer i:idFlight) {
                   boolean existsFlightTab=flights.stream().anyMatch(flight->flight.getId()==i);
                   if(!existsFlightTab){
                       Flight flight=flightRepository.findById(i).orElse(null);
                       if(flight!=null){
                           flights.add(flight);
                       }

                   }
                   boolean existBookingListFly=FlightBooking.stream().anyMatch(booking->booking.
                           getId()==i);
                   if(!existBookingListFly){
                       BookingListFly bookingListFly=flightService.getResultPaymentFlight(i);
                       FlightBooking.add(bookingListFly);
                   }
               }
               modelMap.put("idFlight",idFlight);
           }
           BookingHotel bookingHotel=hotelService.FindBookingHotel(id);
           modelMap.put("hotel",hotelService.FindBookingHotel(id));
           BigDecimal total = bookingHotel.getPrice()
                   .multiply(BigDecimal.valueOf(resultFlightDTO.getNumberPeopleRight()));
           modelMap.put("flight",FlightBooking);
           modelMap.put("searchFlightDTO",resultFlightDTO);
           int paymentTimeOut=20*60;
           modelMap.put("flightTab",flights);
           modelMap.put("timeout",paymentTimeOut);
           modelMap.put("flight",FlightBooking);
           modelMap.put("total",total);
           BookingHotelDTO hotelDTO=new BookingHotelDTO();
           hotelDTO.setTypeId(id);
           hotelDTO.setPrice(bookingHotel.getPrice());
           hotelDTO.setTotalPrice(total);
           hotelDTO.setQuantity(resultFlightDTO.getQuantityRoom());
           modelMap.put("hotelDTO",hotelDTO);
           Integer NumberPeople = (Integer) request.getSession().getAttribute("NumberPeople");
           modelMap.put("number",NumberPeople);
           BookingFlightDTO bookingDto=new BookingFlightDTO();
           modelMap.put("payment",bookingDto);
           return "/User/InformationCustomer/InformationCustomer";
       }catch (Exception e) {
           e.printStackTrace();
           return null;
       }
   }
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
    @GetMapping(SuccessHotel)
    public String SuccessHotel(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, HttpServletRequest request){
       try {
           Payment payment = payPalService.executePayment(paymentId, payerId);
           BookingHotelDTO bookingHotelDTO=(BookingHotelDTO)request.getSession().getAttribute("Hotel");
           BookingFlightDTO bookingFlightDto = (BookingFlightDTO) request.getSession().getAttribute("booking");
           String bookings = (String) request.getSession().getAttribute("bookings");
            BigDecimal amount=(BigDecimal) request.getSession().getAttribute("amount");
           if(payment.getState().equals("approved") && bookingService.addBookingHotel(bookingHotelDTO,bookingHotelDTO.getQuantity(),bookingFlightDto,bookings,amount)){
                return "redirect:/Success";
            }
       }catch (Exception e){
           e.printStackTrace();

       }
        return "redirect:/";
    }
    @GetMapping(SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, HttpServletRequest request,HttpSession session) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);
            BookingFlightDTO bookingFlightDto = (BookingFlightDTO) request.getSession().getAttribute("booking");
            String bookings = (String) request.getSession().getAttribute("bookings");
            int Booking= bookingService.addBooking(bookingFlightDto, bookings);
            if (payment.getState().equals("approved") && Booking>0) {
                session.setAttribute("idBooking",Booking);
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
    public String InformationCustomer(ModelMap modelMap,@PathVariable int id,HttpServletRequest request) {
        try {
            SearchFlightDTO resultFlightDTO=(SearchFlightDTO) request.getSession().getAttribute("searchFlightDTO");
            List<Integer>idFlight=(List<Integer>) request.getSession().getAttribute("idFlight");
            List<BookingListFly>FlightBooking=new ArrayList<>();
            List<Flight>flights=new ArrayList<>();
            if(idFlight!=null){
                for (Integer i:idFlight) {
                    boolean existsFlightTab=flights.stream().anyMatch(flight->flight.getId()==i);
                    if(!existsFlightTab){
                        Flight flight=flightRepository.findById(i).orElse(null);
                        if(flight!=null){
                            flights.add(flight);
                        }

                    }
                    boolean existBookingListFly=FlightBooking.stream().anyMatch(booking->booking.
                            getId()==i);
                    if(!existBookingListFly){
                        BookingListFly bookingListFly=flightService.getResultPaymentFlight(i);
                        FlightBooking.add(bookingListFly);
                    }
                }
                modelMap.put("idFlight",idFlight);
            }
            BookingHotel bookingHotel=hotelService.FindBookingHotel(id);
            modelMap.put("hotel",hotelService.FindBookingHotel(id));
            BigDecimal total = bookingHotel.getPrice()
                    .multiply(BigDecimal.valueOf(resultFlightDTO.getNumberPeopleRight()));
            modelMap.put("flight",FlightBooking);
            modelMap.put("searchFlightDTO",resultFlightDTO);
            int paymentTimeOut=20*60;
            modelMap.put("flightTab",flights);
            modelMap.put("timeout",paymentTimeOut);
            modelMap.put("flight",FlightBooking);
            modelMap.put("total",total);
            BookingHotelDTO hotelDTO=new BookingHotelDTO();
            hotelDTO.setTypeId(id);
            hotelDTO.setPrice(bookingHotel.getPrice());
            hotelDTO.setTotalPrice(total);
            hotelDTO.setQuantity(resultFlightDTO.getQuantityRoom());
            modelMap.put("hotelDTO",hotelDTO);
            Integer NumberPeople = (Integer) request.getSession().getAttribute("NumberPeople");
            modelMap.put("number",NumberPeople);
            BookingFlightDTO bookingDto=new BookingFlightDTO();
            modelMap.put("payment",bookingDto);
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
    public String Success(HttpServletRequest request,ModelMap modelMap) {
        try {
            Integer idBooking=(Integer)request.getSession().getAttribute("idBooking");
            Booking booking=bookingRepository.findById(idBooking).
                    orElseThrow(()->new RuntimeException("Booking not found"));

            modelMap.put("Booking",booking);
            return "/User/InformationCustomer/Success";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
