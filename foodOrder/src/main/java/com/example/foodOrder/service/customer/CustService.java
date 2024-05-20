package com.example.foodOrder.service.customer;

import com.example.foodOrder.dto.*;
import com.razorpay.RazorpayException;

import java.sql.Blob;
import java.util.List;

public interface CustService {
    UserDto updateUser(UserDto userDto, Long userId);

    UserDto getUser(Long userId);

    void deleteUser(Long userId);

    List<CategoryDto> getCats();

    List<CategoryDto> getCatbyRes(Long restId);

    List<CategoryDto> getCatbyResName(String restName);

    List<CategoryDto> getCatbyName(String name);

    List<ProductDto> getProds(Long restId);

    List<ProductDto> getProdByCat(Long restId, Long catId);

    List<ProductDto> getProductbyNameandRestraunt(String productName,Long restrauntId);

    List<ProductDto> getProductbyNameandRestrauntandCat(String productName, Long restrauntId, Long catId);

    CartItemDto addToCart(CartRequest cartRequest,Long productId,Long userId);

    List<CartItemDto> getCart(Long userId);

    void updateCart(Long userId, Long cartItemId);

    void clearCart(Long userId);

    List<ResDto> getRes();

    ProductDto getProductbyId(Long id);

    OrderItemDto addOrder(Long userId, Long restrauntId,int total);

    List<OrderItemDto> getOrders(Long userId);

    void sendEmail(Long userId, String email, String message, String subject);
    PaymentResponse createPayment(Long orderId, String jwt) throws RazorpayException;
}
