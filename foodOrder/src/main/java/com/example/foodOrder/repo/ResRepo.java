package com.example.foodOrder.repo;

import com.example.foodOrder.dto.ResDto;
import com.example.foodOrder.entity.Restraunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ResRepo extends JpaRepository<Restraunt,Long> {
    Restraunt findByOwnerId(Long ownerId);

    List<Restraunt> findAllByNameContaining(String name);
}
