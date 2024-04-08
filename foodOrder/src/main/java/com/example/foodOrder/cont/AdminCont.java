package com.example.foodOrder.cont;

import com.example.foodOrder.dto.ResDto;
import com.example.foodOrder.entity.Restraunt;
import com.example.foodOrder.service.admin.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminCont {
    private final AdminService adminService;

    public AdminCont(AdminService adminService) {
        this.adminService = adminService;
    }


//    @PostMapping("/create")
//public ResponseEntity<?> uploadImage(@RequestParam("name") String name, @RequestParam("address") String address, @RequestParam("ownerId") Long ownerId,@RequestParam("img") MultipartFile image) throws IOException {
////        System.out.println(resDto.getAddress());
//        System.out.println(name);
//        System.out.println(address);
//
//        adminService.createRes(name,address,ownerId,image);
//        return ResponseEntity.ok().body("Restraunt added successfully.");
//
//}
    @PostMapping("/create")
    public String addImagePost(@RequestParam("image") MultipartFile file,@RequestParam("name") String name,@RequestParam("address") String address,@RequestParam("ownerId") Long ownerId) throws IOException, SerialException, SQLException {
        System.out.println(file);
        System.out.println(name);
        System.out.println(address);
        System.out.println(ownerId);
        byte[] bytes = file.getBytes();
        Blob blob = new SerialBlob(bytes);
        adminService.createRes(name, address, blob, ownerId);
        return "Success";
    }

    @GetMapping("/get/{restId}")
    public ResponseEntity<ResDto> getRestraunt(@PathVariable Long restId){
        ResDto resDto=adminService.getRes(restId);
        if(resDto==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(resDto);
    }
    @GetMapping("/restraunt/{ownerId}")
    public ResponseEntity<ResDto> getRestrauntByOwner(@PathVariable Long ownerId){
        ResDto resDto=adminService.getResByOwner(ownerId);
        if(resDto==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(resDto);
    }

    @DeleteMapping("/delete/{ownerId}")
    public ResponseEntity<?> deleteByOwnerId(@PathVariable Long ownerId){
        adminService.deleteResByOwner(ownerId);
        return ResponseEntity.ok().body("deleted successfully");
    }
    @DeleteMapping("/restraunt/delete/{restId}")
    public ResponseEntity<?> delete(@PathVariable Long restId){
        adminService.deleteRes(restId);
        Map<String,String> deletemap=new HashMap<>();
        deletemap.put("message","deleted successfully"+restId);
        return ResponseEntity.ok().body(deletemap);
    }
    @GetMapping("/hello")
    public String sayHello(){
        return "Helele";
    }

}
