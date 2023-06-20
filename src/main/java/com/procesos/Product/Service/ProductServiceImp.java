package com.procesos.Product.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.procesos.Product.Models.Product;
import com.procesos.Product.Repository.ProductRepository;
import com.procesos.Product.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService{

    private final RestTemplate restTemplate;
    private ProductRepository productrepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public ProductServiceImp(RestTemplate restTemplate, ProductRepository productrepository) {
        this.restTemplate = restTemplate;
        this.productrepository = productrepository;
    }




    @Override
    public Product getProduct(Long id){
        return productrepository.findById(id).get();
    }

    @Override
    public List<Product> allProduct() {
        return productrepository.findAll();
    }


    @Override
    public Boolean createProduct(Long id, Long id_user)throws JsonProcessingException {
        String urlApi = "https://6441aadb76540ce2257c3dfc.mockapi.io/product/"+id;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> producto = restTemplate.getForEntity(urlApi, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        Product product = objectMapper.readValue(producto.getBody(), Product.class);
        System.out.println(product.getPrecio());
        if(productrepository.findById(id).isEmpty() && !userRepository.findById(id_user).isEmpty()){
            product.setUser(userService.getUser(id_user));
            productrepository.save(product);
            return true;
        }
        return false;
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
    @Override
    public Boolean delete(Long id){
        try {
            Product product = productrepository.findById(id).get();
            productrepository.delete(product);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
