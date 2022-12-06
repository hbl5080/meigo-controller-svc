package com.example.website.Repo;

import com.example.website.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepo extends JpaRepository<Product,Integer> {

    @Query("SELECT p FROM Product p where p.productCode =: productCode")
    public Product getProductByProductCode(String productCode);
}
