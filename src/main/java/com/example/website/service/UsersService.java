package com.example.website.service;


import com.example.website.Repo.AddressRepo;
import com.example.website.Repo.UsersRepo;
import com.example.website.exceptionHandler.InvalidInputException;
import com.example.website.exceptionHandler.InfoExistedException;
import com.example.website.exceptionHandler.InfoNotFoundException;
import com.example.website.model.User.Address.Address;
import com.example.website.model.User.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<User> getAllUsers(){
        return usersRepo.findAll();
    }

    public User getByUserID(Long id){
        Optional<User> user =usersRepo.findById(id);
        if (!user.isPresent()){
            throw  new InfoNotFoundException("User Not Found");
        }

        user.get().setPassword(null);
        return user.get();
    }

    public void deleteAll(){
        usersRepo.deleteAll();
    }

    public boolean deleteById(Long userId){
        Optional<User> user = usersRepo.findById(userId);
        if(!user.isPresent()){
            throw new InfoNotFoundException("User Not Found");
        }
        usersRepo.deleteById(userId);
        return true;
    }

    public User regNewUser(User user){
        Optional<User> tempUser = usersRepo.findByEmail(user.getEmail());
        if (tempUser.isPresent()){
            throw new InfoExistedException("User existed");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("Normal");
        usersRepo.save(user);
        user.setPassword("");
        return user;
    }

    public User newUser(User savedUser){
        Optional<User> user = usersRepo.findByEmail(savedUser.getEmail());
        if (user.isPresent()){
            throw new InfoExistedException("User existed");
        }

        List<Address> addressList = savedUser.getAddressList();
        if (addressList.isEmpty()){
            throw new InvalidInputException("Incorrect Address Information");
        }

        savedUser.setPassword(passwordEncoder.encode(savedUser.getPassword()));
        usersRepo.save(savedUser);
        savedUser.setPassword(null);
        return savedUser;
    }

    public User updateUser(User savedUser){
        if (savedUser==null){
            throw new InvalidInputException("Invalid Input");
        }

        Optional<User> user = usersRepo.findByEmail(savedUser.getEmail());
        if (!user.isPresent()){
            throw new InfoNotFoundException("User Not Found");
        }

        savedUser.setUserID(user.get().getUserID());
        savedUser.setPassword(passwordEncoder.encode(savedUser.getPassword()));
        usersRepo.save(savedUser);
        savedUser.setPassword(null);
        return savedUser;
    }

    public boolean updateAddress(final Long addressId,Address updateAddress){
        Optional<Address> address = addressRepo.findById(addressId);
        if(!address.isPresent()){
            throw new InvalidInputException("Address Not Found");
        }

        updateAddress.setAddressId(addressId);
        addressRepo.save(updateAddress);

        return true;
    }

    public boolean addAddress(final Long userId, Address newAddress){
        Optional<User> user = usersRepo.findById(userId);
        if(!user.isPresent()){
            throw new InvalidInputException("User Not Found");
        }

        user.get().getAddressList().add(newAddress);

        usersRepo.save(user.get());
        return true;
    }

    public boolean deleteAddress(final Long userId,final Long addressId){
        Optional<User> user = usersRepo.findById(userId);

        if (!user.isPresent()){
            throw new InvalidInputException("User Not Found");
        }
        Optional<Address> address = addressRepo.findById(addressId);

        if (!address.isPresent()){
            throw new InvalidInputException("Address Not Found");
        }

        user.get().getAddressList().remove(address.get());
        usersRepo.save(user.get());
        addressRepo.deleteById(addressId);
        return true;
    }

    public User login(final String email, final String password){
        Optional<User> user = usersRepo.findByEmail(email);
        if(!user.isPresent()){
            throw new InvalidInputException("User Not Found");
        }
        if(!user.get().isActive()){
            throw new InvalidInputException("User Not Active");
        }
        if(checkPassword(password, user.get().getPassword())){
            throw new InvalidInputException("Incorrect Password");
        }
        User tempUser = user.get();
        tempUser.setToken(RandomStringUtils.random(20,true,true));
        usersRepo.save(tempUser);
        return tempUser;
    }

    public User tokenLogin(String token){
        Optional<User> user = usersRepo.findByToken(token);
        if(!user.isPresent()){
            throw new InvalidInputException("User Not Found");
        }
        return user.get();
    }

    public boolean checkPassword(final String password,final String userPassword){
        if(!passwordEncoder.matches(password, userPassword)){
            return false;
        }
        return true;
    }


}
