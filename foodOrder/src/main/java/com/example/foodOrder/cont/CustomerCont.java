package com.example.foodOrder.cont;

import com.example.foodOrder.dto.UserDto;
import com.example.foodOrder.repo.UserRepo;
import com.example.foodOrder.service.UserService;
import com.example.foodOrder.service.customer.CustService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
