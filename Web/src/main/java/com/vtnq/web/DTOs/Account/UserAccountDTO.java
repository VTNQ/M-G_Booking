package com.vtnq.web.DTOs.Account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.LocalDate;

public class UserAccountDTO {
    private Integer id;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    private String phone;
    private String fullName;
    private String username;
    private String email;
    private int level_id;

    public int getLevel_id() {
        return level_id;
    }

    public void setLevel_id(int level_id) {
        this.level_id = level_id;
    }

    public UserAccountDTO() {
    }

    @JsonCreator
    public UserAccountDTO(
            @JsonProperty("id") Integer id,
            @JsonProperty("avatar") String avatar,
            @JsonProperty("cityId") int cityId,
            @JsonProperty("address") String address,
            @JsonProperty("email") String email,
            @JsonProperty("username") String username,
            @JsonProperty("fullName") String fullName,
            @JsonProperty("accountType") String accountType,
            @JsonProperty("countryId") int countryId,
            @JsonProperty("phone") String phone,
            @JsonProperty("level_id") int level_id,
            @JsonProperty("password")String password) {
        this.id = id;
        this.Avatar = avatar;
        this.CityId = cityId;
        this.Address = address;
        this.email = email;
        this.username = username;
        this.fullName = fullName;
        this.AccountType = accountType;
        this.CountryId = countryId;
        this.phone = phone;
        this.level_id = level_id;
        this.password = password;
    }


    public UserAccountDTO(int idSecurityCode, String valueCode, LocalDate startAt, LocalDate endAt, String location, LocalDate dob, String front_security_code, String back_security_code) {
        this.idSecurityCode = idSecurityCode;
        ValueCode = valueCode;
        StartAt = startAt;
        EndAt = endAt;
        this.location = location;
        this.dob = dob;
        this.front_security_code = front_security_code;
        this.back_security_code = back_security_code;
    }

    private String Address;
    private int CityId;
    private int idSecurityCode;
    private String Avatar;
    private MultipartFile AvatarFile;
    private String AccountType;
    private int CountryId;
    private String ValueCode;

    public LocalDate getStartAt() {
        return StartAt;
    }

    public void setStartAt(LocalDate startAt) {
        StartAt = startAt;
    }

    public LocalDate getEndAt() {
        return EndAt;
    }

    public void setEndAt(LocalDate endAt) {
        EndAt = endAt;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    private LocalDate StartAt;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public int getIdSecurityCode() {
        return idSecurityCode;
    }

    public void setIdSecurityCode(int idSecurityCode) {
        this.idSecurityCode = idSecurityCode;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public MultipartFile getAvatarFile() {
        return AvatarFile;
    }

    public void setAvatarFile(MultipartFile avatarFile) {
        AvatarFile = avatarFile;
    }

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String accountType) {
        AccountType = accountType;
    }

    public int getCountryId() {
        return CountryId;
    }

    public void setCountryId(int countryId) {
        CountryId = countryId;
    }

    public String getValueCode() {
        return ValueCode;
    }

    public void setValueCode(String valueCode) {
        ValueCode = valueCode;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getFront_security_code() {
        return front_security_code;
    }

    public void setFront_security_code(String front_security_code) {
        this.front_security_code = front_security_code;
    }

    public String getBack_security_code() {
        return back_security_code;
    }

    public void setBack_security_code(String back_security_code) {
        this.back_security_code = back_security_code;
    }

    public MultipartFile getFront_security_file() {
        return front_security_file;
    }

    public void setFront_security_file(MultipartFile front_security_file) {
        this.front_security_file = front_security_file;
    }

    public MultipartFile getBack_security_file() {
        return back_security_file;
    }

    public void setBack_security_file(MultipartFile back_security_file) {
        this.back_security_file = back_security_file;
    }

    private LocalDate EndAt;
    private String location;
    private LocalDate dob;
    private String front_security_code;
    private String back_security_code;
    private MultipartFile front_security_file;
    private MultipartFile back_security_file;
}
