package com.example.foodOrder.service.admin;

import com.example.foodOrder.dto.ResDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;

public interface AdminService {
    void createRes(String name, String address, Blob blob, Long ownerId) throws IOException;

    ResDto getRes(Long restId);

    ResDto getResByOwner(Long ownerId);

    void deleteResByOwner(Long ownerId);

    void deleteRes(Long restId);
}
