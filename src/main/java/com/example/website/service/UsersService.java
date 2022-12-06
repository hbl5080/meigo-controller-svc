package com.example.website.service;


import com.example.website.Repo.UsersRepo;
import com.example.website.exceptionHandler.InvalidInputException;
import com.example.website.exceptionHandler.InfoExistedException;
import com.example.website.exceptionHandler.InfoNotFoundException;
import com.example.website.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    UsersRepo usersRepo;


    public List<Users> getAllUsers(){
        return usersRepo.findAll();
    }

    public Users getByUserID(int id){
        Optional<Users> user =usersRepo.findById(id);
        if (!user.isPresent()){
            throw  new InfoNotFoundException("User Not Found");
        }
        return user.get();
    }

    public void deleteAll(){
        usersRepo.deleteAll();
    }

    public Users deleteById(int id){
        Optional<Users> user = usersRepo.findById(id);
        if(!user.isPresent()){
            throw new InfoNotFoundException("User Not Found");
        }
        usersRepo.deleteById(id);
        return user.get();
    }

    public Users newUser(Users savedUser){
        Users user = usersRepo.findByUserName(savedUser.getUserName());
        if (user!=null){
            throw new InfoExistedException("User existed");
        }
        usersRepo.save(savedUser);
        return savedUser;
    }


    public Users updateUser(Users savedUser){
        if (savedUser==null){
            throw new InvalidInputException("Invalid Input");
        }

        Users user = usersRepo.findByUserName(savedUser.getUserName());
        if (user==null){
            throw new InfoNotFoundException("User Not Found");
        }
        user.setUserID(savedUser.getUserID());
        user.setFirstName(savedUser.getFirstName());
        user.setLastName(savedUser.getLastName());
        user.setUserName(savedUser.getUserName());
        user.setEmail(savedUser.getEmail());
        user.setPhone(savedUser.getPhone());
        user.setPassword(savedUser.getPassword());
        usersRepo.save(user);
        return savedUser;
    }




}
