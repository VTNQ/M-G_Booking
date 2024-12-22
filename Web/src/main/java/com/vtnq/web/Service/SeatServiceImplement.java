package com.vtnq.web.Service;

import com.vtnq.web.Entities.Seat;
import com.vtnq.web.Repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SeatServiceImplement implements SeatService {
  @Autowired
  private SeatRepository seatRepository;
    @Override
    public List<Seat> FindSeatByFlight(int idFlight) {
        try {
            return seatRepository.FindSeatByFlight(idFlight);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

}
