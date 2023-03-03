package com.example.website.service;

import com.example.website.Repo.AddressRepo;
import com.example.website.exceptionHandler.InfoNotFoundException;
import com.example.website.model.User.Address.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepo addressRepo;

    public List<Address> getAllAddresses(){
        List<Address> addressList = addressRepo.findAll();
        return addressList == null ? addressList:Arrays.asList(new Address());
    }

    public Address getByAddressID(Long id){
        Optional<Address> address =addressRepo.findById(id);
        if (!address.isPresent()){
            throw  new InfoNotFoundException("Address Not Found");
        }
        return address.get();
    }

    public Address newAddress(Address savedAddress){
        addressRepo.save(savedAddress);
        return savedAddress;
    }


    public Address updateAddress(Long addressId,Address savedAddress){
        Optional<Address> address = addressRepo.findById(addressId);
        if(!address.isPresent()){
            throw new InfoNotFoundException("Address ID Not Found");
        }

        address.get().setStreet1(savedAddress.getStreet1());
        address.get().setStreet2(savedAddress.getStreet2());
        address.get().setCity(savedAddress.getCity());
        address.get().setCountry(savedAddress.getCountry());
        address.get().setZipCode(savedAddress.getZipCode());

        addressRepo.save(address.get());
        return address.get();
    }

    public void deleteAll(){
        addressRepo.deleteAll();
    }

    public Address deleteByAddressId(Long addressId){
        Optional<Address> address = addressRepo.findById(addressId);
        if(!address.isPresent()){
            throw new InfoNotFoundException("Address Not Found");
        }
        addressRepo.deleteById(addressId);
        return address.get();
    }

}

