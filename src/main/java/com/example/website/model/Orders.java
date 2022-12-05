package com.example.website.model;

import lombok.Data;

import java.util.List;

@Data
public class Orders {
   int orderID;
   String orderNumber;
   Address address;
   List<Product> products;
}
