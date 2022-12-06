package com.example.website.Repo;

import com.example.website.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepo extends JpaRepository<Photo,Integer> {
}
