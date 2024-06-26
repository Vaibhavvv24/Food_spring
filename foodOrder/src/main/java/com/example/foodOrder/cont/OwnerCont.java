package com.example.foodOrder.cont;

import com.example.foodOrder.dto.*;
import com.example.foodOrder.service.owner.OwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/owner")
@CrossOrigin(origins = "http://localhost:5173")
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
    @GetMapping("/categories/{restId}")
    public ResponseEntity<?> getByRes(@PathVariable Long restId){
        List<CategoryDto> categoryDtos=ownerService.getCatbyResOwner(restId);
        if(categoryDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(categoryDtos);

    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getCat(@PathVariable Long categoryId){
        CategoryDto categoryDto=ownerService.getCatbyId(categoryId);
        if(categoryDto==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(categoryDto);
    }
    @DeleteMapping("/category/{categoryId}/delete/{restId}")
    public ResponseEntity<?> delete(@PathVariable Long categoryId, @PathVariable Long restId){
        ownerService.deleteById(categoryId,restId);
        Map<String,String> deletemap=new HashMap<>();
        deletemap.put("message","deleted successfully"+categoryId);
        return ResponseEntity.ok().body(deletemap);
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
    @GetMapping("/productsGet/{catid}/category/{restId}")
    public ResponseEntity<?> getProducts(@PathVariable Long restId,@PathVariable Long catid){
        List<ProductDto> productDtos=ownerService.getProductsByCategory(restId,catid);
        System.out.println(productDtos.size());
        if(productDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(productDtos);
    }
    @GetMapping("/productsGet/{catid}/category/{restId}/search/{searchterm}")
    public ResponseEntity<?> getProductsByName(@PathVariable Long restId,@PathVariable Long catid,@PathVariable String searchterm){
        List<ProductDto> productDtos=ownerService.getProductsByCategoryAndName(restId,catid,searchterm);
        System.out.println(productDtos.size());
        return ResponseEntity.ok().body(productDtos);
    }
    @DeleteMapping("/{restId}/products/delete/{prodId}")
    public ResponseEntity<?> deleteByProdId(@PathVariable Long prodId,@PathVariable Long restId){
        ownerService.deleteByProductId(prodId,restId);
        Map<String,String> deletemap=new HashMap<>();
        deletemap.put("message","deleted successfully"+restId);
        return ResponseEntity.ok().body(deletemap);

    }
    @GetMapping("/orders/{restId}/ownerId/{ownerId}")
    public ResponseEntity<?> getOrders(@PathVariable Long restId,@PathVariable Long ownerId){

        List<OrderItemDto> orderItemDtos=ownerService.getOrders(restId,ownerId);
        System.out.println(orderItemDtos.size());
        Map<String,String> wrongmap=new HashMap<>();

        if(orderItemDtos.isEmpty()) {
            wrongmap.put("message", "you can access only your orders");
            return ResponseEntity.badRequest().body(wrongmap);
        }
        return ResponseEntity.ok().body(orderItemDtos);
    }
    @PostMapping("/orders/{restId}/ownerId/{ownerId}/change")
    public ResponseEntity<?> updateStatus(@PathVariable Long restId, @PathVariable Long ownerId, @RequestBody StatusReq statusReq){
        System.out.println(statusReq.getOrderItemId());
        ownerService.updateStatus(restId,ownerId,statusReq);
        Map<String,String> deletemap=new HashMap<>();
        deletemap.put("message","updated successfully");
       return  ResponseEntity.ok().body(deletemap);
    }



}
