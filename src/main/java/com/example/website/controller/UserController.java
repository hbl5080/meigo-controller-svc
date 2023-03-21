package com.example.website.controller;

import com.example.website.exceptionHandler.InvalidInputException;
import com.example.website.exceptionHandler.InfoExistedException;
import com.example.website.exceptionHandler.InfoNotFoundException;
import com.example.website.model.User.Address.Address;
import com.example.website.model.User.Address.addAddressRequest;
import com.example.website.model.User.LoginRequest;
import com.example.website.model.User.User;
import com.example.website.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin
@Slf4j
public class UserController {


    @Autowired
    UsersService usersService;

    @GetMapping("/getallusers")
    public List<User> getAllUsers(){
            return usersService.getAllUsers();
    }

    @GetMapping("/getbyuserid")
    public ResponseEntity<Object> getByUserID(@RequestParam Long id){
        try{
            return new ResponseEntity<>(usersService.getByUserID(id),HttpStatus.OK);
        }
        catch(InfoNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/newuser")
    public ResponseEntity<Object> newUser(@RequestBody User savedUser){
        try{
            usersService.regNewUser(savedUser);
            return new ResponseEntity<>(savedUser,HttpStatus.OK);
        }
        catch(InfoExistedException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/updateuser")
    public ResponseEntity<Object> updateUser(@RequestBody User savedUser)
        throws InvalidInputException,InfoNotFoundException{
        try{
            usersService.updateUser(savedUser);
            return new ResponseEntity<>(savedUser,HttpStatus.OK);
        }
        catch(InvalidInputException invalidInputException){
            return new ResponseEntity<>(invalidInputException.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch(InfoNotFoundException notFoundException){
            return new ResponseEntity<>(notFoundException.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/deleteuserbyid")
    public ResponseEntity<Object> deleteUserById(@RequestParam Long userId)
        throws InfoNotFoundException{
        try{
            usersService.deleteById(userId);
            return new ResponseEntity<>(true,HttpStatus.OK);
        }
        catch (InfoNotFoundException infoNotFoundException){
            return new ResponseEntity<>(infoNotFoundException.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<Object> deleteall(){
        usersService.deleteAll();
        return new ResponseEntity<>(true,HttpStatus.OK);
    }

    @PostMapping("/updateaddress")
    public ResponseEntity<Object> updateAddress(@RequestParam Long addressId, @RequestBody Address updateAddress)
        throws InvalidInputException {
        try{
            Boolean update = usersService.updateAddress(addressId,updateAddress);
            return new ResponseEntity<>(update,HttpStatus.OK);
        }
        catch (InvalidInputException invalidInputException){
            return new ResponseEntity<>(invalidInputException.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addaddress")
    public ResponseEntity<Object> addAddress(@RequestParam Long userId,@RequestBody Address newAddress)
        throws InvalidInputException{
        try {
            Boolean added = usersService.addAddress(userId,newAddress);
            return new ResponseEntity<>(added,HttpStatus.OK);
        }
        catch (InvalidInputException invalidInputException){
            return new ResponseEntity<>(invalidInputException.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteaddress")
    public ResponseEntity<Object> detelteAddress(@RequestBody addAddressRequest addAddressRequest)
        throws InvalidInputException{
        try{
            Boolean deleted = usersService.deleteAddress(addAddressRequest.getUserId(), addAddressRequest.getAddressId());
            return new ResponseEntity<>(deleted,HttpStatus.OK);
        }
        catch (InvalidInputException invalidInputException){
            return new ResponseEntity<>(invalidInputException.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest){
        try{
            User user = usersService.login(loginRequest.getEmail(),loginRequest.getPassword());
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
        catch (InvalidInputException invalidInputException){
            return new ResponseEntity<>(invalidInputException.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/tokenlogin")
    public ResponseEntity<Object> tokenLogin(@RequestParam String token){
        try{
            User user = usersService.tokenLogin(token);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (InvalidInputException invalidInputException){
            return new ResponseEntity<>(invalidInputException.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
