package com.example.website.service;

import com.example.website.Repo.Product.PhotoRepo;
import com.example.website.Repo.Product.ProductRepo;
import com.example.website.exceptionHandler.InfoExistedException;
import com.example.website.exceptionHandler.InfoNotFoundException;
import com.example.website.exceptionHandler.InvalidInputException;
import com.example.website.model.Product.Photo;
import com.example.website.model.Product.Product;
import com.example.website.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {


    @Autowired
    ProductRepo productRepo;

    @Autowired
    PhotoRepo photoRepo;

    ProductService productService;


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

    public Product getByProductId(Long id){
        Optional<Product> product = productRepo.findById(id);
        if(!product.isPresent()){
            throw new InfoNotFoundException();
        }

        return product.get();
    }

    public String getByProductCode(String code){
        Optional<Product> product = productRepo.getProductByProductCode(code);
        if(!product.isPresent()){
            throw new InfoNotFoundException("Product Not Found");
        }

        return product.get().toString();
    }

    public String  getByProductName(String name){
        List<Product> productList = productRepo.getProductByProductName(name);


        if (productList.isEmpty()){
            throw new InfoNotFoundException("Product Not Found");
        }

        return productList.toString();
    }

    public Product newProduct(Product newProduct){

        Optional<Product> product = productRepo.getProductByProductCode(newProduct.getProductCode());
        if (product.isPresent()){
            throw new InfoExistedException("Product Existed");
        }
        productRepo.save(newProduct);
        return newProduct;
    }

    public String updateProduct(String productCode, Product updateProduct){
        if (updateProduct.getProductCode()==null||updateProduct.getProductNum()<0||updateProduct.getIntakePrice()<0 ){
            throw new InvalidInputException("Invalid Input");
        }

        Optional<Product> product = productRepo.getProductByProductCode(productCode);
        if(!product.isPresent()){
            throw new InfoNotFoundException("Product Not Found");
        }
        Optional<Product> checkProduct = productRepo.getProductByProductCode(updateProduct.getProductCode());
        if(checkProduct.isPresent() && !productCode.equals(checkProduct.get().getProductCode()) ){
            throw new InfoExistedException();
        }

        updateProduct.setProductId(product.get().getProductId());
        productRepo.save(updateProduct);
        return updateProduct.toString();
    }

    public String deleteProductById(Long id){
        Optional<Product> product = productRepo.findById(id);
        if(!product.isPresent()){
            throw new InfoNotFoundException("Product Not Found");
        }
        Product tempProduct = product.get();
        List<Photo> photoList = productRepo.getImgByProductId(id);
        for(Photo photo:photoList){
            tempProduct.remove(photo);
            photoRepo.delete(photo);
        }

        productRepo.deleteById(id);
        return tempProduct.toString();
    }

    public String deleteProductByCode (String code){
        Optional<Product> product = productRepo.getProductByProductCode(code);
        if(!product.isPresent()){
            throw new InfoNotFoundException();
        }

        Product tempProduct = product.get();
        Long id = tempProduct.getProductId();
        List<Photo> photoList = productRepo.getImgByProductId(id);
        for(Photo photo:photoList){
            tempProduct.remove(photo);
            photoRepo.delete(photo);
        }

        productRepo.deleteById(tempProduct.getProductId());
        return tempProduct.toString();
    }

    public String deleteAllProduct(){
        photoRepo.deleteAll();
        productRepo.deleteAll();
        return "All Deleted";
    }


    //Photo

    public String deleteAllPhoto(){
        photoRepo.deleteAll();

        return "All Photo Deleted";
    }

    public Photo getPhotoByProductCode(Long photoId){
        Optional<Photo> photo = photoRepo.getPhotoById(photoId);
        if(!photo.isPresent()){
            throw new InfoNotFoundException();
        }

        return photo.get();
    }

    public Photo getPhotoByPhotoId(Long photoId){
        Optional<Photo> photo = photoRepo.getPhotoById(photoId);
        if(!photo.isPresent()){
            throw new InfoNotFoundException();
        }
        Photo tempPhoto = photo.get();
        tempPhoto.setImageBytes(ImageUtil.decompressImage(photo.get().getImageBytes()));
        return tempPhoto;
    }

    public String addPhoto(MultipartFile file, String productCode){
        Optional<Product> product = productRepo.getProductByProductCode(productCode);
        if(!product.isPresent()){
            throw new InfoNotFoundException();
        }
        Photo photo = new Photo();
        try{
            photo.setImageName(file.getOriginalFilename());
            photo.setImageType(file.getContentType());
            photo.setImageBytes(ImageUtil.compressImage(file.getBytes()));
        }
        catch(IOException ioException){
            throw new InvalidInputException();
        }

        Product tempProduct = product.get();
        photo = photoRepo.save(photo);
        tempProduct.addPhoto(photo);
        if(photo.getImageName().equals(tempProduct.getDefaultImg())){
            tempProduct.setDefaultImgCode(photo.getPhotoId());
        }else if(tempProduct.getDefaultImg() == null){
            tempProduct.setDefaultImg(photo.getImageName());
            tempProduct.setDefaultImgCode(photo.getPhotoId());
        }
        productRepo.save(tempProduct);
        return tempProduct.toString();
    }

    public String removePhotoById(Long photoId){

        Optional<Photo> photo = photoRepo.getPhotoById(photoId);
        if(!photo.isPresent()){
            throw new InfoNotFoundException();
        }

        Optional<Product> product = productRepo.findById(photo.get().getProduct().getProductId());
        if(!product.isPresent()){
            throw new InfoNotFoundException();
        }

        product.get().remove(photo.get());
        photoRepo.deleteById(photoId);
        String productString = product.get().toString();
        return productString;
    }

    public String removeAllPhotoByProductCode(String productCode){
        Optional<Product> product = productRepo.getProductByProductCode(productCode);
        if(!product.isPresent()){
            throw new InfoNotFoundException();
        }

        Product tempProduct = product.get();
        Long id = tempProduct.getProductId();
        List<Photo> photoList = productRepo.getImgByProductId(id);
        for(Photo photo:photoList){
            tempProduct.remove(photo);
            photoRepo.delete(photo);
        }

        return tempProduct.toString();
    }


}
