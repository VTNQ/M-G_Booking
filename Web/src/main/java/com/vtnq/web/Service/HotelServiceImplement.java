package com.vtnq.web.Service;

import com.vtnq.web.DTOs.Hotel.HotelDto;
import com.vtnq.web.Entities.Account;
import com.vtnq.web.Entities.ContractOwner;
import com.vtnq.web.Entities.Hotel;
import com.vtnq.web.Entities.HotelsOwner;
import com.vtnq.web.Repositories.AccountRepository;
import com.vtnq.web.Repositories.ContractOwnerRepository;
import com.vtnq.web.Repositories.HotelRepository;
import com.vtnq.web.Repositories.HotelsOwnerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelServiceImplement implements HotelService{
    @Autowired
    private ModelMapper model;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private HotelsOwnerRepository hotelsOwnerRepository;
    @Autowired
    private ContractOwnerRepository contractOwnerRepository;
    @Override
    public boolean addHotel(HotelDto hotel) {
        try {
            Account account=accountRepository.findById(hotel.getOwnerId())
                    .orElseThrow(()-> new RuntimeException("Account not found"));
            Hotel hotelEntity = model.map(hotel, Hotel.class);
            Hotel insertHotel=hotelRepository.save(hotelEntity);
            HotelsOwner hotelsOwner=new HotelsOwner();
            hotelsOwner.setHotel(insertHotel);
            hotelsOwner.setOwner(account);
            hotelsOwnerRepository.save(hotelsOwner);
            ContractOwner contractOwner=new ContractOwner();
            contractOwner.setOwnerId(hotel.getOwnerId());
            contractOwner.setHotelId(insertHotel.getId());
            contractOwner.setCommissionRate(5.0);
            contractOwner.setStatus(false);
            contractOwnerRepository.save(contractOwner);
            return true;

        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
