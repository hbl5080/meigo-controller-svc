package com.example.website.Repo;


import com.example.website.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsersRepo extends JpaRepository<User,Integer> {

    User findByUserName(String userName);

}
