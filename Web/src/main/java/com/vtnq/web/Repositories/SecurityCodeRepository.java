package com.vtnq.web.Repositories;

import com.vtnq.web.DTOs.Account.UserAccountDTO;
import com.vtnq.web.Entities.SecurityCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SecurityCodeRepository extends JpaRepository<SecurityCode, Integer> {
    @Query("select new com.vtnq.web.DTOs.Account.UserAccountDTO(a.id,a.valueCode,a.startAt,a.endAt,a.location,a.dob,a.frontSecurityCode,a.backSecurityCode) from SecurityCode a where a.id = :id")
    UserAccountDTO findByIdAccount(int id);
}