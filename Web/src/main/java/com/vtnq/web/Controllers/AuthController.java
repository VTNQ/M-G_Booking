package com.vtnq.web.Controllers;

import com.vtnq.web.DTOs.LoginDTO;
import com.vtnq.web.Service.AuthService;
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
    public String ForgotPassword(ModelMap model) {
        return "User/Forgot/ForgotPassword";
    }
    @GetMapping("LoginAdmin")
    public String LoginAdmin(ModelMap model) {
        model.put("login", new LoginDTO());
        return "SuperAdmin/Login/Login";

    }
    @GetMapping("Login")
    public String Login(ModelMap model) {
    model.put("login", new LoginDTO());
    return "User/login/login";
    }

}
