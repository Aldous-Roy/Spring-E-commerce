package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.awt.color.ProfileDataException;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public String serverSetup(){
        return "Spring Boot Server Is Running";
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/products/{prod_id}")
    public ResponseEntity<Product> getOneProduct(@PathVariable Integer prod_id){
        if(productService.getOneProduct(prod_id)!=null)
            return new ResponseEntity<>(productService.getOneProduct(prod_id),HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping(value = "/products", consumes = "multipart/form-data")
    public void addNewProduct(
            @RequestPart("newproduct") String productJson,
            @RequestPart("imageFile") MultipartFile imageFile) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        Product product = mapper.readValue(productJson, Product.class);

        productService.addNewProduct(product, imageFile);
    }
}
