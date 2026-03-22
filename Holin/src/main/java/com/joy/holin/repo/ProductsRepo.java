package com.joy.holin.repo;

import com.joy.holin.entity.Products;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepo extends JpaRepository<Products, Long> {
    // 要讓前端篩選所以要有這個
    List<Products> findByCategoryId(Long categoryId);
}
