package com.vtnq.web.DTOs;

import java.io.Serializable;

/**
 * DTO for {@link com.vtnq.web.Entities.Airline}
 */
public class AirlineDto implements Serializable {
    private String name;

    public AirlineDto() {
    }

    public AirlineDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}