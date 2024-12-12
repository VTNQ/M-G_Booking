package com.vtnq.web.Repositories;

import com.vtnq.web.Entities.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Integer> {
}