package com.vtnq.web.DTOs.Flight;

public class SearchFlightDTO {
    private int departureAirport;
    private int arrivalAirport;
    private String departureTime;

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    private String TypeFlight;
    private boolean selectedHotel;
    private String checkInTime;
    private String checkOutTime;
    public boolean isRoundTrip() {

        return IsRoundTrip;
    }

    public void setRoundTrip(boolean roundTrip) {
        IsRoundTrip = roundTrip;
        if(IsRoundTrip && (this.arrivalTime==null || this.arrivalTime.isEmpty())){
            throw new IllegalArgumentException("Arrival time Required");
        }
    }

    private boolean IsRoundTrip;

    public int getNumberPeopleRight() {
        return numberPeopleRight;
    }

    public void setNumberPeopleRight(int numberPeopleRight) {
        this.numberPeopleRight = numberPeopleRight;
    }

    private int numberPeopleRight;
    public int getQuantityRoom() {
        return QuantityRoom;
    }

    public void setQuantityRoom(int quantityRoom) {
        QuantityRoom = quantityRoom;
    }

    private int idCity;
    private int QuantityRoom;

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        if(selectedHotel && idCity>0){
            throw new IllegalArgumentException("city Required");
        }
        this.idCity = idCity;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public boolean isSelectedHotel() {
        return selectedHotel;
    }

    public void setSelectedHotel(boolean selectedHotel) {
        this.selectedHotel = selectedHotel;

    }

    public void setArrivalTime(String arrivalTime) {
        if(IsRoundTrip &&(arrivalTime==null || arrivalTime.isEmpty())){
            throw new IllegalArgumentException("Arrival time Required");
        }
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
