package com.example.foodOrder.cont;

import com.example.foodOrder.dto.CartItemDto;
import com.example.foodOrder.dto.CategoryDto;
import com.example.foodOrder.dto.ProductDto;
import com.example.foodOrder.dto.UserDto;
import com.example.foodOrder.entity.Cart;
import com.example.foodOrder.entity.User;
import com.example.foodOrder.repo.CartRepo;
import com.example.foodOrder.repo.UserRepo;
import com.example.foodOrder.service.UserService;
import com.example.foodOrder.service.customer.CustService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")

public class CustomerCont {
    private final CustService custService;


    public CustomerCont(CustService custService) {
        this.custService = custService;

    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId){
        UserDto userDto=custService.getUser(userId);
        if(userDto==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userDto);
    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId,@RequestBody UserDto userDto){
        UserDto userDto1=custService.updateUser(userDto,userId);
        if(userDto==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userDto);

    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        custService.deleteUser(userId);
        return ResponseEntity.ok().body("User deleted Successfully");
    }
    @GetMapping("/categories")
    public ResponseEntity<?> getAll(){
        List<CategoryDto> categoryDtos=custService.getCats();
        if(categoryDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(categoryDtos);
    }
    @GetMapping("/categories/{restId}")
    public ResponseEntity<?> getByRes(@PathVariable Long restId){
        List<CategoryDto> categoryDtos=custService.getCatbyRes(restId);
        if(categoryDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(categoryDtos);

    }
    @GetMapping("/categories/search/{restName}")
    public ResponseEntity<?> getByResName(@PathVariable String restName){
        List<CategoryDto> categoryDtos=custService.getCatbyResName(restName);
        if(categoryDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(categoryDtos);

    }
    @GetMapping("/categories/search/restraunt/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name){
        List<CategoryDto> categoryDtos=custService.getCatbyName(name);
        if(categoryDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(categoryDtos);

    }
    @GetMapping("/products/{restId}")
    public ResponseEntity<?> getProducts(@PathVariable Long restId){
        List<ProductDto> productDtos=custService.getProds(restId);
        System.out.println(productDtos.size());
        if(productDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(productDtos);
    }
    @GetMapping("/category/{catId}/products/{restId}")
    public ResponseEntity<?> getProductsByCat(@PathVariable Long restId,@PathVariable Long catId){
        List<ProductDto> productDtos=custService.getProdByCat(restId,catId);
        System.out.println(productDtos.size());
        if(productDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(productDtos);
    }
    @GetMapping("/products/search/{restrauntId}/restraunt/{productName}")
    public ResponseEntity<?> getProductsbyName(@PathVariable String productName,@PathVariable Long restrauntId) {
        List<ProductDto> productDtos = custService.getProductbyNameandRestraunt(productName,restrauntId);
        System.out.println(productDtos.size());
        if (productDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(productDtos);

    }
    @GetMapping("/products/search/{restrauntId}/restraunt/{catId}/category/{productName}")
    public ResponseEntity<?> getProductsbyNameandCat(@PathVariable String productName,@PathVariable Long restrauntId,@PathVariable Long catId) {
        List<ProductDto> productDtos = custService.getProductbyNameandRestrauntandCat(productName,restrauntId,catId);
        System.out.println(productDtos.size());
        if (productDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(productDtos);

    }

    //cart operations

    @PostMapping("/products/{userId}/add/{productId}")
    public ResponseEntity<?> addToCart(@RequestParam("productName") String productName, @RequestParam("img") MultipartFile file, @RequestParam("price") int price, @RequestParam("restId") Long restId,@RequestParam("catId") Long catId ,@PathVariable Long productId,@PathVariable Long userId) throws IOException, SQLException {

        CartItemDto cartItemDto;
        byte[] bytes = file.getBytes();
        Blob blob = new SerialBlob(bytes);
        cartItemDto = custService.addToCart(productName, price, blob, restId, catId, productId,userId);
        if (cartItemDto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(cartItemDto);
    }
    @GetMapping("/cart/{userId}")
    public ResponseEntity<?> getCart(@PathVariable Long userId){
        List<CartItemDto> cartItemDtoList=custService.getCart(userId);
        if(cartItemDtoList.isEmpty()){
            Map<String,String> map=new HashMap<>();
            map.put("message","Your cart is empty");
            return ResponseEntity.badRequest().body(map);
        }
        return ResponseEntity.ok().body(cartItemDtoList);
    }
    @PutMapping("/cart/{userId}/update/{cartItemId}")
    public ResponseEntity<?> updateCart(@PathVariable Long userId,@PathVariable Long cartItemId){
        custService.updateCart(userId,cartItemId);
        return ResponseEntity.ok().body("Updated cart");
    }
    @DeleteMapping("/cart/{userId}/delete")
    public ResponseEntity<?> deleteCart(@PathVariable Long userId){
        custService.clearCart(userId);
        Map<String,String> msg=new HashMap<>();
        msg.put("Message","Your cart is empty");
        return ResponseEntity.ok().body(msg);
    }


}
