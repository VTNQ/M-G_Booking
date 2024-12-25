package com.vtnq.web.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "booking_flight")
public class BookingFlight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seat_trip_id", nullable = false)
    private Seat seatTrip;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Account user;

    @NotNull
    @Column(name = "total_price", nullable = false, precision = 10)
    private BigDecimal totalPrice;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_return_id")
    private Seat seatReturn;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id_return_trip")
    private Flight flightIdReturnTrip;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "flight_id_trip", nullable = false)
    private Flight flightIdTrip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Seat getSeatTrip() {
        return seatTrip;
    }

    public void setSeatTrip(Seat seatTrip) {
        this.seatTrip = seatTrip;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Seat getSeatReturn() {
        return seatReturn;
    }

    public void setSeatReturn(Seat seatReturn) {
        this.seatReturn = seatReturn;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Flight getFlightIdReturnTrip() {
        return flightIdReturnTrip;
    }

    public void setFlightIdReturnTrip(Flight flightIdReturnTrip) {
        this.flightIdReturnTrip = flightIdReturnTrip;
    }

    public Flight getFlightIdTrip() {
        return flightIdTrip;
    }

    public void setFlightIdTrip(Flight flightIdTrip) {
        this.flightIdTrip = flightIdTrip;
    }

}