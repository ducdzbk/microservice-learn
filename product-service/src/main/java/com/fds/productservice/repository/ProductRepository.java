package com.fds.productservice.repository;

import com.fds.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, Long> {

    Optional<Product> findById(String id);
    Optional<Product> deleteProductById(String id);
    Optional<Product> findByName(String name);
    Optional<Product> deleteProductByName(String name);
    Optional<Product> findProductByName(String name);


}
