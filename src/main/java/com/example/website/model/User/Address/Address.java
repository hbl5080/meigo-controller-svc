package com.example.website.model.User.Address;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class Address{


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "addressIDGenerator")
    @SequenceGenerator(name = "addressIDGenerator", sequenceName = "addressSeq",initialValue = 0,allocationSize = 1)
    @Column(name = "address_id")
    private Long addressId;
    @NotBlank
    @Column(name = "street1")
    private String street1;
    @Column(name = "street2")
    private String street2;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name = "zipCode")
    private String zipCode;

    @Column(name = "default_address")
    private boolean defaultAddress = false;

    //Constructors

    public Address() {
    }

    public Address(String street1, String street2, String city, String country, String zipCode) {
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
    }

    // Getters and Setters
    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }



}

