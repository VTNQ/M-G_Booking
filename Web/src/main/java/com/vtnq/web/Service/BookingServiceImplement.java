package com.vtnq.web.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.vtnq.web.DTOs.Booking.BookingFlightDTO;
import com.vtnq.web.DTOs.Booking.BookingFlightDetail;
import com.vtnq.web.DTOs.Booking.BookingHotelDTO;
import com.vtnq.web.DTOs.BookingFlightDto;
import com.vtnq.web.DTOs.HistoryOrder.HistoryOrderFlight;
import com.vtnq.web.DTOs.HistoryOrder.HistoryOrderHotel;
import com.vtnq.web.Entities.*;
import com.vtnq.web.Repositories.*;
import com.vtnq.web.WebSocket.SeatUpdateWebSocketHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookingServiceImplement implements BookingService {
    @Autowired
    private BookingFlightRepository bookingFlightRepository;
    @Autowired
    private BookingFlightDetailRepository bookingFlightDetailRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private SeatUpdateWebSocketHandler seatUpdateWebSocketHandler;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BookingRoomRepository bookingRoomRepository;
    @Autowired
    private BookingHotelDetailRepository hotelDetailRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public int addBooking(BookingFlightDTO bookingFlightDTO, String bookings) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BookingFlight booking = new BookingFlight();
            List<BookingFlightDetail> bookingsDetail = objectMapper.readValue(bookings, objectMapper.getTypeFactory().constructCollectionType(List.class, BookingFlightDetail.class));
            for (BookingFlightDetail bookingFlightDetail : bookingsDetail) {
                Seat seat = seatRepository.findById(bookingFlightDetail.getId())
                        .orElseThrow(() -> new RuntimeException("Seat Not Found"));
                if (seat.getStatus() != null && seat.getStatus() == 1) {
                    seatUpdateWebSocketHandler.notifySeatStatus(seat, false);
                    return 0;
                }
            }
            Account account = accountRepository.findById(bookingFlightDTO.getUserId()).orElseThrow(() -> new RuntimeException("Account Not Found"));

            booking.setUser(account);

            booking.setTotalPrice(bookingFlightDTO.getTotalPrice());
            BookingFlight saveBookingFlight = bookingFlightRepository.save(booking);
            for (BookingFlightDetail bookingFlightDetail : bookingsDetail) {
                com.vtnq.web.Entities.BookingFlightDetail bookingFlightDetailEntity = new com.vtnq.web.Entities.BookingFlightDetail();
                BookingFlight bookingFlight = bookingFlightRepository.findById(saveBookingFlight.getId())
                        .orElseThrow(() -> new RuntimeException("Booking Flight not found"));
                Flight flight = flightRepository.findById(bookingFlightDetail.getFlightId())
                        .orElseThrow(() -> new RuntimeException("Flight not Round"));
                bookingFlightDetailEntity.setFlight(flight);
                bookingFlightDetailEntity.setBookingFlight(bookingFlight);
                bookingFlightDetailEntity.setTotalPrice(bookingFlightDetail.getTotalPrice());
                Seat seat = seatRepository.findById(bookingFlightDetail.getId())
                        .orElseThrow(() -> new RuntimeException("Seat Not Found"));

                seat.setStatus(1);
                seatRepository.save(seat);
                bookingFlightDetailEntity.setSeat(seat);
                bookingFlightDetailRepository.save(bookingFlightDetailEntity);
            }
            Booking bookingFlight = new Booking();
            BookingFlight bookingFlightEntity = bookingFlightRepository.findById(saveBookingFlight.getId())
                    .orElseThrow(() -> new RuntimeException("Booking Flight not found"));
            bookingFlight.setBookingFlight(bookingFlightEntity);
            bookingFlight.setCreatedAt(Instant.now());
            bookingFlight.setUserId(bookingFlightDTO.getUserId());
            bookingFlight.setTotalPrice(bookingFlightDTO.getTotalPrice());
          Booking insertBooking=  bookingRepository.save(bookingFlight);

            List<Booking> userBookings = bookingRepository.FindBookingByUserId(account.getId());
            BigDecimal totalPrice = userBookings.stream()
                    .map(Booking::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            if (totalPrice.compareTo(new BigDecimal("10000000")) >= 0) {
                Level level = levelRepository.findById(2).orElseThrow(() -> new RuntimeException("Level Not Found"));
                account.setLevel(level);
                accountRepository.save(account);
            }
            return insertBooking.getId();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Seat getBookedSeatFromBookings(String bookings) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<BookingFlightDetail> bookingsDetail = objectMapper.readValue(bookings,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, BookingFlightDetail.class));
            if (!bookingsDetail.isEmpty()) {
                int seatId = bookingsDetail.get(0).getId();  // Assuming 'id' in BookingFlightDetail corresponds to seat id
                Seat seat = seatRepository.findById(seatId).orElse(null);
                return seat;// Assuming the first seat in the booking details is the one you're interested in
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addBookingHotel(BookingHotelDTO bookingHotelDTO, int QuantityRoom, BookingFlightDTO flightDTO, String bookings, BigDecimal amount) {
        try {
            Account account = accountRepository.findById(bookingHotelDTO.getUserId()).orElse(null);
            BookingRoom bookingRoom = new BookingRoom();
            bookingRoom.setUser(account);
            bookingRoom.setTotalPrice(bookingHotelDTO.getTotalPrice());
            BookingRoom insertBooking = bookingRoomRepository.save(bookingRoom);
            for (int i = 0; i < QuantityRoom; i++) {
                BookingRoomDetail bookingRoomDetail = new BookingRoomDetail();
                bookingRoomDetail.setBookingRoom(insertBooking);
                Room room = roomRepository.findByTypeId(bookingHotelDTO.getTypeId());
                if (room != null) {
                    room.setStatus(true);
                    roomRepository.save(room);
                }
                bookingRoomDetail.setRoom(room);
                bookingRoomDetail.setTotalPrice(bookingHotelDTO.getPrice());

                bookingRoomDetail.setCheckInDate(bookingHotelDTO.getCheckInDate());
                bookingRoomDetail.setCheckOutDate(bookingHotelDTO.getCheckOutDate());
                hotelDetailRepository.save(bookingRoomDetail);

            }
            ObjectMapper objectMapper = new ObjectMapper();
            BookingFlight booking = new BookingFlight();
            List<BookingFlightDetail> bookingsDetail = objectMapper.readValue(bookings, objectMapper.getTypeFactory().constructCollectionType(List.class, BookingFlightDetail.class));
            for (BookingFlightDetail bookingFlightDetail : bookingsDetail) {
                Seat seat = seatRepository.findById(bookingFlightDetail.getId())
                        .orElseThrow(() -> new RuntimeException("Seat Not Found"));
                if (seat.getStatus() != null && seat.getStatus() == 1) {
                    seatUpdateWebSocketHandler.notifySeatStatus(seat, false);
                    return false;
                }
            }
            Account accountDto = accountRepository.findById(flightDTO.getUserId()).orElseThrow(() -> new RuntimeException("Account Not Found"));

            booking.setUser(accountDto);

            booking.setTotalPrice(flightDTO.getTotalPrice());
            BookingFlight saveBookingFlight = bookingFlightRepository.save(booking);
            for (BookingFlightDetail bookingFlightDetail : bookingsDetail) {
                com.vtnq.web.Entities.BookingFlightDetail bookingFlightDetailEntity = new com.vtnq.web.Entities.BookingFlightDetail();
                BookingFlight bookingFlight = bookingFlightRepository.findById(saveBookingFlight.getId())
                        .orElseThrow(() -> new RuntimeException("Booking Flight not found"));
                Flight flight = flightRepository.findById(bookingFlightDetail.getFlightId())
                        .orElseThrow(() -> new RuntimeException("Flight not Round"));
                bookingFlightDetailEntity.setFlight(flight);
                bookingFlightDetailEntity.setBookingFlight(bookingFlight);
                bookingFlightDetailEntity.setTotalPrice(bookingFlightDetail.getTotalPrice());
                Seat seat = seatRepository.findById(bookingFlightDetail.getId())
                        .orElseThrow(() -> new RuntimeException("Seat Not Found"));

                seat.setStatus(1);
                seatRepository.save(seat);
                bookingFlightDetailEntity.setSeat(seat);
                bookingFlightDetailRepository.save(bookingFlightDetailEntity);
            }
            Booking bookingFlight = new Booking();
            BookingFlight bookingFlightEntity = bookingFlightRepository.findById(saveBookingFlight.getId())
                    .orElseThrow(() -> new RuntimeException("Booking Flight not found"));
            bookingFlight.setBookingFlight(bookingFlightEntity);
            bookingFlight.setBookingRoom(insertBooking);
            bookingFlight.setCreatedAt(Instant.now());
            bookingRepository.save(bookingFlight);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Booking> FindBookings(int id) {
        try {
            return bookingRepository.findBookingByCountry(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<com.vtnq.web.Entities.BookingFlightDetail> findBookingFlights(int id) {
        try {
            return bookingRepository.FindBookingByFlight(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public BigDecimal getTotalPrice(int id) {
        try {
            return bookingRepository.getBookingTotalPrice(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<BookingRoomDetail> getBookingRooms(int id) {
        try {
            return bookingRepository.getBookingRoomDetails(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public BigDecimal GetTotalPriceHotel(int id) {
        try {
            return bookingRepository.getBookingHotelPrice(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public int CountBookings(int id) {
        try {
            return bookingRepository.CountBooking(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<HistoryOrderFlight> FindHistoryOrderFlights(int id) {
        try {
            return bookingFlightRepository.FindFlightByUser(id);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<HistoryOrderHotel> FindHistoryOrderHotels(int id) {
        try {
           List<HistoryOrderHotel> historyOrderHotel= bookingRoomRepository.FindHotelById(id);
           for (HistoryOrderHotel h : historyOrderHotel) {
               h.setTotalRoom(bookingRoomRepository.CountBookingRoomByRoomId(h.getId()));
           }
           return historyOrderHotel;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


}
