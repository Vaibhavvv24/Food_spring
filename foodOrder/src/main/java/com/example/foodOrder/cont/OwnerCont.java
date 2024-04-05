package com.example.foodOrder.cont;

import com.example.foodOrder.dto.CategoryDto;
import com.example.foodOrder.dto.ProductDto;
import com.example.foodOrder.service.owner.OwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/owner")
public class OwnerCont {

    private final OwnerService ownerService;

    public OwnerCont(OwnerService ownerService) {
        this.ownerService = ownerService;
    }


    //    public ResponseEntity<>
    @PostMapping("/category")
    public ResponseEntity<?> postCategory(@RequestParam("name") String name, @RequestParam("img")MultipartFile file,@RequestParam("description") String description,@RequestParam("restId") Long restId) throws IOException, SQLException {
        CategoryDto categoryDto=new CategoryDto();
        byte[] bytes = file.getBytes();
        Blob blob = new SerialBlob(bytes);
        categoryDto=ownerService.createCat(name,description,blob,restId);
        if(categoryDto==null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(categoryDto);
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getAll(@PathVariable Long categoryId){
        CategoryDto categoryDto=ownerService.getCatbyId(categoryId);
        if(categoryDto==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(categoryDto);
    }
    @DeleteMapping("/category/{categoryId}/delete/{restId}")
    public ResponseEntity<?> delete(@PathVariable Long categoryId, @PathVariable Long restId){
        ownerService.deleteById(categoryId,restId);
        return ResponseEntity.ok().body("deleted category");
    }


    //product routes
    @PostMapping("/product")
    public ResponseEntity<?> postProduct(@RequestParam("productName") String productName, @RequestParam("img")MultipartFile file,@RequestParam("price") int price,@RequestParam("restId") Long restId,@RequestParam("catId") Long catId) throws IOException, SQLException {
        ProductDto productDto=new ProductDto();
        byte[] bytes = file.getBytes();
        Blob blob = new SerialBlob(bytes);
        productDto=ownerService.createProd(productName,price,blob,restId,catId);
        if(productDto==null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(productDto);
    }


}
