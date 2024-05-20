package com.example.foodOrder.cont;

import com.example.foodOrder.dto.*;
import com.example.foodOrder.entity.Cart;
import com.example.foodOrder.entity.User;
import com.example.foodOrder.repo.CartRepo;
import com.example.foodOrder.repo.UserRepo;
import com.example.foodOrder.service.UserService;
import com.example.foodOrder.service.customer.CustService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerCont {
    private final CustService custService;



    public CustomerCont(CustService custService) {
        this.custService = custService;

    }

    @GetMapping("/{userId}")            //done
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId){
        UserDto userDto=custService.getUser(userId);
        if(userDto==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userDto);
    }
    @PutMapping("/update/{userId}")   //done
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId,@RequestBody UserDto userDto){
        UserDto userDto1=custService.updateUser(userDto,userId);
        if(userDto==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userDto1);

    }
    @DeleteMapping("/delete/{userId}")  //done
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        custService.deleteUser(userId);
        Map<String,String> deletemap=new HashMap<>();
        deletemap.put("message","deleted successfully"+userId);
        return ResponseEntity.ok().body(deletemap);
    }
    @GetMapping("/restraunts")  //done
    public ResponseEntity<?> getAllRes(){
        List<ResDto> resDtos=custService.getRes();
        if(resDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(resDtos);
    }
    @GetMapping("/categories")   //done
    public ResponseEntity<?> getAll(){
        List<CategoryDto> categoryDtos=custService.getCats();
        if(categoryDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(categoryDtos);
    }
    @GetMapping("/categories/{restId}")   //done
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
    @GetMapping("/categories/search/restraunt/{name}")   //done
    public ResponseEntity<?> getByName(@PathVariable String name){
        List<CategoryDto> categoryDtos=custService.getCatbyName(name);
        if(categoryDtos.isEmpty()) {
            ArrayList arrayList=new ArrayList<>();
            return ResponseEntity.ok().body(arrayList);
        }
        return ResponseEntity.ok().body(categoryDtos);

    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId){
        ProductDto productDto=custService.getProductbyId(productId);
        System.out.println(productDto);
        if(productDto==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(productDto);
    }
    @GetMapping("/products/{restId}")   //done
    public ResponseEntity<?> getProducts(@PathVariable Long restId){
        List<ProductDto> productDtos=custService.getProds(restId);
        System.out.println(productDtos.size());
        if(productDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(productDtos);
    }
    @GetMapping("/category/{catId}/products/{restId}")          //done
    public ResponseEntity<?> getProductsByCat(@PathVariable Long restId,@PathVariable Long catId){
        List<ProductDto> productDtos=custService.getProdByCat(restId,catId);
        System.out.println(productDtos.size());
        if(productDtos.isEmpty()) {
            ArrayList arrayList=new ArrayList<>();
            return ResponseEntity.ok().body(arrayList);
        }
        return ResponseEntity.ok().body(productDtos);
    }
    @GetMapping("/products/search/{restrauntId}/restraunt/{productName}")  //done
    public ResponseEntity<?> getProductsbyName(@PathVariable String productName,@PathVariable Long restrauntId) {
        List<ProductDto> productDtos = custService.getProductbyNameandRestraunt(productName,restrauntId);
        System.out.println(productDtos.size());
        if (productDtos.isEmpty()) {
            ArrayList arrayList=new ArrayList<>();
            return ResponseEntity.ok().body(arrayList);
        }
        return ResponseEntity.ok().body(productDtos);

    }
    @GetMapping("/products/search/{restrauntId}/restraunt/{catId}/category/{productName}")      //done
    public ResponseEntity<?> getProductsbyNameandCat(@PathVariable String productName,@PathVariable Long restrauntId,@PathVariable Long catId) {
        List<ProductDto> productDtos = custService.getProductbyNameandRestrauntandCat(productName,restrauntId,catId);
        System.out.println(productDtos.size());
        if (productDtos.isEmpty()) {
            ArrayList arrayList=new ArrayList<>();
            return ResponseEntity.ok().body(arrayList);
        }
        return ResponseEntity.ok().body(productDtos);

    }

    //cart operations

    @PostMapping("/products/{userId}/add/{productId}")
    public ResponseEntity<?> addToCart(@RequestBody CartRequest cartRequest,@PathVariable Long userId,@PathVariable Long productId) throws IOException, SQLException {
        System.out.println(productId);
        CartItemDto cartItemDto;
//        byte[] bytes = file.getBytes();
//        Blob blob = new SerialBlob(bytes);
        cartItemDto = custService.addToCart(cartRequest, productId,userId);
        if (cartItemDto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(cartItemDto);
    }
    @GetMapping("/cart/{userId}")   //done
    public ResponseEntity<?> getCart(@PathVariable Long userId){
        List<CartItemDto> cartItemDtoList=custService.getCart(userId);
        if(cartItemDtoList.isEmpty()){
            Map<String,String> map=new HashMap<>();
            map.put("message","Your cart is empty");
            return ResponseEntity.ok().body(map);
        }
        return ResponseEntity.ok().body(cartItemDtoList);
    }
    @PutMapping("/cart/{userId}/update/{cartItemId}")  //done
    public ResponseEntity<?> updateCart(@PathVariable Long userId,@PathVariable Long cartItemId){
        custService.updateCart(userId,cartItemId);
        Map<String,String> msg=new HashMap<>();
        msg.put("Message","Your cart is empty");
        return ResponseEntity.ok().body(msg);
    }
    @DeleteMapping("/cart/{userId}/delete")   //done
    public ResponseEntity<?> deleteCart(@PathVariable Long userId){
        custService.clearCart(userId);
        Map<String,String> msg=new HashMap<>();
        msg.put("Message","Your cart is empty");
        return ResponseEntity.ok().body(msg);
    }

    //order of cust
    @PostMapping("/order/{userId}/restraunt/{restrauntId}/orders/{total}")
    public ResponseEntity<?> postOrder(@PathVariable Long userId,@PathVariable Long restrauntId,@PathVariable int total){
       OrderItemDto orderItemDto;
        orderItemDto = custService.addOrder(userId,restrauntId,total);
        if (orderItemDto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(orderItemDto);
    }
    @GetMapping("/order/{userId}")
    public ResponseEntity<?> getOrders(@PathVariable Long userId){
        List<OrderItemDto> orderItemDtos=custService.getOrders(userId);
        if(orderItemDtos.isEmpty()){
            Map<String,String> map=new HashMap<>();
            map.put("message","Your past orders are none");
            return ResponseEntity.badRequest().body(map);
        }
        return ResponseEntity.ok().body(orderItemDtos);
    }
    @PostMapping("/contact/{userId}")
    public ResponseEntity<?> sendEmail(@PathVariable Long userId,@RequestBody ContactReq contactReq){

        custService.sendEmail(userId,contactReq.getEmail(),contactReq.getMessage(),contactReq.getSubject());
        Map<String,String> donemap=new HashMap<>();
        donemap.put("message","Mail Sent Successfully to admins from "+userId);
        return ResponseEntity.ok().body(donemap);
    }
    @PostMapping("/order/payment/{orderId}")
    public ResponseEntity<PaymentResponse> payment(@PathVariable Long orderId,@RequestHeader("Authorization") String jwt) throws RazorpayException {
        PaymentResponse paymentResponse= custService.createPayment(orderId,jwt);
        return ResponseEntity.ok().body(paymentResponse);

    }

}
