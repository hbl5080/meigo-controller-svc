package com.example.website.controller;


import com.example.website.exceptionHandler.InfoExistedException;
import com.example.website.exceptionHandler.InfoNotFoundException;
import com.example.website.exceptionHandler.InvalidInputException;
import com.example.website.model.Product.Photo;
import com.example.website.model.Product.Product;
import com.example.website.model.Product.UpdateProduct;
import com.example.website.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@CrossOrigin
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
    public ResponseEntity<Object> getPhotoByProductCode(@RequestParam Long photoId)
            throws InfoNotFoundException{
        try{
            Photo photo = productService.getPhotoByProductCode(photoId);
            HttpHeaders httpHeaders = new HttpHeaders();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; file name=\""+photo.getImageName()+"\"")
                    .body(photo.getImageBytes());
        }
        catch (InfoNotFoundException infoNotFoundException){
            throw new InfoNotFoundException();
        }
    }

    @PostMapping("/newphoto")
    public ResponseEntity<Object> newPhoto(@RequestParam String productCode,@RequestParam("files") MultipartFile[] files)

            throws InfoExistedException, InvalidInputException {
        try{
            List<String> fileNames = new ArrayList<>();

            Arrays.asList(files).stream().forEach(file -> {
                productService.addPhoto(file, productCode);
                fileNames.add(file.getOriginalFilename());
            });
            String message = "Upload the files successfully" + fileNames;
            return new ResponseEntity<>(message,HttpStatus.OK);
        }
        catch (InfoExistedException infoExistedException){
            throw new InfoExistedException();
        }
        catch (InvalidInputException invalidInputException){
            throw new InvalidInputException();
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
