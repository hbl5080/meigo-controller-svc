package com.example.website.service;

import com.example.website.Repo.PhotoRepo;
import com.example.website.Repo.ProductRepo;
import com.example.website.exceptionHandler.InfoExistedException;
import com.example.website.exceptionHandler.InfoNotFoundException;
import com.example.website.exceptionHandler.InvalidInputException;
import com.example.website.model.Photo;
import com.example.website.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    PhotoRepo photoRepo;

    //Test
    public Product newProductForTesting(){
        return new Product();
    }

    public Photo newPhotoForTesting(){
        return new Photo();
    }

    //Product
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

    public List<Product> getByProductName(String name){
        List<Product> productList = productRepo.getProductByProductName(name);


        if (productList.isEmpty()){
            throw new InfoNotFoundException("Product Not Found");
        }

        return productList;
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

    public Product deleteProductById(int id){
        Optional<Product> product = productRepo.findById(id);
        if(!product.isPresent()){
            throw new InfoNotFoundException("Product Not Found");
        }

        List<Photo> photoList = product.get().getImg();
        for(Photo photo:photoList){
            photo.setProductId(null);
            photoRepo.save(photo);
        }

        productRepo.deleteById(id);
        return product.get();
    }

    public String deleteAllProduct(){
        productRepo.deleteAll();
        return "All Deleted";
    }


    //Photo
    public Photo newPhoto(Photo photo){
        photoRepo.save(photo);
        return photo;
    }

    public Photo updatePhoto(Photo updatePhoto, int photoId){
        Optional<Photo> photo =photoRepo.findById(photoId);
        if(!photo.isPresent()){
            throw new InfoNotFoundException("Photo Not Found");
        }

        updatePhoto.setPhotoId(photoId);
        photoRepo.save(updatePhoto);
        return updatePhoto;
    }

    public String deletePhotoById(int photoId){
        Optional<Photo> photo = photoRepo.findById(photoId);
        if(!photo.isPresent()){
            throw new InfoNotFoundException("Photo Not Found");
        }

        String string = photo.get().toString();
        photoRepo.deleteById(photoId);

        return string;
    }

    public Photo findPhotoByPhotoId(int photoId){
        Optional<Photo> photo = photoRepo.findById(photoId);
        if(!photo.isPresent()){
            throw new InfoNotFoundException("Photo Not Found");
        }

        return  photo.get();
    }

    public String deleteAllPhoto(){
        photoRepo.deleteAll();
        return "All photo Deleted";
    }



    //Product && Photo
    public String linkPhotoToProduct(int photoId, int productId){
        Optional<Photo> photo = photoRepo.findById(photoId);
        Optional<Product> product = productRepo.findById(productId);

        if ((!product.isPresent())||(!photo.isPresent())){
            throw new InfoNotFoundException("Invalid Input");
        }
        List<Photo> photoList = productRepo.getImgByProductCode(productId);
        photo.get().setProductId(productId);
        photoList.add(photo.get());
        product.get().setImg(photoList);

        photoRepo.save(photo.get());
        productRepo.save(product.get());

        return "Photo:\n"+ photo.get().toString() + "\nProduct:\n"+ product.get().toString();
    }
    
}
