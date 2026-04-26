package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getProductImageById(@PathVariable Integer id){
        Product product =productService.getOneProduct(id);
        byte[] imageFile =product.getImage();
        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }

    @PutMapping(value = "/product/{prod_id}", consumes = "multipart/form-data")
    public ResponseEntity<String> updateProduct(
            @PathVariable Integer prod_id,
            @RequestPart("newproduct") String productJson,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            Product product = mapper.readValue(productJson, Product.class);

            productService.updateProduct(prod_id, product, imageFile);

            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>("Error updating product", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/product/{prod_id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer prod_id){
        try{
            return new ResponseEntity<>(productService.deleteProduct(prod_id),HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>("Error in deleing the product",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/product/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String searchWord){
        List<Product> product =productService.searchProduct(searchWord);
        return new  ResponseEntity<>(product,HttpStatus.OK);
    }
}
