package com.fds.productservice.controller;

import com.fds.productservice.dto.ProductRequest;
import com.fds.productservice.dto.ProductRespon;
import com.fds.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/product")
public class ProductController {
    private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void creatProduct(@RequestBody ProductRequest productRequest){
        productService.creatProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductRespon> getAllProduct() throws InterruptedException {
       return  productService.getAllProduct();
    }

}
