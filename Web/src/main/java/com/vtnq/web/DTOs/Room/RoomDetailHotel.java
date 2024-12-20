package com.vtnq.web.DTOs.Room;

import com.vtnq.web.DTOs.Amenities.AmenitiesList;

import java.math.BigDecimal;
import java.util.List;

public class RoomDetailHotel {
    public int id;
    private String type;
    private BigDecimal price;

    public RoomDetailHotel(int id, String type, BigDecimal price, int occupancy) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.occupancy = occupancy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }

    public List<AmenitiesList> getAmenitiesLists() {
        return amenitiesLists;
    }

    public void setAmenitiesLists(List<AmenitiesList> amenitiesLists) {
        this.amenitiesLists = amenitiesLists;
    }

    private int occupancy;
    private List<AmenitiesList>amenitiesLists;
}
