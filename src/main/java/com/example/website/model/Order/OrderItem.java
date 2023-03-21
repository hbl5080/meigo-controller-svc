package com.example.website.model.Order;

import javax.persistence.*;

@Entity
public class OrderItem {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "orderItemIDGenerator")
    @SequenceGenerator(name = "orderItemIDGenerator", sequenceName = "orderItemSeq",initialValue = 0,allocationSize = 1)
    private Long id;

    @Column(name = "product_id")
    private Long productId;
    private int amount;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long product) {
        this.productId = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
