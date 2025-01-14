package com.vtnq.web.Controllers;

import com.vtnq.web.DTOs.Account.RegisterUser;
import com.vtnq.web.Service.AuthService;
import com.vtnq.web.Service.CountryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            RegisterUser registerOwner=new RegisterUser();
            registerOwner.setAccountType("ROLE_OWNER");
            model.put("RegisterOwner",registerOwner);
            model.put("country",countryService.findAll());
            return "User/Register/RegisterUser";
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @PostMapping("registerOwner")
    public String registerOwner(@ModelAttribute("RegisterOwner")@Valid RegisterUser registerOwner, BindingResult bindingResult, RedirectAttributes redirectAttributes, ModelMap model) {
        try {
            if(bindingResult.hasErrors()) {
                StringBuilder errorMessages = new StringBuilder("Validation errors:\n");
                bindingResult.getFieldErrors().forEach(error ->
                        errorMessages.append(String.format(" %s - %s\n", error.getField(), error.getDefaultMessage()))
                );
                redirectAttributes.addFlashAttribute("message", errorMessages.toString());

                redirectAttributes.addFlashAttribute("messageType", "error");
                return "redirect:/register";
            }
            if(authService.RegisterAccount(registerOwner)){
                return "redirect:/register";
            }else{
                return "redirect:/register";
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @PostMapping("registerUser")
    public String registerUser(@ModelAttribute("RegisterUser") @Valid RegisterUser registerUser, BindingResult bindingResult, RedirectAttributes redirectAttributes, ModelMap model) {
        try {
            if(bindingResult.hasErrors()) {
                StringBuilder errorMessages = new StringBuilder("Validation errors:\n");
                bindingResult.getFieldErrors().forEach(error ->
                        errorMessages.append(String.format(" %s - %s\n", error.getField(), error.getDefaultMessage()))
                );
                redirectAttributes.addFlashAttribute("message", errorMessages.toString());

                redirectAttributes.addFlashAttribute("messageType", "error");
                return "redirect:/register";
            }
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
