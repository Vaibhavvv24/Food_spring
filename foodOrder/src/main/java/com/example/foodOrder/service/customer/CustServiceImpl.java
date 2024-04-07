package com.example.foodOrder.service.customer;

import com.example.foodOrder.dto.CartItemDto;
import com.example.foodOrder.dto.CategoryDto;
import com.example.foodOrder.dto.ProductDto;
import com.example.foodOrder.dto.UserDto;
import com.example.foodOrder.entity.*;
import com.example.foodOrder.repo.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustServiceImpl implements CustService{


    public static String blobToBase64(Blob blob) {
        try (InputStream inputStream = blob.getBinaryStream()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] imageBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private final UserRepo userRepo;

    private final CatRepo catRepo;

    private final ProductRepo productRepo;

    private final CartRepo cartRepo;

    private final ResRepo repo;

    private final CartItemRepo cartItemRepo;
    private final PasswordEncoder passwordEncoder;

    public CustServiceImpl(UserRepo userRepo, CatRepo catRepo, ProductRepo productRepo, CartRepo cartRepo, ResRepo repo, CartItemRepo cartItemRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.catRepo = catRepo;
        this.productRepo = productRepo;
        this.cartRepo = cartRepo;
        this.repo = repo;
        this.cartItemRepo = cartItemRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto getUser(Long userId) {
        User user=userRepo.findById(userId).get();
        UserDto userDto=new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setPhoneNum(user.getPhoneNum());
        return userDto;
    }

    @Override
    public void deleteUser(Long userId) {
        User user=userRepo.findById(userId).get();
        System.out.println(user);
        userRepo.delete(user);
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto, Long userId) {
        User user=userRepo.findById(userId).get();
        if(userId!=user.getId()){
            System.out.println("not matching");
            return null;
        }
        if( userDto.getName()!=null && !userDto.getName().isEmpty()){
            user.setName(userDto.getName());
        }
        if(userDto.getEmail()!=null && !userDto.getEmail().isEmpty()){
            user.setEmail(userDto.getEmail());
        }
        if(userDto.getPhoneNum()!=null &&  !userDto.getPhoneNum().isEmpty()){
            user.setPhoneNum(userDto.getPhoneNum());
        }
        User updatedUser=userRepo.save(user);
        UserDto newUserDto=new UserDto();
        newUserDto.setName(updatedUser.getName());
        newUserDto.setEmail(updatedUser.getEmail());
        newUserDto.setPhoneNum(updatedUser.getPhoneNum());
        return newUserDto;

    }
    @Override
    public List<CategoryDto> getCats() {
        return catRepo.findAll().stream().map(Category::getCatDto).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getCatbyRes(Long restId) {
        return catRepo.findALlByRestrauntId(restId).stream().map(Category::getCatDto).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getCatbyResName(String restName) {
        return catRepo.findAllByRestrauntNameContaining(restName).stream().map(Category::getCatDto).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getCatbyName(String name) {
        return catRepo.findALlByNameContaining(name).stream().map(Category::getCatDto).collect(Collectors.toList());

    }

    @Override
    public List<ProductDto> getProds(Long restId) {
        return productRepo.findAllByRestrauntId(restId).stream().map(Product::getProductDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProdByCat(Long restId, Long catId) {
        return productRepo.findAllByRestrauntIdAndCategoryId(restId,catId).stream().map(Product::getProductDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductbyNameandRestraunt(String productName,Long restrauntId) {
        return productRepo.findAllByRestrauntIdAndProductNameContaining(restrauntId,productName).stream().map(Product::getProductDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductbyNameandRestrauntandCat(String productName, Long restrauntId, Long catId) {
        return productRepo.findAllByRestrauntIdAndCategoryIdAndProductNameContaining(restrauntId,catId,productName).stream().map(Product::getProductDto).collect(Collectors.toList());
    }

    @Override
    public CartItemDto addToCart(String productName, int price, Blob blob, Long restId, Long catId, Long productId,Long userId) {
        CartItem cartItem=new CartItem();
        Restraunt restraunt=repo.findById(restId).get();
        Category category=catRepo.findById(catId).get();
        Product product=productRepo.findById(productId).get();
        cartItem.setRestraunt(restraunt);
        User user=userRepo.findById(userId).get();
        Cart cart=cartRepo.findByCustomer(user);
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setCategory(category);
        cart.setTotalPrice(cart.getTotalPrice()+price);
        cartRepo.save(cart);
        CartItemDto cartItemDto=new CartItemDto();
        CartItem savedCartItem=cartItemRepo.save(cartItem);
        cartItemDto.setId(savedCartItem.getId());
        return cartItemDto;
    }

    @Override
    public List<CartItemDto> getCart(Long userId) {
        User user=userRepo.findById(userId).get();
        Cart cart=cartRepo.findByCustomer(user);
        List<CartItem> cartItems=cartItemRepo.findAllByCart(cart);
        List<CartItemDto> cartItemDtoList=new ArrayList<>();
        for(CartItem cartItem:cartItems){
            CartItemDto cartItemDto=new CartItemDto();
            cartItemDto.setCartId(cartItem.getCart().getId());
            cartItemDto.setId(cartItem.getId());
            cartItemDto.setProductId(cartItem.getProduct().getId());
            Blob blob=cartItem.getProduct().getImg();
            String base64=blobToBase64(blob);

            cartItemDto.setProductImg(base64);
            cartItemDto.setRestId(cartItem.getRestraunt().getId());
            cartItemDto.setProductPrice(cartItem.getProduct().getPrice());
            cartItemDto.setRestrauntName(cartItem.getRestraunt().getName());
            cartItemDtoList.add(cartItemDto);
        }
        return cartItemDtoList;
    }
}
