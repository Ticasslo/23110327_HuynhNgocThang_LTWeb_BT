package vn.ngocthang.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class UploadHelper {

    private static final String UPLOAD_DIR = "uploads";

    public static String save(MultipartFile file) {
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
            String ext = "";
            String name = file.getOriginalFilename();
            if (name != null && name.lastIndexOf('.') > -1) {
                ext = name.substring(name.lastIndexOf('.'));
            }
            String newName = UUID.randomUUID().toString().replace("-", "") + ext;
            Path target = Paths.get(UPLOAD_DIR).resolve(newName).normalize();
            file.transferTo(target);
            return newName;
        } catch (IOException e) {
            throw new RuntimeException("Cannot save upload file", e);
        }
    }
    
    public static boolean delete(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return false;
        }
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        try {
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.err.println("Error deleting file: " + e.getMessage());
            return false;
        }
    }
}


