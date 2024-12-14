package com.vtnq.web.Controllers;

import com.vtnq.web.DTOs.Account.RegisterUser;
import com.vtnq.web.Service.AuthService;
import com.vtnq.web.Service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"","/"})
public class RegisterController {
    @Autowired
    private CountryService countryService;
    @Autowired
    private AuthService authService;
    @GetMapping("register")

    public String register(ModelMap model) {
        try {
            RegisterUser registerUser = new RegisterUser();
            registerUser.setAccountType("ROLE_USER");
            model.put("RegisterUser",registerUser);
            model.put("country",countryService.findAll());
            return "User/Register/RegisterUser";
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @PostMapping("registerUser")
    public String registerUser(@ModelAttribute("RegisterUser")RegisterUser registerUser) {
        try {
            if(authService.RegisterAccount(registerUser)){
                return "redirect:/register";
            }else{
                return "redirect:/register";
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
