package com.vtnq.web.DTOs.Flight;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class FlightListDTO { private int id;
    private String nameAirline;
    private String departure_airport;
    private String arrival_airport;
    private Instant departure_time;
    private Instant arrival_time;

    public Instant getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(Instant departure_time) {
        this.departure_time = departure_time;
    }

    public Instant getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(Instant arrival_time) {
        this.arrival_time = arrival_time;
    }

    public FlightListDTO(int id, String nameAirline, String departure_airport, String arrival_airport,Instant departure_time, Instant arrival_time) {
        this.id = id;
        this.nameAirline = nameAirline;
        this.departure_airport = departure_airport;
        this.arrival_airport = arrival_airport;
        this.departure_time = departure_time;
        this.arrival_time = arrival_time;
    }

    public String getDeparture_airport() {
        return departure_airport;
    }

    public void setDeparture_airport(String departure_airport) {
        this.departure_airport = departure_airport;
    }

    public String getArrival_airport() {
        return arrival_airport;
    }

    public void setArrival_airport(String arrival_airport) {
        this.arrival_airport = arrival_airport;
    }

    public String getNameAirline() {
        return nameAirline;
    }

    public void setNameAirline(String nameAirline) {
        this.nameAirline = nameAirline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
