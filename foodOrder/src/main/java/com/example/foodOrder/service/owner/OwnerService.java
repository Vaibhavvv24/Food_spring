package com.example.foodOrder.service.owner;

import com.example.foodOrder.dto.CategoryDto;

import java.sql.Blob;

public interface OwnerService {
    CategoryDto createCat(String name, String description, Blob blob, Long restId);
}
