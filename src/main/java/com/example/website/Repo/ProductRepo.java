package com.example.website.Repo;

import com.example.website.model.Photo;
import com.example.website.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,Integer> {

    @Query("SELECT p FROM Product p where p.productCode =:productCode")
    public Product getProductByProductCode(String productCode);

    @Query("SELECT p.img from Product p where p.productID =:productId")
    public List<Photo> getImgByProductCode(int productId);

    @Query("SELECT p FROM Product p where p.productName like %:name%")
    public List<Product> getProductByProductName(String name);
}
