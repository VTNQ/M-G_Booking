package com.vtnq.web.Service;

import com.vtnq.web.DTOs.Flight.DetailFlightDTO;
import com.vtnq.web.Entities.DetailFlight;

import java.util.List;

public interface DetailFlightService {
    public List<DetailFlightDTO>findAll(int id);
    public boolean addDetailFlight(DetailFlightDTO detailFlight);
    public boolean existByType(String type,int idFlight);
    public boolean updateDetailFlight(List<DetailFlightDTO>detailFlight);
}
