package com.vtnq.web.APIs;

import com.vtnq.web.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("api/account")
public class AccountAPI {
    @Autowired
    private AuthService authService;
    @PostMapping("forgetPassword")
    public ResponseEntity<Object> forgetPassword(@RequestBody String email) {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            var result=authService.ForgetAccount(email);
            if(result==true){
                response.put("status",200);
                response.put("message","Success");
                return ResponseEntity.ok(response);
            }else {
                response.put("status",400);
                response.put("message","Error");
                return ResponseEntity.badRequest().body(response);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
