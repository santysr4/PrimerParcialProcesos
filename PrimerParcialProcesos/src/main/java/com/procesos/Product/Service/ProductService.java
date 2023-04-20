package com.procesos.Product.Service;

import com.procesos.Product.Models.Product;

import java.util.List;

public interface ProductService {
    Product getProduct(Long id);
    List<Product> allProduct();
    Boolean createProduct();
    Boolean updateProduct(Long id, Product product);
}
