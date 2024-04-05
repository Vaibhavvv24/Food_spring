package com.example.foodOrder.service.customer;

import com.example.foodOrder.dto.CategoryDto;
import com.example.foodOrder.dto.UserDto;
import com.example.foodOrder.entity.Category;
import com.example.foodOrder.entity.User;
import com.example.foodOrder.repo.CatRepo;
import com.example.foodOrder.repo.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustServiceImpl implements CustService{

    private final UserRepo userRepo;

    private final CatRepo catRepo;

    private final PasswordEncoder passwordEncoder;

    public CustServiceImpl(UserRepo userRepo, CatRepo catRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.catRepo = catRepo;
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
}
