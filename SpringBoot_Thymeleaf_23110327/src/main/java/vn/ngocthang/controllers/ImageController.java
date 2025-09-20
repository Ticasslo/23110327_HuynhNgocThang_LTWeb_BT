package vn.ngocthang.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ImageController {

    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage(@RequestParam String fname) {
        try {
            // Đường dẫn đến thư mục uploads
            Path imagePath = Paths.get("uploads", fname);
            
            // Kiểm tra file có tồn tại không
            if (!Files.exists(imagePath)) {
                return ResponseEntity.notFound().build();
            }
            
            // Đọc file
            byte[] imageBytes = Files.readAllBytes(imagePath);
            
            // Xác định content type
            String contentType = Files.probeContentType(imagePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            
            // Trả về response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentLength(imageBytes.length);
            
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}