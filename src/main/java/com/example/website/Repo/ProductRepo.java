package com.example.website.Repo;

import com.example.website.model.Photo;
import com.example.website.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product,Long> {

    @Query("SELECT p FROM Product p where p.productCode =:productCode")
    public Optional<Product> getProductByProductCode(String productCode);

    @Query("SELECT p.img from Product p where p.productId =:productId")
    public List<Photo> getImgByProductId(Long productId);

    @Query("SELECT p FROM Product p where p.productName like CONCAT('%',:name,'%')" )
    public List<Product> getProductByProductName(String name);





}
