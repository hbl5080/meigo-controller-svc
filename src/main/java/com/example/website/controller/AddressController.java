//package com.example.website.controller;
//
//import com.example.website.exceptionHandler.InfoNotFoundException;
//import com.example.website.model.User.Address.Address;
//import com.example.website.service.AddressService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Slf4j
//@CrossOrigin
//@RestController
//@RequestMapping("/address")
//public class AddressController {
//
//    @Autowired
//    AddressService addressService;
//
//    @GetMapping("/getalladdress")
//    public List<Address> getAllAddresses(){
//        return addressService.getAllAddresses();
//    }
//
//
//
//    @PostMapping("/newaddress")
//    public ResponseEntity<Object> newAddress(@RequestBody Address newAddress){
//        try{
//            addressService.newAddress(newAddress);
//            return new ResponseEntity<>(newAddress,HttpStatus.OK);
//        }
//        catch (Exception e){
//            return new ResponseEntity<>("Something Wrong Happened",HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PostMapping("updateaddress")
//    public ResponseEntity<Object> updateAddress(Long addressId, Address newAddress){
//        try{
//            Address address =addressService.updateAddress(addressId,newAddress);
//            return new ResponseEntity<>(address,HttpStatus.OK);
//        }
//        catch (InfoNotFoundException e){
//            return new ResponseEntity<>("Address ID Not Found",HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PostMapping("/deletebyid")
//    public ResponseEntity<Object> deleteById(Long id){
//        try{
//            Address address =addressService.deleteByAddressId(id);
//            return new ResponseEntity<>(address,HttpStatus.OK);
//        }
//        catch(InfoNotFoundException e){
//            return new ResponseEntity<>("Address Not Found",HttpStatus.NOT_FOUND);
//        }
//    }
//
//
//
//    @PostMapping("/deleteall")
//    public ResponseEntity<Object> deleteAll(){
//        addressService.deleteAll();
//        return  new ResponseEntity<>("All deleted",HttpStatus.OK);
//    }
//
//}
