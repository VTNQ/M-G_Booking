package com.vtnq.web.DTOs;

import java.io.Serializable;

/**
 * DTO for {@link com.vtnq.web.Entities.ContractOwner}
 */
public class ContractOwnerDto implements Serializable {
    private Integer ownerId;
    private Integer hotelId;
    private Double commissionRate;
    private String paymentTerms;
    private String termsAndConditions;
    private Boolean status = false;

    public ContractOwnerDto() {
    }

    public ContractOwnerDto(Integer ownerId, Integer hotelId, Double commissionRate, String paymentTerms, String termsAndConditions, Boolean status) {
        this.ownerId = ownerId;
        this.hotelId = hotelId;
        this.commissionRate = commissionRate;
        this.paymentTerms = paymentTerms;
        this.termsAndConditions = termsAndConditions;
        this.status = status;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Double commissionRate) {
        this.commissionRate = commissionRate;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}