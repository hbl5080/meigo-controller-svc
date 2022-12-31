package com.example.website.model;

import com.example.website.model.Product.Product;
import lombok.Data;

import java.util.List;

@Data
public class Orders {
   int orderID;
   String orderNumber;
   Address address;
   List<Product> products;
}
