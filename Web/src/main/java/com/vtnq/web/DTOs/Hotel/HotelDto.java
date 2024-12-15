package com.vtnq.web.DTOs.Hotel;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link com.vtnq.web.Entities.Hotel}
 */
public class HotelDto implements Serializable {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Address is required")
    private String address;
    @Min(value = 1,message = "city is required")
    private Integer cityId;
    @NotBlank(message = "Description is required")
    private String decription;
    @Min(value = 1,message = "Owner Is required")
    private Integer ownerId;
    private Integer ratingId;

    public HotelDto() {
    }

    public HotelDto(String name, String address, Integer cityId, String decription, Integer ownerId, Integer ratingId) {
        this.name = name;
        this.address = address;
        this.cityId = cityId;
        this.decription = decription;
        this.ownerId = ownerId;
        this.ratingId = ratingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getRatingId() {
        return ratingId;
    }

    public void setRatingId(Integer ratingId) {
        this.ratingId = ratingId;
    }
}