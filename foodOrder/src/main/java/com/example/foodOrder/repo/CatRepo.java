package com.example.foodOrder.repo;

import com.example.foodOrder.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRepo extends JpaRepository<Category,Long> {
}
