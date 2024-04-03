package com.example.foodOrder.cont;

import com.example.foodOrder.dto.SignUpReq;
import com.example.foodOrder.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AuthCont {


    @PostMapping
    public ResponseEntity<UserDto> signup(@RequestBody SignUpReq sign){
        UserDto userDto=


    }
}
