package com.vtnq.web.Repositories;

import com.vtnq.web.Entities.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PictureRepository extends JpaRepository<Picture, Integer> {
    @Query("SELECT  a from Picture a where a.airlineId = :id")
    public Picture findByImageId(@Param("id") Integer id);
}