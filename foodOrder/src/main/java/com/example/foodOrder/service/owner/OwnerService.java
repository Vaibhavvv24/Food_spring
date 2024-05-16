package com.example.foodOrder.service.owner;

import com.example.foodOrder.dto.CategoryDto;
import com.example.foodOrder.dto.OrderItemDto;
import com.example.foodOrder.dto.ProductDto;
import com.example.foodOrder.dto.StatusReq;

import java.sql.Blob;
import java.util.List;

public interface OwnerService {
    CategoryDto createCat(String name, String description, Blob blob, Long restId);


    CategoryDto getCatbyId(Long categoryId);

    void deleteById(Long categoryId, Long restId);

    ProductDto createProd(String productName, int price, Blob blob, Long restId, Long catId);

    List<ProductDto> getProducts(Long restId);

//    ProductDto getProductbyId(Long productId);

    void deleteByProductId(Long prodId, Long restId);


    List<CategoryDto> getCatbyResOwner(Long restId);

    List<OrderItemDto> getOrders(Long restId,Long ownerId);

    void updateStatus(Long restId, Long ownerId, StatusReq statusReq);
}
