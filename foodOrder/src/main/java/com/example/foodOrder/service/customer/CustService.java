package com.example.foodOrder.service.customer;

import com.example.foodOrder.dto.UserDto;

public interface CustService {
    UserDto updateUser(UserDto userDto, Long userId);

    UserDto getUser(Long userId);
}
