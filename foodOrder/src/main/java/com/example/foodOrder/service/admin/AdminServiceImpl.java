package com.example.foodOrder.service.admin;

import com.example.foodOrder.dto.ResDto;
import com.example.foodOrder.entity.Restraunt;
import com.example.foodOrder.entity.User;
import com.example.foodOrder.enums.Role;
import com.example.foodOrder.repo.ResRepo;
import com.example.foodOrder.repo.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;


@Service
public class AdminServiceImpl implements AdminService{

    private final UserRepo userRepo;

    private final ResRepo repo;
    public AdminServiceImpl(UserRepo userRepo, ResRepo repo) {
        this.userRepo = userRepo;
        this.repo = repo;
    }

    @Override
    public void createRes(String name, String address, Blob image, Long ownerId) throws IOException {
        Restraunt restraunt=new Restraunt();
        restraunt.setName(name);
        restraunt.setAddress(address);
        restraunt.setImg(image);
        User user= userRepo.findById(ownerId).get();
        restraunt.setOwner(user);
        user.setRole(Role.OWNER);
        repo.save(restraunt);

    }
}
