package com.example.website.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.util.List;

@Entity
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "userIDGenerator")
    @SequenceGenerator(name = "userIDGenerator", sequenceName = "userSeq",initialValue = 0,allocationSize = 1)
    private Integer userID;
    private String firstName;



    private String lastName;
    private String userName;
    private String password;
    private String email;
    private String phone;
    @OneToMany
    private List<Address> Address;

    public Users() {
    }

    public Users(Integer userID, String firstName, String lastName, String userName, String password, String email, String phone) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public Users(String firstName,String lastName, String userName, String password, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public Users(String firstName, String lastName, String userName, String password, String email, String phone, List<com.example.website.model.Address> address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        Address = address;
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

    public List<com.example.website.model.Address> getAddress() {
        return Address;
    }

    public void setAddress(List<com.example.website.model.Address> address) {
        Address = address;
    }
}
