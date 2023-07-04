package com.fds.orderservice.contrller;

import com.fds.orderservice.dto.OrderRequest;
import com.fds.orderservice.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;


@RestController
@RequestMapping("/api/order")

public class OrderController {
    @Autowired
    private  OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory",fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
   @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest){
    return CompletableFuture.supplyAsync(()->orderService.placeOrder(orderRequest));
//    public String placeOrder(@RequestBody OrderRequest orderRequest){
//        return orderService.placeOrder(orderRequest);

    }
    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest,RuntimeException runtimeException){
       return CompletableFuture.supplyAsync(()-> "đang gặp vấn đề, xin vui lòng thử lại sau") ;
      //  return "đang gặp vấn đề, xin vui lòng thử lại sau";
    }
}
