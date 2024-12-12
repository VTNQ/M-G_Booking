package com.vtnq.web.Repositories;

import com.vtnq.web.Entities.SecurityCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityCodeRepository extends JpaRepository<SecurityCode, Integer> {
}