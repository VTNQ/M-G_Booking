package com.vtnq.web.Repositories;

import com.vtnq.web.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query(value = "FROM Account a WHERE a.email = :email")
    Optional<Account> findByEmail(@Param("email") String email);
}