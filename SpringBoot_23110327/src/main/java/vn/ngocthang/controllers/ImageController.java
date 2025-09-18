package vn.ngocthang.controllers;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ImageController {

    public static final String UPLOAD_DIR = "uploads"; // thư mục lưu ảnh bên cạnh thư mục chạy app

    @GetMapping("/image")
    public ResponseEntity<Resource> image(@RequestParam("fname") String fname) {
        Path file = Paths.get(UPLOAD_DIR).resolve(fname).normalize();
        File f = file.toFile();
        if (!f.exists() || !f.isFile()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Resource res = new FileSystemResource(f);
        String contentType = MediaType.IMAGE_JPEG_VALUE;
        if (fname.toLowerCase().endsWith(".png")) contentType = MediaType.IMAGE_PNG_VALUE;
        else if (fname.toLowerCase().endsWith(".gif")) contentType = MediaType.IMAGE_GIF_VALUE;
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + f.getName())
                .contentType(MediaType.parseMediaType(contentType))
                .body(res);
    }
}


