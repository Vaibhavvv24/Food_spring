package com.example.foodOrder.service.owner;

import com.example.foodOrder.dto.CategoryDto;
import com.example.foodOrder.dto.ProductDto;
import com.example.foodOrder.entity.Category;
import com.example.foodOrder.entity.Product;
import com.example.foodOrder.entity.Restraunt;
import com.example.foodOrder.repo.CatRepo;
import com.example.foodOrder.repo.ProductRepo;
import com.example.foodOrder.repo.ResRepo;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private final ProductRepo productRepo;

    public OwnerServiceImpl(CatRepo catRepo, ResRepo repo, ProductRepo productRepo) {
        this.catRepo = catRepo;
        this.repo = repo;
        this.productRepo = productRepo;
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

    @Override
    public CategoryDto getCatbyId(Long CategoryId) {
        Category category=catRepo.findById(CategoryId).get();
        CategoryDto categoryDto=new CategoryDto();
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        Blob blob= category.getImg();
        String base64=blobToBase64(blob);
        categoryDto.setReturnedimage(base64);
        categoryDto.setId(category.getId());
        categoryDto.setResId(category.getRestraunt().getId());
        categoryDto.setRestName(category.getRestraunt().getName());
        return categoryDto;
    }

    @Override
    public void deleteById(Long categoryId, Long restId) {
        Category category=catRepo.findById(categoryId).get();
        if(!Objects.equals(category.getRestraunt().getId(), restId)){
            System.out.println("Not equal");
            return;
        }
        catRepo.delete(category);
    }

    @Override
    public ProductDto createProd(String productName, int price, Blob blob, Long restId, Long catId) {
        ProductDto productDto=new ProductDto();
        Product product=new Product();
        product.setPrice(price);
        product.setProductName(productName);
        Category category=catRepo.findById(catId).get();
        product.setCategory(category);
        Restraunt restraunt=repo.findById(restId).get();
        product.setRestraunt(restraunt);
        product.setImg(blob);
        Product savedProd=productRepo.save(product);
        productDto.setId(savedProd.getId());
        return productDto;
    }

    @Override
    public List<ProductDto> getProducts() {
        return productRepo.findAll().stream().map(Product::getProductDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductbyId(Long productId) {
        Product product=productRepo.findById(productId).get();
        ProductDto productDto=new ProductDto();
        productDto.setPrice(product.getPrice());
        productDto.setId(product.getId());

        productDto.setRestrauntId(product.getRestraunt().getId());
        productDto.setRestrauntName(product.getRestraunt().getName());
        productDto.setCategoryid(product.getCategory().getId());
        productDto.setCategoryname(product.getCategory().getName());
        productDto.setName(product.getProductName());
        Blob blob= product.getImg();
        String base64=blobToBase64(blob);
        productDto.setReturnedimg(base64);
        return productDto;
    }


}
