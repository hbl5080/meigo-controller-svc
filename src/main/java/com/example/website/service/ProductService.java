package com.example.website.service;

import com.example.website.Repo.ProductRepo;
import com.example.website.exceptionHandler.InfoNotFoundException;
import com.example.website.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    public List<Product> getAllProudcts(){
        return productRepo.findAll();
    }

    public Product getByProductId(int id){
        Optional<Product> product = productRepo.findById(id);
        if(!product.isPresent()){
            throw new InfoNotFoundException("Product Not Found");
        }

        return product.get();
    }

//    public Product getByProductCode(int code){
//
//    }
    
}
