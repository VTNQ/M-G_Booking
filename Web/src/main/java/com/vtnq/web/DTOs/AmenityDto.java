package com.vtnq.web.DTOs;

import java.io.Serializable;

/**
 * DTO for {@link com.vtnq.web.Entities.Amenity}
 */
public class AmenityDto implements Serializable {
    private String name;
    private String decription;

    public AmenityDto() {
    }

    public AmenityDto(String name, String decription) {
        this.name = name;
        this.decription = decription;
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
}