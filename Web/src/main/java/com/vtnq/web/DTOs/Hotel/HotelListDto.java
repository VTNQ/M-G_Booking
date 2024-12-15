package com.vtnq.web.DTOs.Hotel;

public class HotelListDto {
    private int id;
    private String name;
    private String nameDistrict;
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public HotelListDto(int id, String name, String nameDistrict, String imageUrl) {
        this.id = id;
        this.name = name;
        this.nameDistrict = nameDistrict;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameDistrict() {
        return nameDistrict;
    }

    public void setNameDistrict(String nameDistrict) {
        this.nameDistrict = nameDistrict;
    }

}
