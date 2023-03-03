package com.example.website.Repo;


import com.example.website.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<User,Long> {

    Optional<User> findByUserName(String userName);

}
