package com.example.foodOrder.service.auth;

import com.example.foodOrder.dto.SignUpReq;
import com.example.foodOrder.dto.UserDto;

public interface AuthService {
    UserDto createuser(SignUpReq sign);
}
