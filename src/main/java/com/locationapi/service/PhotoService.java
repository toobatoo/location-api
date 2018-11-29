package com.locationapi.service;

import com.locationapi.entity.Photo;
import com.locationapi.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    public List<Photo> getPhotos(){

        List<Photo> l = (List<Photo>) this.photoRepository.getPhotos();
        return l;
    }
}
