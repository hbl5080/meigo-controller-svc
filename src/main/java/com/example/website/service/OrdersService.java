package com.example.website.service;

import com.example.website.Repo.OrderRepo;
import com.example.website.Repo.Product.ProductRepo;
import com.example.website.exceptionHandler.InfoExistedException;
import com.example.website.exceptionHandler.InfoNotFoundException;
import com.example.website.exceptionHandler.InvalidInputException;
import com.example.website.model.Order.Order;
import com.example.website.model.Order.OrderItem;
import com.example.website.model.Product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    ProductRepo productRepo;

    public List<Order> getAllOrder(){
        return orderRepo.findAll();
    }

    public Order getOrderById(Long orderID){
        Optional<Order> order = orderRepo.findById(orderID);
        if(order.isPresent()){
            return order.get();
        }

        throw new InfoNotFoundException();
    }

    public Order newOrder(Order newOrder){
        Optional<Order> order =orderRepo.findOrderByOrderNumber(newOrder.getOrderNumber());
        if(order.isPresent()){
            throw new InfoExistedException();
        }

        for (OrderItem orderItem:newOrder.getProducts()){
            Optional<Product> product = productRepo.findById(orderItem.getProduct().getProductId());
            if(product.isPresent()){
                Product newProduct = orderItem.getProduct();
                if(newProduct.getProductNum()<orderItem.getAmount()){
                    throw new InvalidInputException();
                }
            }
            else{
                throw new InfoNotFoundException("Product does not exist");
            }

        }

        for (OrderItem orderItem:newOrder.getProducts()){
            Optional<Product> product = productRepo.findById(orderItem.getProduct().getProductId());
            if(product.isPresent()){
                product.get().setProductNum(product.get().getProductNum()- orderItem.getAmount());
                productRepo.save(product.get());
            }
            else{
                throw new InfoNotFoundException("Product does not exist");
            }
        }

        orderRepo.save(newOrder);
        return newOrder;
    }

    public Order cancelOrder(Long orderId){
        Optional<Order> tempOrder = orderRepo.findById(orderId);
        if(!tempOrder.isPresent()){
            throw new InfoNotFoundException("Order dose not exist");
        }
        Order order = tempOrder.get();
        for (OrderItem orderItem: order.getProducts()){
            Optional<Product> product = productRepo.findById(orderItem.getProduct().getProductId());
            if (product.isPresent()){
                product.get().setProductNum(product.get().getProductNum()+ orderItem.getAmount());
                productRepo.save(product.get());
            }
            else{
                throw new InfoNotFoundException("Product does not exist");
            }
        }

        orderRepo.deleteById(order.getOrderID());
        return order;
    }

    public Order updateOrder(Order updateOrder, Long orderId){
        Optional<Order> order = orderRepo.findById(orderId);
        if(!order.isPresent()){
            throw new InfoNotFoundException("Order does not exist");
        }

        HashMap<Long, Integer> amounts = new HashMap<>();
        // put all old products to amounts
        for (OrderItem orderItem:order.get().getProducts()){
            Optional<Product> product = productRepo.findById(orderItem.getProduct().getProductId());
            if (product.isPresent()){
                Integer amountInStock = product.get().getProductNum()+orderItem.getAmount();
                amounts.put(orderItem.getProduct().getProductId(),amountInStock);
            }
            else {
                throw new InvalidInputException("Something went wrong");
            }
        }

        // check availability for updateOrder
        for (OrderItem orderItem:updateOrder.getProducts()){
            Optional<Product> product = productRepo.findById(orderItem.getProduct().getProductId());
            if(product.isPresent()){
                Long productId =product.get().getProductId();
                if (!amounts.containsKey(productId)){
                    amounts.put(productId,product.get().getProductNum());
                }

                if (amounts.get(productId)<orderItem.getAmount()){
                    throw new InvalidInputException("Not enough Amount");
                }

                amounts.put(productId,amounts.get(productId)-orderItem.getAmount());
            }
            else{
                throw new InfoNotFoundException("Product does not exist");
            }
        }

        // update products
        for(Long productId: amounts.keySet()){
            Optional<Product> product = productRepo.findById(productId);
            if(product.isPresent()){
                product.get().setProductNum(amounts.get(productId));
                productRepo.save(product.get());
            }
            else{
                throw new InfoNotFoundException("Product does not exist");
            }
        }

        updateOrder.setOrderID(orderId);
        orderRepo.save(updateOrder);
        return updateOrder;
    }

}


