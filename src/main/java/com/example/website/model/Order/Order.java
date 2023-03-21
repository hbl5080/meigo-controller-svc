package com.example.website.model.Order;

import com.example.website.model.User.Address.Address;
import com.example.website.model.User.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
public class Order {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "orderIDGenerator")
   @SequenceGenerator(name = "orderIDGenerator", sequenceName = "orderSeq",initialValue = 1,allocationSize = 1)
   @Column(name = "order_id")
   private Long orderID;

   @Column(name = "order_number",unique = true,nullable = false)
   private String orderNumber;


   @ManyToOne
   //@JsonManagedReference
   @JoinColumn(name = "address_id")
   private Address address;

   @ManyToOne
   //@JsonManagedReference
   @JoinColumn(name = "user_id")
   private User user;

   @NotBlank
   @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
   private List<OrderItem> products;

   @Column(name = "payment")
   private Integer payment;

   @Column(name = "invoice_num")
   private String invoiceNum;

   @Column(name = "create_date")
   private Timestamp createdDate;

   @Column(name = "update_date")
   private Timestamp updatedDate;

   @Column(name = "status")
   private String status = "new";

   public Long getOrderID() {
      return orderID;
   }

   public void setOrderID(Long orderID) {
      this.orderID = orderID;
   }

   public String getOrderNumber() {
      return orderNumber;
   }

   public void setOrderNumber(String orderNumber) {
      this.orderNumber = orderNumber;
   }

   public Address getAddress() {
      return address;
   }

   public void setAddress(Address address) {
      this.address = address;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   private static final String ORDER_NUMBER_PREFIX = "JYMeiGo";
   private static final int ORDER_NUMBER_SIZE = 6;



}
