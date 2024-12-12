package com.vtnq.web.DTOs;

import java.io.Serializable;

/**
 * DTO for {@link com.vtnq.web.Entities.District}
 */
public class DistrictDto implements Serializable {
    private String name;

    public DistrictDto() {
    }

    public DistrictDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}