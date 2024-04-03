package com.example.foodOrder.repo;

import com.example.foodOrder.entity.User;
import com.example.foodOrder.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    User findByRole(Role role);

    Optional<User> findByEmail(String email);
}
