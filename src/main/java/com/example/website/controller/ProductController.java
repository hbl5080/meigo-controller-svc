package com.example.website.controller;


import com.example.website.exceptionHandler.InfoExistedException;
import com.example.website.exceptionHandler.InfoNotFoundException;
import com.example.website.exceptionHandler.InvalidInputException;
import com.example.website.model.NewPhoto;
import com.example.website.model.Photo;
import com.example.website.model.Product;
import com.example.website.model.UpdateProduct;
import com.example.website.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public String getAllProduct(){
        return productService.getAllProudcts();
    }

    @GetMapping("/getproductbyid")
    public ResponseEntity<Object> getProductById(@RequestParam Long id) throws InfoNotFoundException{
        try{
            String product = productService.getByProductId(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        catch (InfoNotFoundException e){
            throw new InfoNotFoundException();
        }
    }

    @GetMapping("/getproductbycode")
    public ResponseEntity<Object> getProductByCode(@RequestParam String code) throws InfoNotFoundException{
        try{
            String product = productService.getByProductCode(code);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        catch (InfoNotFoundException e){
            throw new InfoNotFoundException();
        }
    }

    @GetMapping("/getproductbyname")
    public ResponseEntity<Object> getProductByName(@RequestParam String name) throws InfoNotFoundException{
        try{
            String productList = productService.getByProductName(name);
            return new ResponseEntity<>(productList,HttpStatus.OK);
        }
        catch (InfoNotFoundException e){
            throw new InfoNotFoundException();
        }
    }

    @PostMapping("/newproduct")
    public ResponseEntity<Object> newProduct(@Valid @RequestBody Product newProduct){
        try{
            String product = productService.newProduct(newProduct);
            return new ResponseEntity<>(product,HttpStatus.OK);
        }
        catch(InfoExistedException existedException){
            throw new InfoExistedException();
        }
    }

    @PostMapping("/updateproduct")
    public ResponseEntity<Object> updateProduct(@RequestBody @Valid UpdateProduct updateProduct)
        throws InfoNotFoundException,InfoExistedException{
        try{
            String product = productService.updateProduct(updateProduct.getProductCode(),updateProduct.getProduct());
            return new ResponseEntity<>(product,HttpStatus.OK);
        }
        catch (InfoNotFoundException infoNotFoundException){
            throw new InfoNotFoundException();
        }
        catch (InfoExistedException infoExistedException){
            throw new InfoExistedException();
        }

    }

    @DeleteMapping ("/deleteproductbyid")
    public ResponseEntity<Object> deleteProductById(@RequestParam Long productId)
        throws InfoNotFoundException{
        try{
            String product = productService.deleteProductById(productId);
            return new ResponseEntity<>(product,HttpStatus.OK);
        }
        catch (InfoNotFoundException infoNotFoundException){
            throw new InfoNotFoundException();
        }
    }

    @DeleteMapping("/deleteproductbycode")
    public ResponseEntity<Object> deleteProductByCode (@RequestParam String productCode)
            throws InfoNotFoundException {
        try{
            String product = productService.deleteProductByCode(productCode);
            return new ResponseEntity<>(product,HttpStatus.OK);
        }
        catch (InfoNotFoundException infoNotFoundException){
            throw new InfoNotFoundException();
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Object> deleteAllProduct(){
        productService.deleteAllProduct();
        return new ResponseEntity<>("All Product Deleted",HttpStatus.OK);
    }

//    Photo
    @GetMapping("/getphotobyproductcode")
    public ResponseEntity<Object> getPhotoByProductCode(@RequestParam String productCode)
            throws InfoNotFoundException{
        try{
            String photoList = productService.getPhotoByProductCode(productCode);
            return new ResponseEntity<>(photoList,HttpStatus.OK);
        }
        catch (InfoNotFoundException infoNotFoundException){
            throw new InfoNotFoundException();
        }
    }

    @PostMapping("/newphoto")
    public ResponseEntity<Object> newPhoto(@RequestBody @Valid NewPhoto newPhoto)
            throws InfoExistedException{
        try{
            String product = productService.addPhoto(newPhoto.getPhoto(), newPhoto.getProductCode());
            return new ResponseEntity<>(product,HttpStatus.OK);
        }
        catch (InfoExistedException infoExistedException){
            throw new InfoExistedException();
        }
    }

    @DeleteMapping("/deletephotobyphotoid")
    public ResponseEntity<Object> deletePhotoByPhotoId(@RequestParam Long photoId)
            throws InfoNotFoundException{
        try{
            String product = productService.removePhotoById(photoId);
            return new ResponseEntity<>(product,HttpStatus.OK);
        }
        catch (InfoNotFoundException infoNotFoundException){
            throw new InfoNotFoundException();
        }
    }

    @DeleteMapping("/deleteallphotobyproductcode")
    public ResponseEntity<Object> deleteAllPhotoByProductCode(@RequestParam String productCode)
            throws InfoNotFoundException{
        try{
            String product = productService.removeAllPhotoByProductCode(productCode);
            return new ResponseEntity<>(product,HttpStatus.OK);
        }
        catch (InfoNotFoundException infoNotFoundException) {
            throw new InfoNotFoundException();
        }
    }


}
