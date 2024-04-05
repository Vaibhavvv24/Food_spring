package com.example.foodOrder.service.customer;

import com.example.foodOrder.dto.CategoryDto;
import com.example.foodOrder.dto.UserDto;

import java.util.List;

public interface CustService {
    UserDto updateUser(UserDto userDto, Long userId);

    UserDto getUser(Long userId);

    void deleteUser(Long userId);

    List<CategoryDto> getCats();

    List<CategoryDto> getCatbyRes(Long restId);

    List<CategoryDto> getCatbyResName(String restName);

    List<CategoryDto> getCatbyName(String name);
}
