package com.example.website.controller;


import com.example.website.exceptionHandler.InfoNotFoundException;
import com.example.website.exceptionHandler.InvalidInputException;
import com.example.website.model.Order.Order;
import com.example.website.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    OrdersService ordersService;


    @GetMapping("/getallorders")
    public List<Order> getAllOrders(){
        return ordersService.getAllOrder();
    }
    @GetMapping("/getorderbyid")
    public ResponseEntity<Object> getOrderById(@RequestParam Long orderId)
        throws InfoNotFoundException {
        try{
            Order order = ordersService.getOrderById(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        catch (InfoNotFoundException infoNotFoundException){
            throw new InfoNotFoundException("Order Not Found");
        }
    }
    @PostMapping("/neworder")
    public ResponseEntity<Object> newOrder(@RequestBody Order newOrder)
        throws InfoNotFoundException, InvalidInputException {
        try{
            Order order = ordersService.newOrder(newOrder);
            return new ResponseEntity<>(order,HttpStatus.OK);
        }
        catch (InfoNotFoundException infoNotFoundException){
            throw new InfoNotFoundException();
        }
        catch (InvalidInputException invalidInputException){
            throw new InvalidInputException();
        }
    }

    @DeleteMapping("/cancelorder")
    public ResponseEntity<Object> cancelOrder(@RequestParam Long orderId)
        throws InfoNotFoundException{
        try{
            Order order = ordersService.cancelOrder(orderId);
            return new ResponseEntity<>(order,HttpStatus.OK);
        }
        catch (InfoNotFoundException infoNotFoundException){
            throw new InfoNotFoundException();
        }
    }

    @PostMapping("/updateorder")
    public ResponseEntity<Object> updateOrder(@RequestBody Order updateOrder,@RequestParam Long orderId)
        throws InfoNotFoundException,InvalidInputException{
        try{
            Order order = ordersService.updateOrder(updateOrder, orderId);
            return new ResponseEntity<>(order,HttpStatus.OK);
        }
        catch (InfoNotFoundException infoNotFoundException){
            throw new InfoNotFoundException();
        }
        catch (InvalidInputException invalidInputException){
            throw new InvalidInputException();
        }
    }
}
