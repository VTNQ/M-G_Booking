package com.vtnq.web.Service;

import com.vtnq.web.DTOs.Account.AccountDto;
import com.vtnq.web.DTOs.Account.AdminAccountList;
import com.vtnq.web.Entities.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AuthService extends UserDetailsService {
    public Account GetAccountByEmail(String Email);
    public boolean RegisterAdmin(AccountDto accountDto);
    public boolean emailExists(String email);
    public boolean existPhone(String phone);
    public boolean existFullName(String fullName);
    public List<AdminAccountList>getAdmin();
}
