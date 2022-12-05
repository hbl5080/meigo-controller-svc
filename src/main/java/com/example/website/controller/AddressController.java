package com.example.website.controller;

import com.example.website.exceptionHandler.InfoNotFoundException;
import com.example.website.model.Address;
import com.example.website.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping("/getalladdress")
    public List<Address> getAllAddresses(){
        return addressService.getAllAddresses();
    }

    @GetMapping("/getbyuserid")
    public ResponseEntity<Object> getByUserId(Integer id) {
        try {
            List<Address> addresses = addressService.getByUserId(id);
            return new ResponseEntity<>(addresses, HttpStatus.OK);
        } catch (InfoNotFoundException e) {
            return new ResponseEntity<>("User Id Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/newaddress")
    public ResponseEntity<Object> newAddress(Address newAddress){
        try{
            addressService.newAddress(newAddress);
            return new ResponseEntity<>(newAddress,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Something Wrong Happened",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("updateaddress")
    public ResponseEntity<Object> updateAddress(int addressId, Address newAddress){
        try{
            Address address =addressService.updateAddress(addressId,newAddress);
            return new ResponseEntity<>(address,HttpStatus.OK);
        }
        catch (InfoNotFoundException e){
            return new ResponseEntity<>("Address ID Not Found",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/deletebyid")
    public ResponseEntity<Object> deleteById(int id){
        try{
            Address address =addressService.deleteByAddressId(id);
            return new ResponseEntity<>(address,HttpStatus.OK);
        }
        catch(InfoNotFoundException e){
            return new ResponseEntity<>("Address Not Found",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/deletebyuserid")
    public ResponseEntity<Object> deleteByUserId(int id){
        try{
            List<Address> addressList= addressService.deleteByUserId(id);
            return new ResponseEntity<>(addressList,HttpStatus.OK);
        }
        catch(InfoNotFoundException e){
            return new ResponseEntity<>("User Not Found",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/deleteall")
    public ResponseEntity<Object> deleteAll(){
        addressService.deleteAll();
        return  new ResponseEntity<>("All deleted",HttpStatus.OK);
    }

}
