package vn.ngocthang.controllers;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ImageController {

    @GetMapping("/image")
    public ResponseEntity<Resource> getImage(@RequestParam String fname) {
        try {
            Path filePath = Paths.get("uploads", fname).normalize();
            File file = filePath.toFile();
            
            if (!file.exists() || !file.isFile()) {
                return ResponseEntity.notFound().build();
            }
            
            Resource resource = new FileSystemResource(file);
            
            // Xác định content type
            String contentType = "application/octet-stream";
            if (fname.toLowerCase().endsWith(".jpg") || fname.toLowerCase().endsWith(".jpeg")) {
                contentType = "image/jpeg";
            } else if (fname.toLowerCase().endsWith(".png")) {
                contentType = "image/png";
            } else if (fname.toLowerCase().endsWith(".gif")) {
                contentType = "image/gif";
            }
            
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CACHE_CONTROL, "max-age=3600")
                    .body(resource);
                    
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
