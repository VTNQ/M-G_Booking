package com.vtnq.web.DTOs.Flight;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ResultFlightDTO {
    private String imageUrl;
    private int id;
    private int idFlight;
    private String nameCity;

    public int getIdFlight() {
        return idFlight;
    }

    public void setIdFlight(int idFlight) {
        this.idFlight = idFlight;
    }

    private String timeArrival;
    private String DateDepart;
    private String DateArrival;
    private long durationHours;
    private long durationMinutes;
    private String durationString; // Thêm thuộc tính lưu khoảng cách dưới dạng chuỗi
    private String nameAiport;

    public String getNameAiport() {
        return nameAiport;
    }

    public void setNameAiport(String nameAiport) {
        this.nameAiport = nameAiport;
    }

    public String getDurationString() {
        return durationString;
    }

    private void calculateAndStoreDuration() {
        if (arrivalTime != null && departureTime != null) {
            Duration duration = Duration.between(departureTime, arrivalTime);
            this.durationHours = duration.toHours();
            this.durationMinutes = duration.toMinutes() % 60;
            this.durationString = String.format("%02d:%02d", durationHours, durationMinutes); // Định dạng HH:mm
        } else {
            this.durationHours = 0;
            this.durationMinutes = 0;
            this.durationString = "00:00"; // Khoảng cách mặc định
        }
    }

    public String getDateDepart() {
        return DateDepart;
    }

    public void setDateDepart(String dateDepart) {
        DateDepart = dateDepart;
    }

    public String getDateArrival() {
        return DateArrival;
    }

    public void setDateArrival(String dateArrival) {
        DateArrival = dateArrival;
    }

    public String getTimeArrival() {
        return timeArrival;
    }

    public void setTimeArrival(String timeArrival) {
        this.timeArrival = timeArrival;
    }

    public String getTimeDepart() {
        return timeDepart;
    }

    public void setTimeDepart(String timeDepart) {
        this.timeDepart = timeDepart;
    }

    private String timeDepart;

    public String getNameAirline() {
        return nameAirline;
    }

    private String formatDate(Instant time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yy")
                .withZone(ZoneId.of("UTC"));
        return formatter.format(time);
    }

    public void setNameAirline(String nameAirline) {
        this.nameAirline = nameAirline;
    }

    private String formatTime(Instant time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm")
                .withZone(ZoneId.of("UTC"));
        return formatter.format(time);
    }

    private String nameAirline;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Instant arrivalTime;
    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Instant getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Instant arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Instant getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Instant departureTime) {
        this.departureTime = departureTime;
    }

    private Instant departureTime;

    public ResultFlightDTO(int id, String imageUrl, String nameCity, Instant arrivalTime, Instant departureTime, BigDecimal price,
                           String nameAirline, Instant TimeDepart, Instant TimeArrival, Instant DateDepart, Instant DateArrival, String nameAiport,
                           int idFlight) {
        this.imageUrl = imageUrl;
        this.nameCity = nameCity;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.price = price;
        this.id = id;
        this.nameAirline = nameAirline;
        this.timeDepart = formatTime(TimeDepart);
        this.timeArrival = formatTime(TimeArrival);
        this.DateDepart = formatDate(departureTime);
        this.DateArrival = formatDate(arrivalTime);
        calculateAndStoreDuration();
        this.nameAiport=nameAiport;
        this.idFlight=idFlight;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
