package com.locationapi.repository;

import com.locationapi.entity.Photo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface PhotoRepository extends CrudRepository<Photo, Integer> {

    @Query(value = "SELECT * FROM picture", nativeQuery = true)
    Collection< Photo > getPhotos();
}

