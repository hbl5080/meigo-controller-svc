package com.example.website.controller;

import com.example.website.exceptionHandler.InvalidInputException;
import com.example.website.exceptionHandler.InfoExistedException;
import com.example.website.exceptionHandler.InfoNotFoundException;
import com.example.website.model.ErrorObject;
import com.example.website.model.User;
import com.example.website.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {


    @Autowired
    UsersService usersService;

    @GetMapping("/getallusers")
    public List<User> getAllUsers(){
            return usersService.getAllUsers();
    }

    @GetMapping("/getbyuserid")
    public ResponseEntity<Object> getByUserID(Integer id){
        try{
            return new ResponseEntity<>(usersService.getByUserID(id),HttpStatus.OK);
        }
        catch(InfoNotFoundException e){
            return new ResponseEntity<>("User Not Founded",HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/newuser")
    public ResponseEntity<String> newUser(User savedUser){
        try{
            usersService.newUser(savedUser);
            return new ResponseEntity<String>("User Added",HttpStatus.OK);
        }
        catch(InfoExistedException e){
            return new ResponseEntity<String>("User Existed",HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/updateuser")
    public ResponseEntity<String> updateUser(@RequestBody User savedUser){
        try{
            usersService.updateUser(savedUser);
            return new ResponseEntity<String>("User updated",HttpStatus.OK);
        }
        catch(InvalidInputException invalidInputException){
            return new ResponseEntity<String>("Invalid Input",HttpStatus.BAD_REQUEST);
        }
        catch(InfoNotFoundException notFoundException){
            return new ResponseEntity<String>("User Not Found",HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/deleteall")
    public ResponseEntity<String> deleteall(){
        usersService.deleteAll();
        return new ResponseEntity<String>("Deleted",HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleException(InfoNotFoundException e){
        ErrorObject errorObject = new ErrorObject(HttpStatus.NOT_FOUND.value(),
                e.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.NOT_FOUND);
    }


}
