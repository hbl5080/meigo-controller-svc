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
        Optional<Order> order =orderRepo.findByOrderNumber(newOrder.getOrderNumber());
        if(order.isPresent()){
            throw new InfoExistedException();
        }

        newOrder.getProducts().stream().forEach(
                orderItem->{
                    Product Product = productRepo.findById(orderItem.getProductId())
                            .orElseThrow(()->new InfoNotFoundException("Product does not exist"));
                    if (Product.getProductNum()<orderItem.getAmount()){
                        throw new InvalidInputException("Not enough products");
                    }
                }
        );


        newOrder.getProducts().stream().forEach(
                orderItem -> {
                    Product product = productRepo.findById(orderItem.getProductId())
                            .orElseThrow(()->new InfoNotFoundException("Product does not exist 2"));
                    product.setProductNum(product.getProductNum()-orderItem.getAmount());
                    productRepo.save(product);
                }
        );

        orderRepo.save(newOrder);
        return newOrder;

        //        for (OrderItem orderItem:newOrder.getProducts()){
//            Optional<Product> product = productRepo.findById(orderItem.getProductId());
//            if(product.isPresent()){
//                Product newProduct = orderItem.getProductId();
//                if(newProduct.getProductNum()<orderItem.getAmount()){
//                    throw new InvalidInputException();
//                }
//            }
//            else{
//                throw new InfoNotFoundException("Product does not exist");
//            }
//
//        }

//        for (OrderItem orderItem:newOrder.getProducts()){
//            Optional<Product> product = productRepo.findById(orderItem.getProductId().getProductId());
//            if(product.isPresent()){
//                product.get().setProductNum(product.get().getProductNum()- orderItem.getAmount());
//                productRepo.save(product.get());
//            }
//            else{
//                throw new InfoNotFoundException("Product does not exist");
//            }
//        }
    }

    public Order cancelOrder(Long orderId){
        Optional<Order> tempOrder = orderRepo.findById(orderId);
        if(!tempOrder.isPresent()){
            throw new InfoNotFoundException("Order dose not exist");
        }
        Order order = tempOrder.get();
        for (OrderItem orderItem: order.getProducts()){
            Optional<Product> product = productRepo.findById(orderItem.getProductId());
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
            Optional<Product> product = productRepo.findById(orderItem.getProductId());
            if (product.isPresent()){
                Integer amountInStock = product.get().getProductNum()+orderItem.getAmount();
                amounts.put(orderItem.getProductId(),amountInStock);
            }
            else {
                throw new InvalidInputException("Something went wrong");
            }
        }

        // check availability for updateOrder
        for (OrderItem orderItem:updateOrder.getProducts()){
            Optional<Product> product = productRepo.findById(orderItem.getProductId());
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

//    public Order newOrder(String orderNumber, List<OrderItem> orderItemList, Long userId, Address address){
//        Optional<Order> order =orderRepo.findOrderByOrderNumber(orderNumber);
//
//        if(order.isPresent()){
//            throw new InfoExistedException("Order Existed");
//        }
//
//        //check validation for orderItem list
//        for (OrderItem orderItem:orderItemList){
//            Optional<Product> product = productRepo.findById(orderItem.getProductId());
//            if(product.isPresent()){
//
//                if(product.get().getProductNum()<orderItem.getAmount()){
//                    throw new InvalidInputException();
//                }
//            }
//            else{
//                throw new InfoNotFoundException("Product does not exist");
//            }
//
//        }
//
//        //check validation for user id and address
//        if(userId==null) {
//            addressRepo.save(address);
//        }
//        else{
//            Optional<User> user=userRepo.findById(userId);
//            if(!user.isPresent()){
//                throw new InfoNotFoundException("User Not Found");
//            }
//
//            if(!user.get().getAddressList().contains(address)) {
//                throw new InfoNotFoundException("Address Not Existed");
//            }
//        }
//        //minus order number
//        for (OrderItem orderItem:orderItemList){
//            Optional<Product> product = productRepo.findById(orderItem.getProductId());
//            if(product.isPresent()){
//                product.get().setProductNum(product.get().getProductNum()- orderItem.getAmount());
//                productRepo.save(product.get());
//            }
//            else{
//                throw new InfoNotFoundException("Product does not exist");
//            }
//        }
//
//
//        orderRepo.save(order.get());
//        return newOrder;
//    }

