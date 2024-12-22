package com.vtnq.web.Service;

import com.vtnq.web.Entities.Seat;

import java.util.List;

public interface SeatService {
    public List<Seat>FindSeatByFlight(int idFlight);
}
