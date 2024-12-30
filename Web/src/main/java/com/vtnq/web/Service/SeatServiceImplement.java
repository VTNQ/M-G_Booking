package com.vtnq.web.Service;

import com.vtnq.web.Controllers.Seat.SeatDTO;
import com.vtnq.web.Entities.Seat;
import com.vtnq.web.Repositories.SeatRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
@Service
public class SeatServiceImplement implements SeatService {
  @Autowired
  private SeatRepository seatRepository;
  @Autowired
  private ModelMapper modelMapper;

  private WebSocketSession session;
    @Override
    public List<SeatDTO> FindSeatByFlight(int idFlight) {
        try {
            List<SeatDTO> seats = modelMapper.map(seatRepository.FindSeatByFlight(idFlight), new TypeToken<List<SeatDTO>>(){}.getType());


            if (session != null && session.isOpen()) {
                String seatUpdateMessage = "Updated seats: " + seats;
                session.sendMessage(new TextMessage(seatUpdateMessage));
            }
            return seats;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

}
