package com.example.foodOrder.service.auth;

import com.example.foodOrder.dto.SignUpReq;
import com.example.foodOrder.dto.UserDto;
import com.example.foodOrder.entity.User;
import com.example.foodOrder.enums.Role;
import com.example.foodOrder.repo.UserRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class AuthServiceImpl implements AuthService{

    private final PasswordEncoder passwordEncoder;

    private final UserRepo userRepo;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    @PostConstruct
    public void createAdmin(){
        User admin=userRepo.findByRole(Role.ADMIN);
        if(admin==null){
            User user=new User();
            user.setEmail("admin@admin.com");
            user.setName("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRole(Role.ADMIN);
            userRepo.save(user);
        }
    }

    @Override
    public UserDto createuser(SignUpReq sign) {
        User user=new User();
        user.setEmail(sign.getEmail());
        user.setRole(Role.CUSTOMER);
        user.setName(sign.getPassword());
        user.setPhoneNum(sign.getPhoneNum());
        user.setPassword(passwordEncoder.encode(sign.getPassword()));
        User user1=userRepo.save(user);
        UserDto userDto=new UserDto();
        userDto.setId(user1.getId());
        return userDto;

    }
}
