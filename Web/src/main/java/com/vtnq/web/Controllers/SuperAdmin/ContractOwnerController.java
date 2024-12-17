package com.vtnq.web.Controllers.SuperAdmin;

import com.vtnq.web.Service.ContractOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("ContractOwnerSuperAdmin")
@RequestMapping({"/SuperAdmin"})
public class ContractOwnerController {
    @Autowired
    private ContractOwnerService contractOwnerService;
    @GetMapping(value = "SignatureContract/{id}")
    public String SignatureContract(@PathVariable int id) {
        try {
            return "SignContract/SignContract";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @GetMapping("Contract")
    public String Contract(ModelMap model) {
        try {
            model.put("Contract",contractOwnerService.findAll());

            return "/SuperAdmin/Contract/Contract";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
