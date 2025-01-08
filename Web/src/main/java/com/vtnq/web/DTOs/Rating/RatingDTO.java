package com.vtnq.web.DTOs.Rating;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class RatingDTO {
    private int id;
    private String Avatar;
    private String Name;
    private Double Rating;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private Instant createRating;
    private String CreateDate;
    private String description;

    public String getCreateDate() {
        return CreateDate;
    }

    public RatingDTO(int id, String avatar, String name,String description, Double rating, Instant createRating) {
        this.id = id;
        Avatar = avatar;
        Name = name;
        this.description = description;
        Rating = rating;
        this.createRating = createRating;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getFormattedCreateRating() {
        if (createRating == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm a", Locale.ENGLISH);
        return formatter.format(createRating.atZone(ZoneId.of("UTC")));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getRating() {
        return Rating;
    }

    public void setRating(Double rating) {
        Rating = rating;
    }

    public Instant getCreateRating() {
        return createRating;
    }

    public void setCreateRating(Instant createRating) {
        this.createRating = createRating;
    }
}
