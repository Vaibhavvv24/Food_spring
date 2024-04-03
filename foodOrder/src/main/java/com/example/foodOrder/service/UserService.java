package com.example.foodOrder.service;

import com.example.foodOrder.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();

}
