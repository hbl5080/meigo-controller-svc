package com.example.website.model;


import lombok.Data;

import javax.persistence.*;
import java.util.Arrays;

@Data
@Entity
public class Photo {



    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "photoIDGenerator")
    @SequenceGenerator(name = "photoIDGenerator", sequenceName = "photoSeq",initialValue = 1,allocationSize = 1)
    private int photoId;
    private String description;
    private byte[] imageBytes;

    private Integer productId;
    //Getters and Setters
    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "photoId=" + photoId +
                ", description='" + description + '\'' +
                ", imageBytes=" + Arrays.toString(imageBytes) +
                ", productId=" + productId +
                '}';
    }


}
