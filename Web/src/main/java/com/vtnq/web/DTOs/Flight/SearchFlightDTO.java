package com.vtnq.web.DTOs.Flight;

public class SearchFlightDTO {
    private int departureAirport;
    private int arrivalAirport;
    private String departureTime;
    private String TypeFlight;

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    private String arrivalTime;
    public String getTypeFlight() {
        return TypeFlight;
    }

    public void setTypeFlight(String typeFlight) {
        TypeFlight = typeFlight;
    }

    public int getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(int arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(int departureAirport) {
        this.departureAirport = departureAirport;
    }
}
