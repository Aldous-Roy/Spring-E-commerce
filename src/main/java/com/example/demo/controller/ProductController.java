package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.color.ProfileDataException;
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
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/products/{prod_id}")
    public Product getOneProduct(@PathVariable Integer prod_id){
        return productService.getOneProduct(prod_id);
    }
    @PostMapping("/products")
    public void addNewProduct(@RequestBody Product newproduct){
         productService.addNewProduct(newproduct);
    }
}
