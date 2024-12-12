package com.vtnq.web.DTOs;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.vtnq.web.Entities.Service}
 */
public class ServiceDto implements Serializable {
    private String name;
    private String decription;
    private BigDecimal price;

    public ServiceDto() {
    }

    public ServiceDto(String name, String decription, BigDecimal price) {
        this.name = name;
        this.decription = decription;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}