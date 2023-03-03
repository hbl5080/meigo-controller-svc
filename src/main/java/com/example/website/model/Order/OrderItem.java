package com.example.website.model.Order;

import com.example.website.model.Product.Product;

import javax.persistence.*;

@Entity
public class OrderItem {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "orderItemIDGenerator")
    @SequenceGenerator(name = "orderItemIDGenerator", sequenceName = "orderItemSeq",initialValue = 0,allocationSize = 1)
    private Long id;

    @OneToOne
    private Product product;
    private int amount;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
