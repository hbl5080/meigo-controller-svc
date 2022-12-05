package com.example.website.Repo;

import com.example.website.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepo extends JpaRepository<Address,Integer> {

    @Query("SELECT a FROM Address a WHERE a.userId=:id")
    public List<Address> getByUserId(int id);

}


