package com.fds.productservice.service;

import com.fds.productservice.dto.ProductRequest;
import com.fds.productservice.dto.ProductRespon;
import com.fds.productservice.model.Product;
import com.fds.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@RequiredArgsConstructor
@Service
@Slf4j
public class ProductService {

    /**
     * chưa tồn tại ProductRepository nên cần tạo contrutor cho nó tuy nhiên thay vì làm như vậy chỉ
     * cần dùng @RequiredArgsConstructor
     */
    @Autowired
    private ProductRepository productRepository;
    @SneakyThrows
    public void creatProduct(ProductRequest productRequest){
        Product product=Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        //Thread.sleep(10000);
        productRepository.save(product);
        log.info("product {} is save", product.getId());// id của product sẽ được điền vào trong place holder {}
    }

    public List<ProductRespon> getAllProduct() throws InterruptedException {
       Thread.sleep(2000);
       List<Product> products= productRepository.findAll();
    return products.stream().map(this::maptoProductResponse).collect(Collectors.toList());
    /*
         dùng stream để đưa thành 1 dòng dữ liệu  sau
         đó với từng product thì thực hiện mã mapto.
        collect(Collectors.toList()) để chuyển lại về dạng list
        */
    }
    public void deleteProduct(String id){
     boolean product=productRepository.findById(id).isPresent();
      if(product){
          productRepository.deleteProductById(id);

      }else {
       throw new RuntimeException("id không tồn tại");
      }
    }
    public void deleteProductName(String name) {
        boolean product = productRepository.findProductByName(name).isPresent();
        if (product) {
            productRepository.deleteProductByName(name);

        } else {
            throw new RuntimeException("id không tồn tại");
        }
    }

    private ProductRespon maptoProductResponse(Product product) {
        return ProductRespon.builder()
                .id(product.getId())
                .description(product.getDescription())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
