package com.example.foodOrder.repo;

import com.example.foodOrder.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CatRepo extends JpaRepository<Category,Long> {
    List<Category> findALlByRestrauntId(Long restId);

    List<Category> findAllByRestrauntNameContaining(String restName);

    List<Category> findALlByNameContaining(String name);
}
