package com.procesos.Product.Controller;

import com.procesos.Product.Models.Product;
import com.procesos.Product.Service.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

public class ProductController {

    @Autowired
    private ProductServiceImp productServiceImp;

    @GetMapping(value = "/product/{id}")
    public ResponseEntity findProductById(@PathVariable Long id){

        Map response = new HashMap();
        try {
            return new ResponseEntity(productServiceImp.getProduct(id), HttpStatus.OK);
        }catch(Exception e){
            response.put("status","404");
            response.put("message","No se encontro el producto!");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/product")
    public ResponseEntity findAllProducto(){
        Map response = new HashMap();
        try {
            return new ResponseEntity(productServiceImp.allProduct(), HttpStatus.OK);
        }catch(Exception e){
            response.put("status","404");
            response.put("message","No se encontraron los productos!");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping(value = "/product")
    public ResponseEntity saveProduct(){
        Map response = new HashMap();
        Boolean userResp = productServiceImp.createProduct();

        if(userResp == true){
            response.put("status", "201");
            response.put("message","Se creo el Producto");
            return new ResponseEntity(response, HttpStatus.CREATED);
        }
        response.put("status","400");
        response.put("message","Hubo un error al crear el Producto");
        return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
    }
    @PutMapping(value = "/product/{id}")

    public ResponseEntity updateProduct(@PathVariable Long id, @RequestBody Product product){
        Map response = new HashMap();
        Boolean userResp = productServiceImp.updateProduct(id,product);

        if(userResp == true){
            response.put("status", "200");
            response.put("message","Se actualizo el vehiculo");
            return new ResponseEntity(response, HttpStatus.OK);
        }
        response.put("status","400");
        response.put("message","Hubo un error al actualizar el vehiculo");
        return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
    }
}
