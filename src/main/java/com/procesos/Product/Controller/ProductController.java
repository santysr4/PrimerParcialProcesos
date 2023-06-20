package com.procesos.Product.Controller;

import com.procesos.Product.Models.Product;
import com.procesos.Product.Service.ProductServiceImp;
import com.procesos.Product.Utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ProductController {

    @Autowired
    private ProductServiceImp productServiceImp;
    @Autowired
    private JWTUtil jwtUtil;

    private Boolean validateToken(String token){
        try{
            if(jwtUtil.getKey(token) != null){
                return true;
            }
            return  false;
        }catch (Exception e){
            return  false;
        }
    }

    @GetMapping(value = "/product/{id}")
    public ResponseEntity findProductById(@PathVariable Long id,
                                          @RequestHeader(value = "Authorization") String token){
        if(!validateToken(token)){return new ResponseEntity<>("Token invalido",HttpStatus.UNAUTHORIZED);}

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
    public ResponseEntity findAllProduct(@RequestHeader(value = "Authorization") String token){
        if(!validateToken(token)){return new ResponseEntity<>("Token invalido",HttpStatus.UNAUTHORIZED);}

        Map response = new HashMap();
        try {
            return new ResponseEntity(productServiceImp.allProduct(), HttpStatus.OK);
        }catch(Exception e){
            response.put("status","404");
            response.put("message","No se encontraron los productos!");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping (value = "/product/{id}/{id_usuario}")
    public ResponseEntity saveVehicle(@RequestHeader(value = "Authorization") String token,@PathVariable Long id,@PathVariable Long id_usuario){
        Map<String, String> response = new HashMap<>();

        try {
            if(!validateToken(token))
            {
                return new ResponseEntity("Token invalido", HttpStatus.UNAUTHORIZED);
            }
            Boolean vehicleResp = productServiceImp.createProduct(id, id_usuario);
            if(vehicleResp){
                response.put("status","201");
                response.put("message", "Producto creado correctamente");
                return new ResponseEntity(response, HttpStatus.CREATED);
            }
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            response.put("status", "400");
            response.put("message", "error creando el producto");
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/product/{id}")

    public ResponseEntity updateProduct(@PathVariable Long id, @RequestBody Product product,
                                        @RequestHeader(value = "Authorization") String token){
        if(!validateToken(token)){return new ResponseEntity<>("Token invalido",HttpStatus.UNAUTHORIZED);}
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

    @DeleteMapping(value = "/product/{id}")
    public ResponseEntity deleteproduct(@PathVariable Long id, @RequestHeader(value = "Authorization") String token){
        Map response = new HashMap();
        try {
            if(!validateToken(token)){
                return new ResponseEntity("Token invalido", HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity(productServiceImp.delete(id), HttpStatus.OK);
        }catch(Exception e){
            response.put("status","404");
            response.put("message","No se encontro el producto!");
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    }

}
