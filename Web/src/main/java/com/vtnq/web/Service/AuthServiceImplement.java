package com.vtnq.web.Service;

import com.vtnq.web.DTOs.Account.AccountDto;
import com.vtnq.web.DTOs.Account.AdminAccountList;
import com.vtnq.web.DTOs.Account.RegisterUser;
import com.vtnq.web.Entities.Account;
import com.vtnq.web.Entities.Level;
import com.vtnq.web.Repositories.AccountRepository;
import com.vtnq.web.Repositories.LevelRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthServiceImplement implements AuthService {
    @Autowired
    private JavaMailSenderImpl mailSender;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(account.getAccountType()));
        return new org.springframework.security.core.userdetails.User(account.getEmail(), account.getPassword(), authorities);
    }

    private void SendConfirmationEmail(String Email, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(Email);
        message.setSubject("Account Registration Confirmation");
        message.setText(text);

        mailSender.send(message);
    }

    @Override
    public Account GetAccountByEmail(String Email) {
        return accountRepository.findByEmail(Email).orElse(null);
    }

    private String GeneateUsername(String Email) {
        if (Email != null && Email.endsWith("@gmail.com")) {
            String baseName = Email.substring(0, Email.indexOf("@"));
            int randomNumber = new Random().nextInt(1000);
            return baseName + randomNumber;
        }
        return null;
    }

    private String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }

        return password.toString();
    }

    @Override
    public boolean RegisterAdmin(AccountDto accountDto) {
        try {
            Account account = modelMapper.map(accountDto, Account.class);
            account.setId(null);
            account.setAccountType("ROLE_ADMIN");
            String password = generateRandomPassword(12);
            account.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            account.setUsername(GeneateUsername(accountDto.getEmail()));

            Level level = levelRepository.findById(1).orElseThrow(() -> new RuntimeException("Level not found"));

            account.setLevel(level);
            SendConfirmationEmail(accountDto.getEmail(), String.format("Username: %s \n Password: %s", account.getUsername(), password));
            accountRepository.save(account);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean RegisterAccount(RegisterUser accountDto) {
        try {
            Account account = modelMapper.map(accountDto, Account.class);
            account.setId(null);
            account.setPassword(BCrypt.hashpw(account.getPassword(), BCrypt.gensalt()));
            account.setUsername(GeneateUsername(accountDto.getEmail()));
            Level level = levelRepository.findById(1).orElseThrow(() -> new RuntimeException("Level not found"));
            SendConfirmationEmail(accountDto.getEmail(), String.format("Username: %s \n Password: %s", account.getUsername(), accountDto.getPassword()));
            account.setLevel(level);
            accountRepository.save(account);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean emailExists(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public boolean existPhone(String phone) {
        return accountRepository.existsByPhone(phone);
    }

    @Override
    public boolean existFullName(String fullName) {
        return accountRepository.existsByEmail(fullName);
    }

    @Override
    public List<AdminAccountList> getAdmin() {
        return accountRepository.getAdmin();
    }

    private String GenerateOTP() {
        Random random = new Random();
        int otp = 1000 + random.nextInt(1000);
        return String.valueOf(otp);
    }

    private void SendOTPEmail(String Email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(Email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp + "\n\nThis code is valid for 10 minutes.");
        mailSender.send(message);
    }

    @Override
    public boolean ForgetAccount(String email) {
        try {
            Optional<Account> optionalAccount = accountRepository.findByEmail(email);
            if (optionalAccount.isEmpty()) {
                return false;
            }
            Account account = optionalAccount.get();
            String otp = GenerateOTP();
            Instant CurrentInstant = Instant.now();
            account.setOtp(otp);
            account.setCreatedOTP(CurrentInstant);
            accountRepository.save(account);
            SendOTPEmail(email, otp);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean CheckOTP(String email, String otp) {
        try {
            Account account = accountRepository.checkOTP(email, otp);
            if (account == null) {
                return false;
            }
            Instant otpCreatedTime = account.getCreatedOTP();
            Instant currentTime = Instant.now();
            Duration duration = Duration.between(otpCreatedTime, currentTime);
            if (duration.toMillis() > 120000) {
                return false;
            }
            account.setOtp(null);
            account.setCreatedOTP(null);
            accountRepository.save(account);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean ChangePassword(String email, String password) {
        try {
            Optional<Account> optionalAccount = accountRepository.findByEmail(email);
            if (optionalAccount.isEmpty()) {
                return false;
            }
            Account account = optionalAccount.get();
            account.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            accountRepository.save(account);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
