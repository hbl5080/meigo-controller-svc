package com.example.website.model.Product;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Arrays;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
public class Photo {



    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "photoIDGenerator")
    @SequenceGenerator(name = "photoIDGenerator", sequenceName = "photoSeq",initialValue = 1,allocationSize = 1)
    private Long photoId;

    private String imageName;
    private String imageType;

    @Lob
    @Basic(fetch = LAZY)
    @Column(name = "imageBytes")
    private byte[] imageBytes;

    @JsonBackReference
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;


    //Getters and Setters
    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
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
                ", imageBytes=" + Arrays.toString(imageBytes) +
                '}';
    }


}
