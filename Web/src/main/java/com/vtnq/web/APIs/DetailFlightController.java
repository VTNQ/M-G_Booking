package com.vtnq.web.APIs;

import com.vtnq.web.DTOs.Flight.DetailFlightDTO;
import com.vtnq.web.Service.DetailFlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/DetailFlight")
public class DetailFlightController {
    @Autowired
    private DetailFlightService detailFlightService;

    @PutMapping("UpdateDetail")
    public ResponseEntity<Object> UpdateDetail(@RequestBody List<DetailFlightDTO> detailFlight) {
        Map<String, Object> response = new LinkedHashMap<>();
        if (detailFlightService.updateDetailFlight(detailFlight)) {
            response.put("status", 200);
            response.put("message", "Update DetailFlight Successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", 400);
            response.put("message", "Update DetailFlight Failed");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
