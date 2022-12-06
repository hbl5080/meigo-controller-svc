package com.example.website.service;

import com.example.website.Repo.PhotoRepo;
import com.example.website.Repo.ProductRepo;
import com.example.website.exceptionHandler.InfoExistedException;
import com.example.website.exceptionHandler.InfoNotFoundException;
import com.example.website.exceptionHandler.InvalidInputException;
import com.example.website.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    PhotoRepo photoRepo;

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

    public Product getByProductCode(String code){
        Product product = productRepo.getProductByProductCode(code);
        if(product == null){
            throw new InfoNotFoundException("Product Not Found");
        }

        return product;
    }

    public Product newProduct(Product newProduct){
        if (newProduct.getProductCode()==null||newProduct.getProductNum()<0||newProduct.getIntakePrice()<0 ){
            throw new InvalidInputException("Invalid Input");
        }

        Product product = productRepo.getProductByProductCode(newProduct.getProductCode());
        if (product != null){
            throw new InfoExistedException("Product Existed");
        }
        productRepo.save(newProduct);
        return newProduct;
    }

    public Product updateProduct(String productCode, Product updateProduct){
        if (updateProduct.getProductCode()==null||updateProduct.getProductNum()<0||updateProduct.getIntakePrice()<0 ){
            throw new InvalidInputException("Invalid Input");
        }

        Product product = productRepo.getProductByProductCode(productCode);
        if(product ==null){
            throw new InfoNotFoundException("Product Not Found");
        }

        updateProduct.setProductID(product.getProductID());
        productRepo.save(updateProduct);
        return updateProduct;
    }
    
}
