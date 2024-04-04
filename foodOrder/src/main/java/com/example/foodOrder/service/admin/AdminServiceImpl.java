package com.example.foodOrder.service.admin;

import com.example.foodOrder.dto.ResDto;
import com.example.foodOrder.entity.Restraunt;
import com.example.foodOrder.entity.User;
import com.example.foodOrder.enums.Role;
import com.example.foodOrder.repo.ResRepo;
import com.example.foodOrder.repo.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Base64;


@Service
public class AdminServiceImpl implements AdminService{

    public static String blobToBase64(Blob blob) {
        try (InputStream inputStream = blob.getBinaryStream()) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] imageBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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

    @Override
    public ResDto getRes(Long restId) {
        ResDto resDto=new ResDto();
        Restraunt restraunt=repo.findById(restId).get();
        resDto.setName(restraunt.getName());
        resDto.setAddress(restraunt.getAddress());
        resDto.setId(restraunt.getId());
        Blob blob= restraunt.getImg();
        String base64=blobToBase64(blob);
        resDto.setImg(base64);
        resDto.setOwnerId(restraunt.getOwner().getId());
        resDto.setOwnerName(restraunt.getOwner().getName());
        return resDto;
    }

    @Override
    public ResDto getResByOwner(Long ownerId) {
        Restraunt restraunt=repo.findByOwnerId(ownerId);
        ResDto resDto=new ResDto();
        resDto.setName(restraunt.getName());
        resDto.setAddress(restraunt.getAddress());
        resDto.setId(restraunt.getId());
        Blob blob= restraunt.getImg();
        String base64=blobToBase64(blob);
        resDto.setImg(base64);
        resDto.setOwnerId(restraunt.getOwner().getId());
        resDto.setOwnerName(restraunt.getOwner().getName());
        return resDto;
    }

    @Override
    public void deleteResByOwner(Long ownerId) {
        Restraunt restraunt=repo.findByOwnerId(ownerId);
        repo.delete(restraunt);
    }

    @Override
    public void deleteRes(Long restId) {
        Restraunt restraunt=repo.findById(restId).get();
        repo.delete(restraunt);
    }
}
