package com.vtnq.web.Entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "booking_flight")
public class BookingFlight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "flight_id_trip", nullable = false)
    private DetailFlight flightIdTrip;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Account user;

    @Column(name = "total_price", nullable = false, precision = 10)
    private BigDecimal totalPrice;

    @Column(name = "status", nullable = false)
    private Boolean status = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id_return_trip")
    private DetailFlight flightIdReturnTrip;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DetailFlight getFlightIdTrip() {
        return flightIdTrip;
    }

    public void setFlightIdTrip(DetailFlight flightIdTrip) {
        this.flightIdTrip = flightIdTrip;
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

    public DetailFlight getFlightIdReturnTrip() {
        return flightIdReturnTrip;
    }

    public void setFlightIdReturnTrip(DetailFlight flightIdReturnTrip) {
        this.flightIdReturnTrip = flightIdReturnTrip;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

}