package com.example.website.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Arrays;

@Data
@Entity
public class Photo {



    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "photoIDGenerator")
    @SequenceGenerator(name = "photoIDGenerator", sequenceName = "photoSeq",initialValue = 1,allocationSize = 1)
    private Long photoId;
    private String description;
    //@NotBlank(message = "image can not be blank")
    private byte[] imageBytes;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;


    //Getters and Setters
    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Photo)) return false;
        return photoId!=null && photoId.equals(((Photo)o).getPhotoId());
    }

    @Override
    public int hashCode(){
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Photo{" +
                "photoId=" + photoId +
                ", description='" + description + '\'' +
                ", imageBytes=" + Arrays.toString(imageBytes) +
                '}';
    }


}
