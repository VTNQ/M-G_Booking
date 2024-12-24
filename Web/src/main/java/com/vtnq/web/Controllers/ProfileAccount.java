package com.vtnq.web.Controllers;

import ch.qos.logback.core.model.Model;
import com.vtnq.web.Entities.Account;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"","/"})
public class ProfileAccount {
    @RequestMapping("Profile")
    public String profile(ModelMap model, HttpServletRequest request) {
        try {
            Account currentAccount = (Account) request.getSession().getAttribute("currentAccount");
        model.put("")
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
