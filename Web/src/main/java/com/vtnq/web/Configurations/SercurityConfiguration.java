package com.vtnq.web.Configurations;

import com.vtnq.web.Entities.Account;
import com.vtnq.web.Service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Configuration
@EnableWebSecurity
public class SercurityConfiguration {
    @Autowired
    private AuthService authService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {return httpSecurity
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/LoginAdmin","/registerUser","/register","/Login","/css/**","/js/**","/user/**","/SuperAdmin/assets/**",
                                    "/ForgotPassword","/images/flight/**","/images/hotels/**","/images/**","/Home","/SearchFlight","/Admin/SignatureContract/{id}",
                                    "/DetailHotel/{id}","/InformationCustomer/{id}","/rating","/Payment","/Success","/payFlight/**","/RoundTrip/{id}","/SearchHotel/{id}","/RoundTripHotel/{id}","/HistoryOrder").permitAll()
                            .requestMatchers("SuperAdmin/Home","/SuperAdmin/Country/add","/SuperAdmin/Country"
                            ,"/SuperAdmin/Country/update","/SuperAdmin/Country/delete/{id}","/SuperAdmin/AccountAdmin/add"
                            ,"/SuperAdmin/Airline/add","/SuperAdmin/Airline","/SuperAdmin/Airline/edit/{id}",
                                    "/SuperAdmin/Airline/UpdateAirline").hasAnyRole("SUPERADMIN")
                            .requestMatchers("/Admin/Home","/Admin/City/add","/Admin/City","/Admin/City/edit/{id}",
                                    "Admin/City/UpdateCity","/Admin/City/delete/{id}","Admin/District/{id}",
                                    "Admin/District/add","/Admin/District/edit/{id}","/Admin/District/update","/Admin/District/delete/{id}",
                                    "Admin/AirPort/add","Admin/AirPort","/Admin/AirPort/edit/{id}","/Admin/Flight/add","/Admin/Flight/edit/{id}","/Admin/Flight/UpdateFlight","/Admin/Flight/addSeat","/Admin/Contract", "/Admin/Booking","/Admin/Booking/detail/{id}").hasAnyRole("ADMIN")
                            .requestMatchers("/Owner","/Owner/Hotel/add","/Owner/Hotel","/Owner/Hotel/edit/{id}","/Owner/Hotel/update","/Owner/Hotel/Detail/{id}"
                            ,"/Owner/service/add","/Owner/service/{id}","/Owner/service/edit/{id}","/Owner/service/update","/Owner/Room/{id}","/Owner/Room/add",
                                    "/Owner/Room/edit/{id}","/Owner/Room/update","/Owner/Room/delete/{id}","/Owner/Amenities/{id}","/Owner/Amenities/add","/Owner/Amenities/edit/{id}",
                                    "/Owner/Amenities/update","/Owner/Amenities/delete/{id}","/Owner/Room/addType").hasAnyRole("OWNER")
                            .requestMatchers("/Profile","/InformationFly/{id}","/payHotelFlight","/payHotelFlight/**","/InformationFlightHotel/{id}"
                                   ).hasAnyRole("USER")
                            .anyRequest().authenticated();
                })

                .formLogin(form -> form
                        .loginPage("/LoginAdmin")
                        .loginPage("/Login")
                        .loginProcessingUrl("/account/process-login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .failureUrl("/account/login?error")
                        .successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
                                Map<String, String> urls = new HashMap<>();
                                urls.put("ROLE_ADMIN", "/Admin/Home");
                                urls.put("ROLE_SUPERADMIN", "/SuperAdmin/Home");
                                urls.put("ROLE_USER", "/Home");
                                urls.put("ROLE_OWNER", "/Owner");

                                String redirectUrl = "/Error";
                                String email = authentication.getName();
                                Account account = authService.GetAccountByEmail(email);

                                // Lưu tài khoản hiện tại vào session
                                request.getSession().setAttribute("currentAccount", account);

                                // Xác định URL chuyển hướng dựa trên vai trò
                                for (GrantedAuthority authority : authorities) {
                                    String role = authority.getAuthority();
                                    if (urls.containsKey(role)) {
                                        redirectUrl = urls.get(role);
                                        break;
                                    }
                                }

                                response.sendRedirect(redirectUrl);
                            }
                        })
                )
            .logout(logout -> logout
                    .logoutUrl("/account/logout")
                    .addLogoutHandler((request, response, authentication) -> {
                        // Get the current account from the session
                        HttpSession session = request.getSession(false); // Get the current session, if any
                        if (session != null) {
                            Account currentAccount = (Account) session.getAttribute("currentAccount");
                            if (currentAccount != null) {
                                // Custom logic with currentAccount
                                try {
                                    if ("ROLE_ADMIN".equals(currentAccount.getAccountType()) || "ROLE_SUPERADMIN".equals(currentAccount.getAccountType())) {
                                        // Redirect to /LoginAdmin
                                        response.sendRedirect("/LoginAdmin");
                                        session.invalidate();
                                        return;
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace(); // Log the exception
                                }
                            }
                            // Invalidate session after processing
                            session.invalidate();
                        }
                    })
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
            )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/account/access-denied")
                )
                .build();
    }



    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    public void globalConfig(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(authService);
    }
}
