package com.example.website.model.User;


import com.example.website.model.User.Address.Address;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "userIDGenerator")
    @SequenceGenerator(name = "userIDGenerator", sequenceName = "userSeq",initialValue = 0,allocationSize = 1)
    private Long userID;

    @Column(name = "firstName")
    private String firstName;



    @Column(name = "lastName")
    private String lastName;

    @Column(name = "userName",unique = true,nullable = false)
    private String userName;

    @NotBlank
    @Column(name = "password",nullable = false)
    private String password;
    @Email
    @Column(name = "email")
    private String email;
    @Size(min = 10,max = 10)
    @Column(name = "phone")
    private String phone;
    //@Valid
    //@JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "addressList")
    private List<Address> addressList;

    public User() {
    }

    public User(String firstName, String lastName, String userName, String password, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public User(String firstName, String lastName, String userName, String password, String email, String phone, List<Address> address) {
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

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
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

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }


}
