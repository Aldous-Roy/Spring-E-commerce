package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {


    @Autowired
    private ProductRepo productRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public void addNewProduct(Product newProduct, MultipartFile imageFile) throws IOException {
        newProduct.setImageName(imageFile.getOriginalFilename());
        newProduct.setImageType(imageFile.getContentType());
        newProduct.setImage(imageFile.getBytes());

        productRepo.save(newProduct);
    }

    public Product getOneProduct(Integer prod_Id){
        return productRepo.findById(prod_Id).orElse(null);
    }
}
