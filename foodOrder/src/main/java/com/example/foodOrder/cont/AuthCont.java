package com.example.foodOrder.cont;

import com.example.foodOrder.dto.SignUpReq;
import com.example.foodOrder.dto.UserDto;
import com.example.foodOrder.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AuthCont {
    private final AuthService authService;

    public AuthCont(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping
    public ResponseEntity<UserDto> signup(@RequestBody SignUpReq sign){
        UserDto userDto=authService.createuser(sign);
        if(userDto==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userDto);


    }
}
