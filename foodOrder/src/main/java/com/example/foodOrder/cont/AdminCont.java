package com.example.foodOrder.cont;

import com.example.foodOrder.dto.ResDto;
import com.example.foodOrder.entity.Restraunt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminCont {

@PostMapping("/create")
public ResponseEntity<?> uploadImage(@RequestParam String name, @RequestParam String type, @RequestParam("file") MultipartFile file) {
    try {
        byte[] data = file.getBytes();
        imageService.uploadImage(name, type, data);
        return ResponseEntity.ok().body("Image uploaded successfully.");
    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
    }
}

}
