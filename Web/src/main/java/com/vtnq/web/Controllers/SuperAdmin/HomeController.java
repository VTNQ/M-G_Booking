package com.vtnq.web.Controllers.SuperAdmin;

import com.vtnq.web.Entities.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller("superAdminHomeController")
@RequestMapping({"/SuperAdmin"})
public class HomeController {

    @GetMapping("Home")
    public String Home(HttpServletRequest request){
        Account account = (Account) request.getSession().getAttribute("currentAccount");
        if(account==null || !"ROLE_SUPERADMIN".equals(account.getAccountType())){
            return "redirect:/LoginAdmin";
        }
        return "SuperAdmin/Home/index";
    }

}
