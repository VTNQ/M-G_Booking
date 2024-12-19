package com.vtnq.web.DTOs.Flight;


import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public class DetailFlightDTO {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    private Integer id;
    private String type;
    @Min(value = 1,message = "Price is required")
    private BigDecimal price;
    @Min(value = 1,message = "Quantity is required")
    private Integer quantity;
    private int idFlight;

    public int getIdFlight() {
        return idFlight;
    }

    public void setIdFlight(int idFlight) {
        this.idFlight = idFlight;
    }
}
