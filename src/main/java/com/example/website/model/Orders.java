package com.example.website.model;

import com.example.website.model.Product.Product;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Orders {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "orderIDGenerator")
   @SequenceGenerator(name = "orderIDGenerator", sequenceName = "orderSeq",initialValue = 1,allocationSize = 1)
   private int orderID;
   private String orderNumber;

   @ManyToOne
   @JoinColumn(name = "address_id")
   private Address address;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private User user;

   @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
   @JoinColumn(name = "product_id")
   private List<Product> products;
}
