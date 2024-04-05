package com.example.foodOrder.service.owner;

import com.example.foodOrder.dto.CategoryDto;
import com.example.foodOrder.entity.Category;
import com.example.foodOrder.entity.Restraunt;
import com.example.foodOrder.repo.CatRepo;
import com.example.foodOrder.repo.ResRepo;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Base64;

@Service
public class OwnerServiceImpl implements OwnerService{

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

    private final CatRepo catRepo;

    private final ResRepo repo;

    public OwnerServiceImpl(CatRepo catRepo, ResRepo repo) {
        this.catRepo = catRepo;
        this.repo = repo;
    }

    @Override
    public CategoryDto createCat(String name, String description, Blob blob, Long restId) {
        Category category=new Category();
        category.setName(name);
        category.setDescription(description);
        category.setImg(blob);
        Restraunt restraunt=repo.findById(restId).get();

        category.setRestraunt(restraunt);
        Category category1= catRepo.save(category);
        CategoryDto categoryDto=new CategoryDto();
        categoryDto.setId(category1.getId());
        return categoryDto;
    }
}