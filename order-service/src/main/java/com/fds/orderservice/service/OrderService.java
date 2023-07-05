package com.fds.orderservice.service;

import com.fds.orderservice.dto.InventoryRespon;
import com.fds.orderservice.dto.OrderLineItemsDto;
import com.fds.orderservice.dto.OrderRequest;
import com.fds.orderservice.exception.NotfoundException;
import com.fds.orderservice.exception.handle.CustomAccessDeniedHandler;
import com.fds.orderservice.model.Order;
import com.fds.orderservice.model.OrderLineItems;
import com.fds.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Service
@Transactional
public class OrderService {
    @Autowired
    private  OrderRepository orderRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @SneakyThrows// tự động bắt và xử lý ngoại lệ
   // public String  placeOrder(OrderRequest orderRequest) {
        public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream()
                .map(this::maptoDto)
                .collect(Collectors.toList());
        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList()
                        .stream()
                .map(OrderLineItems::getSkuCode).collect(Collectors.toList());

        WebClient client = WebClient.create();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
           JSONObject principalObj = new JSONObject(authentication.getPrincipal());

       String token=principalObj.getString("tokenValue");

        InventoryRespon[] inventoryResponsArray= webClientBuilder.build().get()
                .uri("http://localhost:8082/api/inventory",uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .header("Authorization", "Bearer"+" " +token)
                .retrieve()
                .bodyToMono(InventoryRespon[].class)
                .block();
        if(inventoryResponsArray!=null){
        for (InventoryRespon inventoryRespon : inventoryResponsArray) {
            System.out.println(inventoryRespon.toString());
        }}else{
            System.out.println("array null");
            }

        boolean allproduct = Arrays.stream(inventoryResponsArray).allMatch(InventoryRespon::isInStock);
        // Arrays.stream(inventoryResponsArray).allMatch(inventoryRespon -> inventoryRespon.isInStock());
        if(allproduct){

           orderRepository.save(order);
          return "còn hàng";
        }else {
            throw new IllegalArgumentException("KHÔNG TỒN TẠI");
        }

    }


    public void deleteOrderByOrderNumber(String orderNumber){
        Optional<Order> order=orderRepository.findByOrderNumber(orderNumber);
        if(order!=null){
            orderRepository.deleteByOrderNumber(orderNumber);
        }else {
            throw new RuntimeException();
        }
    }

    private OrderLineItems maptoDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }



}
