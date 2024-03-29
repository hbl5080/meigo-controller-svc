package com.example.website.model.Product;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;


@Data
@Entity
@Table(name = "Product")

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "productIDGenerator")
    @SequenceGenerator(name = "productIDGenerator", sequenceName = "productSeq",initialValue = 1,allocationSize = 1)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_code",unique = true,nullable = false)
    private String productCode;

    @NotEmpty(message = "Product Name can not be empty")
    private String productName;
    private String productDescription;

    @NotNull
    @Min(value = 0,message = "Product Number Must be Greater or equals to 0")
    private int productNum;

    private String productType;

    private String productScale;

    @Min(value = 0,message = "Intake Price Must be Greater Than 0")
    private int productInputPrice;

    @Min(value = 0,message = "Outtake Price Must be Greater Than 0")
    private int productOutputPrice;

    private String manufacturer;

    private String origin;

    private String defaultImg;

    private Long defaultImgCode;

    @Valid
    @JsonManagedReference
    @OneToMany(mappedBy = "product")
    private List<Photo> img;

    public Product() {
    }

    public Product(String productCode, String productName, String productDescription, int productNum, String productType, String productScale, int productInputPrice, int productOutputPrice, List<Photo> img) {
        this.productCode = productCode;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productNum = productNum;
        this.productType = productType;
        this.productInputPrice = productInputPrice;
        this.productOutputPrice = productOutputPrice;
        this.productScale = productScale;
        this.img = img;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productID){ this.productId = productID;}

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
        return productInputPrice;
    }

    public void setIntakePrice(int intakePrice) {
        this.productInputPrice = intakePrice;
    }

    public int getOuttakePrice() {
        return productOutputPrice;
    }

    public void setOuttakePrice(int outtakePrice) {
        this.productOutputPrice = outtakePrice;
    }
    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }


    public String getType() {
        return productType;
    }

    public void setType(String type) {
        this.productType = type;
    }

    public List<Photo> getImg() {
        return img;
    }

    public void addPhoto(Photo photo){
        img.add(photo);
        photo.setProduct(this);
    }

    public void remove(Photo photo){
        img.remove(photo);
        photo.setProduct(null);
    }
}
