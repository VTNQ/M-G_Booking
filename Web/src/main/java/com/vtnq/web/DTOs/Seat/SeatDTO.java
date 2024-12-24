package com.vtnq.web.DTOs.Seat;

import java.math.BigDecimal;

public class SeatDTO {
    int firstClassSeat;
    int businessClassSeat;
    int economyClassSeat;
    int idFlight;

    public BigDecimal getPriceClassSeat() {
        return priceClassSeat;
    }

    public void setPriceClassSeat(BigDecimal priceClassSeat) {
        this.priceClassSeat = priceClassSeat;
    }

    public BigDecimal getPriceBusinessClassSeat() {
        return priceBusinessClassSeat;
    }

    public void setPriceBusinessClassSeat(BigDecimal priceBusinessClassSeat) {
        this.priceBusinessClassSeat = priceBusinessClassSeat;
    }

    public BigDecimal getPriceEconomyClassSeat() {
        return priceEconomyClassSeat;
    }

    public void setPriceEconomyClassSeat(BigDecimal priceEconomyClassSeat) {
        this.priceEconomyClassSeat = priceEconomyClassSeat;
    }

    public int getIdFlight() {
        return idFlight;
    }

    public void setIdFlight(int idFlight) {
        this.idFlight = idFlight;
    }
    private BigDecimal priceClassSeat;
    private BigDecimal priceBusinessClassSeat;
    private BigDecimal priceEconomyClassSeat;
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
