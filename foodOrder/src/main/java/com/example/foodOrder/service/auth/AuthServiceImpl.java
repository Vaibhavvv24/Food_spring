package com.example.foodOrder.service.auth;

import com.example.foodOrder.dto.SignUpReq;
import com.example.foodOrder.dto.UserDto;
import com.example.foodOrder.entity.Cart;
import com.example.foodOrder.entity.User;
import com.example.foodOrder.enums.Role;
import com.example.foodOrder.repo.CartRepo;
import com.example.foodOrder.repo.UserRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{

    private final PasswordEncoder passwordEncoder;

    private final CartRepo cartRepo;

    private final UserRepo userRepo;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, CartRepo cartRepo, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.cartRepo = cartRepo;
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
        user.setName(sign.getName());
        user.setPhoneNum(sign.getPhoneNum());
        user.setPassword(passwordEncoder.encode(sign.getPassword()));
        Cart cart=new Cart();

        User user1=userRepo.save(user);
        cart.setCustomer(user1);
        cart.setTotalPrice(0);
        cartRepo.save(cart);
        UserDto userDto=new UserDto();
        userDto.setId(user1.getId());
        return userDto;

    }
}
