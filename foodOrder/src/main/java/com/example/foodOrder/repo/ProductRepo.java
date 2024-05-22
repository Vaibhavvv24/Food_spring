package com.example.foodOrder.repo;

import com.example.foodOrder.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    List<Product> findAllByRestrauntId(Long restId);

    List<Product> findAllByRestrauntIdAndCategoryId(Long restId, Long catId);

//    List<Product> findAllByProductNameContaining(String productName);

    List<Product> findAllByRestrauntIdAndProductNameContaining(Long restrauntId,String productNam);

    List<Product> findAllByRestrauntIdAndCategoryIdAndProductNameContaining(Long restrauntId, Long catId, String productName);

    List<Product> findAllByCategoryId(Long catId);
}
