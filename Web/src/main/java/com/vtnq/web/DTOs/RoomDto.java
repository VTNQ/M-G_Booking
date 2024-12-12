package com.vtnq.web.DTOs;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.vtnq.web.Entities.Room}
 */
public class RoomDto implements Serializable {
    private String type;
    private BigDecimal price;
    private Integer occupancy;
    private Boolean status = false;

    public RoomDto() {
    }

    public RoomDto(String type, BigDecimal price, Integer occupancy, Boolean status) {
        this.type = type;
        this.price = price;
        this.occupancy = occupancy;
        this.status = status;
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

    public Integer getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(Integer occupancy) {
        this.occupancy = occupancy;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}