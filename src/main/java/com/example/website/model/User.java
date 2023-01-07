package com.example.website.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "userIDGenerator")
    @SequenceGenerator(name = "userIDGenerator", sequenceName = "userSeq",initialValue = 0,allocationSize = 1)
    private Integer userID;
    private String firstName;



    private String lastName;
    private String userName;
    private String password;
    @Email
    private String email;
    @Size(min = 10,max = 10)
    private String phone;
    @Valid
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "Address_id")
    private List<Address> addressList;

    public User() {
    }

    public User(Integer userID, String firstName, String lastName, String userName, String password, String email, String phone) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public User(String firstName, String lastName, String userName, String password, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public User(String firstName, String lastName, String userName, String password, String email, String phone, List<com.example.website.model.Address> address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        addressList = address;
    }

    public void addAddress(Address address){
        addressList.add(address);
    }

    public void removeAddress(Address address){
        addressList.remove(address);
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<com.example.website.model.Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<com.example.website.model.Address> addressList) {
        this.addressList = addressList;
    }
}
