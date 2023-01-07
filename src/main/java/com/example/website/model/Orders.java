package com.example.website.model;

import com.example.website.model.Product.Product;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Data
public class Orders {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "orderIDGenerator")
   @SequenceGenerator(name = "orderIDGenerator", sequenceName = "orderSeq",initialValue = 1,allocationSize = 1)
   @Column(name = "order_id")
   private int orderID;
   private String orderNumber;

   @NotBlank
   @ManyToOne
   @JoinColumn(name = "address_id")
   private Address address;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private User user;

   @NotBlank
   @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
   private List<Product> products;
}
