package com.joy.holin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joy.holin.service.ProductsService;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getByCategory(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(productsService.getProductsByCategory(categoryId));
    }

}
