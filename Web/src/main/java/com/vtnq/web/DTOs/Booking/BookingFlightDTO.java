package com.vtnq.web.DTOs.Booking;

import java.math.BigDecimal;
import java.util.List;

public class BookingFlightDTO {
    private int flightId;
    private BigDecimal TotalPrice;
    private int userId;
    private List<Integer> seatId;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        TotalPrice = totalPrice;
    }

    public int getFlightId() {
        return flightId;
    }

    public List<Integer> getSeatId() {
        return seatId;
    }

    public void setSeatId(List<Integer> seatId) {
        this.seatId = seatId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }
}
