package com.vtnq.web.DTOs.Seat;

public class SeatDTO {
    int firstClassSeat;
    int businessClassSeat;
    int economyClassSeat;
    int idFlight;

    public int getIdFlight() {
        return idFlight;
    }

    public void setIdFlight(int idFlight) {
        this.idFlight = idFlight;
    }

    public int getFirstClassSeat() {
        return firstClassSeat;
    }

    public void setFirstClassSeat(int firstClassSeat) {
        this.firstClassSeat = firstClassSeat;
    }

    public int getBusinessClassSeat() {
        return businessClassSeat;
    }

    public void setBusinessClassSeat(int businessClassSeat) {
        this.businessClassSeat = businessClassSeat;
    }

    public int getEconomyClassSeat() {
        return economyClassSeat;
    }

    public void setEconomyClassSeat(int economyClassSeat) {
        this.economyClassSeat = economyClassSeat;
    }
}
