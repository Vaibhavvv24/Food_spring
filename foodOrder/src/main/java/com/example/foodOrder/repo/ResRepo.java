package com.example.foodOrder.repo;

import com.example.foodOrder.entity.Restraunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResRepo extends JpaRepository<Restraunt,Long> {
}
