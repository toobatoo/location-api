package com.locationapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.locationapi.entity.Photo;
import com.locationapi.service.PhotoService;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/api/photo")
@RestController
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping("/get-photos")
    List<Photo> getPhotos() {
    	
    	List<Photo> l = this.photoService.getPhotos();
        return l;
    }
}
 
