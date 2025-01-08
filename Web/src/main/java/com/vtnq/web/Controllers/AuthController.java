package com.vtnq.web.Controllers;

import com.vtnq.web.DTOs.LoginDTO;
import com.vtnq.web.Entities.Account;
import com.vtnq.web.Service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"","/"})
public class AuthController {
    @Autowired
    private AuthService authService;
    @GetMapping("ForgotPassword")
    public String ForgotPassword(ModelMap model, HttpServletRequest request) {
        Account account=(Account)request.getSession().getAttribute("currentAccount");
        if(account==null){
            return "User/Forgot/ForgotPassword";
        }else{
            return "User/Forgot/ForgotPasswordAccount";
        }

    }
    @GetMapping("LoginAdmin")
    public String LoginAdmin(ModelMap model) {
        model.put("login", new LoginDTO());
        return "SuperAdmin/Login/Login";

    }
    @GetMapping("Login")
    public String Login(ModelMap model, HttpServletRequest request) {
        Account account=(Account)request.getSession().getAttribute("currentAccount");
        if(account==null) {
            model.put("login", new LoginDTO());
            return "User/login/login";
        }else{
            model.put("login", new LoginDTO());
            return "User/login/loginAccount";
        }

    }

}
