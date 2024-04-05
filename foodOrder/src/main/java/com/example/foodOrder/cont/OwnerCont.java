package com.example.foodOrder.cont;

import com.example.foodOrder.dto.CategoryDto;
import com.example.foodOrder.service.owner.OwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

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
}
