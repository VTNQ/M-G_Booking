package com.vtnq.web.DTOs;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.vtnq.web.Entities.Flight}
 */
public class FlightDto implements Serializable {
    private Instant departureTime;
    private Instant arrivalTime;

    public FlightDto() {
    }

    public FlightDto(Instant departureTime, Instant arrivalTime) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Instant getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Instant departureTime) {
        this.departureTime = departureTime;
    }

    public Instant getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Instant arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}