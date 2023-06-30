package com.fds.orderservice.service;

import com.fds.orderservice.dto.InventoryRespon;
import com.fds.orderservice.dto.OrderLineItemsDto;
import com.fds.orderservice.dto.OrderRequest;
import com.fds.orderservice.exception.handle.CustomAccessDeniedHandler;
import com.fds.orderservice.model.Order;
import com.fds.orderservice.model.OrderLineItems;
import com.fds.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class OrderService {
    @Autowired
    private  OrderRepository orderRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @SneakyThrows// tự động bắt và xử lý ngoại lệ
    public String  placeOrder(OrderRequest orderRequest) {
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
//       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("Authentication: " + authentication);
//       JSONObject principalObj = new JSONObject(authentication.getPrincipal());
//
//
//        String token=principalObj.getString("tokenValue");

        InventoryRespon[] inventoryResponsArray= webClientBuilder.build().get()
                .uri("http://localhost:8082/api/inventory",uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .header("Authorization", "Bearer"+" " +"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJaZnJaNl92VkU1NFFFeUllVThtUzQzTWw5ckNfQnJKclRzSXJ3OGlETUNnIn0.eyJleHAiOjE2ODgxMTY0NzgsImlhdCI6MTY4ODExNjE3OCwianRpIjoiOTNlZTdjZDAtZDRhNi00YTM3LWEzMzgtMTg4NDJiOTNjZjY3IiwiaXNzIjoiaHR0cHM6Ly9pZHAuZmRzLnZuL3JlYWxtcy9taWNyb3NlcnZpY2UxNTYiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiZGE5ZmQ2MzItZjI2ZS00ZmQxLThlYzktMGU5MzU3MzA0Yjc1IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiY2xpZW50LW1pY3Jvc2VydmljZSIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiIsImRlZmF1bHQtcm9sZXMtbWljcm9zZXJ2aWNlMTU2Il19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJjbGllbnRIb3N0IjoiMTEzLjE5MC4yNTIuNzEiLCJjbGllbnRJZCI6ImNsaWVudC1taWNyb3NlcnZpY2UiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzZXJ2aWNlLWFjY291bnQtY2xpZW50LW1pY3Jvc2VydmljZSIsImNsaWVudEFkZHJlc3MiOiIxMTMuMTkwLjI1Mi43MSJ9.A95Eayr89UfABLzfSjFR_x4Au3XvzIflzGkkHYIBmvacRJ_nZYwygxLJWLCvw10xu7942Z7tXK8rvPqlmttLKULzan_CzEBFU4qKDyAdqlerdCL3GkUKKdeO54x-PEN2lmdROEN_BN7jDTMe-YLi3UMdhe1QgimoUcIZdlH3y-7frGR9Bsob0Z3ozK5kVgMagUCXmiA4fTbHjU2DDm7Lo0gLcSJ7E_5gTSK3xPGQYxvfF4IyieZAOliOg6wV0th-nFIBGwRo-4yNndIWbevf3ySBO7FJqGuTDo-6B9-uUA4jXTMc3BtffL29beGMoXJD278MwuhkqevPEj2PLZz2zQ")
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
//        List<String> skuCodes = order.getOrderLineItemsList()
//                        .stream()
//                .map(OrderLineItems::getSkuCode).collect(Collectors.toList());//lấy ra mã skuCode của từng item
//
//        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
//                .uri("http://inventory-service/api/inventory",
//                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
//                .retrieve()
//                .bodyToMono(InventoryResponse[].class)
//                .block();
//
//        boolean allProductInstock = Arrays.stream(inventoryResponArray).allMatch(InventoryRespon::isInStock);
//        // kiểm tra tất cả các phần tử của mảng đó có thuộc InStock hay ko, nếu tất cả đều thuộc thì trả về true
//       if(allProductInstock){
//        orderRepository.save(order);}
//       else {
//           throw new IllegalAccessException("lỗi rồi");
//      }





//        WebClient client = WebClient.create();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        JSONObject principalObj = new JSONObject(authentication.getPrincipal());
//
//        String token=principalObj.getString("tokenValue");
//
//        String response = client.get()
//                .uri(new URI("http://localhost:8082/api/inventory?skuCode="+skuCodes.stream().toString()))
//
//                .header("Authorization", "Bearer"+" " +token)
//
//                .accept(MediaType.APPLICATION_JSON)
//
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();

    }


    private OrderLineItems maptoDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }



}
