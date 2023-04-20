package com.procesos.Product.Service;

import com.procesos.Product.Models.Product;
import com.procesos.Product.Repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService{

    private final RestTemplate restTemplate;
    private ProductRepository productrepository;

    public ProductServiceImp(RestTemplate restTemplate, ProductRepository productrepository) {
        this.restTemplate = restTemplate;
        this.productrepository = productrepository;
    }




    @Override
    public Product getProduct(Long id){
        String url="https://6441aadb76540ce2257c3dfc.mockapi.io/product/";
        System.out.println(url+id);
        Product response= restTemplate.getForObject(url+id, Product.class);
        return response;
    }

    @Override
    public List<Product> allProduct() {
        String url="https://6441aadb76540ce2257c3dfc.mockapi.io/product";
        Product[] response= restTemplate.getForObject(url, Product[].class);
        return Arrays.asList(response);
    }

    @Override
    public Boolean createProduct() {

        try {
            String url="https://6441aadb76540ce2257c3dfc.mockapi.io/product";
            Product[] response= restTemplate.getForObject(url, Product[].class);
            productrepository.saveAll(Arrays.asList(response));
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
    @Override
    public Boolean updateProduct(Long id, Product product) {
        try {
            Product productBD = productrepository.findById(id).get();
            productBD.setTitulo(product.getTitulo());
            productBD.setDescripcion(product.getDescripcion());
            productBD.setPrecio(product.getPrecio());
            productBD.setImagen(product.getImagen());
            productBD.setCategoria(product.getCategoria());
            Product productUp = productrepository.save(productBD);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
