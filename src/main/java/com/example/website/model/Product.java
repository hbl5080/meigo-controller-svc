package com.example.website.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
public class Product {

    @Id
    private int productID;
    @Column(unique = true,nullable = false)
    private String productCode;
    private String productName;
    private String productDescription;
    private int productNum;
    private String type;
    private int intakePrice;
    private int outtakePrice;
    @OneToMany
    private List<Photo> img;

    public Product() {
    }

    public int getProductID() {
        return productID;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getIntakePrice() {
        return intakePrice;
    }

    public void setIntakePrice(int intakePrice) {
        this.intakePrice = intakePrice;
    }

    public int getOuttakePrice() {
        return outtakePrice;
    }

    public void setOuttakePrice(int outtakePrice) {
        this.outtakePrice = outtakePrice;
    }
    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImg(List<Photo> img) {
        this.img = img;
    }
}
