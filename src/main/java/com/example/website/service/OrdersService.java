package com.example.website.service;

import com.example.website.Repo.OrderRepo;
import com.example.website.Repo.Product.ProductRepo;
import com.example.website.exceptionHandler.InfoExistedException;
import com.example.website.exceptionHandler.InfoNotFoundException;
import com.example.website.exceptionHandler.InvalidInputException;
import com.example.website.model.Order.Order;
import com.example.website.model.Order.OrderItem;
import com.example.website.model.Product.Product;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    ProductRepo productRepo;

    public List<Order> getAllOrder() {
        return orderRepo.findAll();
    }

    public Order getOrderById(Long orderID) {
        Optional<Order> order = orderRepo.findById(orderID);
        if (order.isPresent()) {
            return order.get();
        }

        throw new InfoNotFoundException();
    }

    public Order newOrder(Order newOrder) {
        List<Product> productList = new ArrayList();
        newOrder.getProducts().stream().forEach(
                orderItem -> {
                    Product product = productRepo.findById(orderItem.getProductId())
                            .orElseThrow(() -> new InfoNotFoundException("Product does not exist"));
                    if (product.getProductNum() < orderItem.getAmount()) {
                        throw new InvalidInputException("Not enough products");
                    }
                    product.setProductNum(product.getProductNum() - orderItem.getAmount());
                    productList.add(product);
                }
        );

        productRepo.saveAll(productList);

        newOrder.setCreatedDate(new Timestamp(new java.util.Date().getTime()));
        newOrder.setUpdatedDate(new Timestamp(new java.util.Date().getTime()));
        newOrder.setOrderNumber("OR" + RandomStringUtils.random(15,false,true));
        orderRepo.save(newOrder);
        return newOrder;
    }

    public Order getOrderByOrderNumber(String orderNum){
        return orderRepo.findByOrderNumber(orderNum).orElseThrow(()-> new InfoNotFoundException("Order dose not exist"));
    }

    public Order cancelOrder(Long orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow(()-> new InfoNotFoundException("Order dose not exist"));
        order.setStatus("cancelled");

        order.getProducts().parallelStream().forEach(orderItem -> {
            Optional<Product> tempProduct = productRepo.findById(orderItem.getProductId());
            if (tempProduct.isPresent()) {
                Product product = tempProduct.get();
                product.setProductNum(product.getProductNum() + orderItem.getAmount());
                productRepo.save(product);
            }
        });

        order.setUpdatedDate(new Timestamp(new java.util.Date().getTime()));
        orderRepo.save(order);
        return order;
    }

    public Order updateOrder(Order updateOrder, Long orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow(()-> new InfoNotFoundException("Order dose not exist"));
        Map<Long, Integer> amounts = updateOrder.getProducts().stream().collect(Collectors.toMap(OrderItem::getProductId, OrderItem::getAmount));
        List<Product> productList = new ArrayList<>();
        order.getProducts().forEach(orderItem -> {
            if(orderItem.getAmount() != amounts.get(orderItem.getProductId())){
                Optional<Product> tempProduct = productRepo.findById(orderItem.getProductId());
                if (tempProduct.isPresent()) {
                    Product product = tempProduct.get();
                    product.setProductNum(product.getProductNum() + (orderItem.getAmount() - amounts.get(orderItem.getProductId())));
                    if(product.getProductNum() < 0){
                        throw new InvalidInputException("Not enough Amount on product " + product.getProductName());
                    }
                    productList.add(product);
                }
            }
        });
        productRepo.saveAll(productList);
        updateOrder.setOrderID(orderId);
        updateOrder.setUpdatedDate(new Timestamp(new java.util.Date().getTime()));
        orderRepo.save(updateOrder);
        return updateOrder;
    }

}

