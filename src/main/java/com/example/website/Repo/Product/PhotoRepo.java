package com.example.website.Repo.Product;

import com.example.website.model.Product.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PhotoRepo extends JpaRepository<Photo,Long> {

    @Query("SELECT p from Photo p where p.photoId=?1")
    public Optional<Photo> getPhotoById(Long photoId);
}
