package com.vtnq.web.DTOs.Airport;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link com.vtnq.web.Entities.Airport}
 */
public class AirportDto {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }
    @Min(value = 1,message = "City is required")
    private int idCity;
    @NotBlank(message = "Name is required")
    private String name;

    public AirportDto() {
    }

    public AirportDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}