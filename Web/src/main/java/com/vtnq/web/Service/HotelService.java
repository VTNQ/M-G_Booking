package com.vtnq.web.Service;

import com.vtnq.web.DTOs.Hotel.*;
import com.vtnq.web.Entities.Hotel;
import com.vtnq.web.Entities.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface HotelService {
public boolean addHotel(HotelDto hotel);
public List<HotelListDto>FindHotelsByOwner(int id);
public HotelUpdateDTO findHotels(int id);
public List<ImageHotelListDTO>findImage(int id);
public boolean UpdateHotel(HotelUpdateDTO hotel, MultipartFile file);
public boolean deleteImageHotel(int id);
public boolean updateMultipleImages(int id,List<MultipartFile> files);
public List<HotelSearchDTO>SearchHotels(int id,int quantityRoom);
public ShowDetailHotel FindDetailHotel(int id);
public List<Picture>FindImageInDetailHotel(int id);
}
