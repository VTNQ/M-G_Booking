package com.vtnq.web.DTOs;

import java.io.Serializable;

/**
 * DTO for {@link com.vtnq.web.Entities.Amenity}
 */
public class AmenityDto implements Serializable {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String name;

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    private String decription;
    private int room_id;
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