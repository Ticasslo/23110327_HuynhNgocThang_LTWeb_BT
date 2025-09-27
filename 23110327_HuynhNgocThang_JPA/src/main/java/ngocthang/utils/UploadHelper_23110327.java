package ngocthang.utils;

import jakarta.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class UploadHelper_23110327 {

    public static String save(Part filePart) {
        try {
            // Tạo đường dẫn tuyệt đối đến thư mục webapp/uploads_23110327
            String webappPath = System.getProperty("user.dir") + "/src/main/webapp/" + Constant_23110327.DIR;
            Path uploadDir = Paths.get(webappPath);
            Files.createDirectories(uploadDir);
            
            String ext = "";
            String name = filePart.getSubmittedFileName();
            if (name != null && name.lastIndexOf('.') > -1) {
                ext = name.substring(name.lastIndexOf('.'));
            }
            String newName = UUID.randomUUID().toString().replace("-", "") + ext;
            Path target = uploadDir.resolve(newName).normalize();
            
            // Sử dụng Files.copy thay vì Part.write
            Files.copy(filePart.getInputStream(), target);
            return newName;
        } catch (IOException e) {
            throw new RuntimeException("Cannot save upload file", e);
        }
    }
    
    public static boolean delete(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return false;
        }
        String webappPath = System.getProperty("user.dir") + "/src/main/webapp/" + Constant_23110327.DIR;
        Path filePath = Paths.get(webappPath, fileName);
        try {
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.err.println("Error deleting file: " + e.getMessage());
            return false;
        }
    }
}
