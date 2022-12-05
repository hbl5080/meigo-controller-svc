package com.example.website.Repo;


import com.example.website.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsersRepo extends JpaRepository<Users,Integer> {

    Users findByUserName(String userName);

}
