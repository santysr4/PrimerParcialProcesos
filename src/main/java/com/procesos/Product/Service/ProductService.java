package com.procesos.Product.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.procesos.Product.Models.Product;

import java.util.List;

public interface ProductService {
    Product getProduct(Long id);
    List<Product> allProduct();
    Boolean createProduct(Long id, Long id_user) throws JsonProcessingException;
    Boolean updateProduct(Long id, Product product);
    Boolean delete(Long id);
}
