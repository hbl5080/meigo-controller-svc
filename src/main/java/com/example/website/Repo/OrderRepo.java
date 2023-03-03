package com.example.website.Repo;

import com.example.website.model.Order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {

    @Query("SELECT o FROM Order o WHERE o.orderNumber =: orderNumber ")
    public Optional<Order> findOrderByOrderNumber(String orderNumber);


}
