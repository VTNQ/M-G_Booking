package com.vtnq.web.Controllers;

import com.vtnq.web.Entities.Account;
import com.vtnq.web.Entities.Hotel;
import com.vtnq.web.Entities.Rating;
import com.vtnq.web.Repositories.HotelRepository;
import com.vtnq.web.Service.AmenitiesService;
import com.vtnq.web.Service.HotelService;
import com.vtnq.web.Service.RatingService;
import com.vtnq.web.Service.RoomService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"","/"})
public class DetailHotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private AmenitiesService amenitiesService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private RoomService roomService;
    @GetMapping({"DetailHotel/{id}"})
    public String DetailController(ModelMap modelMap, @PathVariable int id, HttpServletRequest request) {
        try {
            Account currentAccount = (Account) request.getSession().getAttribute("currentAccount");
            modelMap.put("Hotel",hotelService.FindDetailHotel(id));
            modelMap.put("Image",hotelService.FindImageInDetailHotel(id));
            modelMap.put("Amenities",amenitiesService.FindAmenitiesByHotel(id));
            modelMap.put("DetailRoom",roomService.ShowDetailHotel(id));
            modelMap.put("avgRating",ratingService.getAverageRating(id));
            Rating rating=new Rating();
            Hotel hotel=hotelRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Hotel not found"));
            rating.setHotel(hotel);
            if(currentAccount!=null){
                rating.setUserId(currentAccount.getId());
            }

            modelMap.put("Rating",rating);
            return "User/DetailHotel/DetailHotel";
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
