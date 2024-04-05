package com.example.foodOrder.service.owner;

import com.example.foodOrder.dto.CategoryDto;

import java.sql.Blob;
import java.util.List;

public interface OwnerService {
    CategoryDto createCat(String name, String description, Blob blob, Long restId);


    CategoryDto getCatbyId(Long categoryId);

    void deleteById(Long categoryId, Long restId);
}
