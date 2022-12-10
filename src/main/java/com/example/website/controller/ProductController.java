package com.example.website.controller;


import com.example.website.exceptionHandler.InfoExistedException;
import com.example.website.exceptionHandler.InfoNotFoundException;
import com.example.website.exceptionHandler.InvalidInputException;
import com.example.website.model.Photo;
import com.example.website.model.Product;
import com.example.website.model.UpdateProduct;
import com.example.website.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;



    //Test
    @GetMapping("/emptyproduct")
    public Product getEmptyProduct(){
        return productService.newProductForTesting();
    }

    @GetMapping("/emptyphoto")
    public Photo getEmptyPhoto(){
        return productService.newPhotoForTesting();
    }

    //Product
    @GetMapping("/getallproduct")
    public List<Product> getAllProduct(){
        return productService.getAllProudcts();
    }

    @GetMapping("/getproductbyid")
    public ResponseEntity<Object> getProductById(@RequestBody int id){
        try{
            Product product = productService.getByProductId(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        catch (InfoNotFoundException e){
            return new ResponseEntity<>("Product Not Found",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getproductbycode")
    public ResponseEntity<Object> getProductByCode(@RequestBody String code){
        try{
            Product product = productService.getByProductCode(code);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        catch (InfoNotFoundException e){
            return new ResponseEntity<>("Product Not Found",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getproductbyname")
    public ResponseEntity<Object> getProductByName(@RequestParam String name){
        try{
            List<Product> productList = productService.getByProductName(name);
            return new ResponseEntity<>(productList,HttpStatus.OK);
        }
        catch (InfoNotFoundException e){
            return new ResponseEntity<>("Product Not Found",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/newproduct")
    public ResponseEntity<Object> newProduct(@RequestBody Product newProduct){
        try{
            Product product = productService.newProduct(newProduct);
            return new ResponseEntity<>(product,HttpStatus.OK);
        }
        catch (InvalidInputException invalidInputException){
            return new ResponseEntity<>("Invalid Input",HttpStatus.BAD_REQUEST);
        }
        catch(InfoExistedException existedException){
            return new ResponseEntity<>("Product Code Existed",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/updateproduct")
    public ResponseEntity<Object> updateProduct(@RequestBody UpdateProduct updateProduct){
        try{
            Product product = productService.updateProduct(updateProduct.getProductCode(),updateProduct.getProduct());
            return new ResponseEntity<>(product,HttpStatus.OK);
        }
        catch (InvalidInputException invalidInputException){
            return new ResponseEntity<>("Invalid Input",HttpStatus.BAD_REQUEST);
        }
        catch (InfoNotFoundException infoNotFoundException){
            return new ResponseEntity<>("Product Not Found",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/deleteproductbyid")
    public ResponseEntity<Object> deleteProductById(@RequestBody int productId){
        try{
            Product product = productService.deleteProductById(productId);
            return new ResponseEntity<>(product,HttpStatus.OK);
        }
        catch (InfoNotFoundException infoNotFoundException){
            return new ResponseEntity<>("Product Not Found",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/deleteAll")
    public ResponseEntity<Object> deleteAllProduct(){
        productService.deleteAllProduct();
        return new ResponseEntity<>("All Product Deleted",HttpStatus.OK);
    }

}
