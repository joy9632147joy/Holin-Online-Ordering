package com.joy.holin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joy.holin.entity.Products;
import com.joy.holin.repo.ProductsRepo;

@Service
public class ProductsService {

    @Autowired
    private ProductsRepo productsRepo;

    public List<Products> getProductsByCategory(Long categoryId) {
        return productsRepo.findByCategoryId(categoryId);
    }

}
