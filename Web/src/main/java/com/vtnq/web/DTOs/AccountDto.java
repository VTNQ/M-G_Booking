package com.vtnq.web.DTOs;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.vtnq.web.Entities.Account}
 */
public class AccountDto implements Serializable {
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Integer cityId;
    private String otp;
    private String avatar;
    private String password;
    private String accountType;
    private Integer countryId;
    private Instant createdOTP;

    public AccountDto() {
    }

    public AccountDto(String username, String fullName, String email, String phone, String address, Integer cityId, String otp, String avatar, String password, String accountType, Integer countryId, Instant createdOTP) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.cityId = cityId;
        this.otp = otp;
        this.avatar = avatar;
        this.password = password;
        this.accountType = accountType;
        this.countryId = countryId;
        this.createdOTP = createdOTP;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Instant getCreatedOTP() {
        return createdOTP;
    }

    public void setCreatedOTP(Instant createdOTP) {
        this.createdOTP = createdOTP;
    }
}