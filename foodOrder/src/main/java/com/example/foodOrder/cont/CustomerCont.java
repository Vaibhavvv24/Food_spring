package com.example.foodOrder.cont;

import com.example.foodOrder.dto.CategoryDto;
import com.example.foodOrder.dto.UserDto;
import com.example.foodOrder.repo.UserRepo;
import com.example.foodOrder.service.UserService;
import com.example.foodOrder.service.customer.CustService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")

public class CustomerCont {
    private final CustService custService;

    public CustomerCont( CustService custService) {
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

}
