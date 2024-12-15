package com.vtnq.web.Configurations;

import com.vtnq.web.Entities.Account;
import com.vtnq.web.Service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/LoginAdmin","/registerUser","/register","/Login","/css/**","/js/**","/user/**","/SuperAdmin/assets/**",
                                    "/ForgotPassword").permitAll()
                            .requestMatchers("SuperAdmin/Home","/SuperAdmin/Country/add","/SuperAdmin/Country"
                            ,"/SuperAdmin/Country/update","/SuperAdmin/Country/delete/{id}","/SuperAdmin/AccountAdmin/add"
                            ,"/SuperAdmin/Airline/add","/SuperAdmin/Airline","/SuperAdmin/Airline/edit/{id}",
                                    "/SuperAdmin/Airline/UpdateAirline").hasAnyRole("SUPERADMIN")
                            .requestMatchers("/Admin/Home","/Admin/City/add","/Admin/City","/Admin/City/edit/{id}",
                                    "Admin/City/UpdateCity","/Admin/City/delete/{id}","Admin/District/{id}",
                                    "Admin/District/add","/Admin/District/edit/{id}","/Admin/District/update","/Admin/District/delete/{id}",
                                    "Admin/AirPort/add","Admin/AirPort","/Admin/AirPort/edit/{id}","/Admin/Flight/add","/Admin/Flight/edit/{id}",
                                    "Admin/Flight/AddDetailFlight","/Admin/Flight/UpdateFlight").hasAnyRole("ADMIN")
                            .requestMatchers("/Owner","/Owner/Hotel/add").hasAnyRole("OWNER")
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
                                urls.put("ROLE_USER", "/Doctor/index");
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
                        .logoutSuccessUrl("/Login/index")
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
