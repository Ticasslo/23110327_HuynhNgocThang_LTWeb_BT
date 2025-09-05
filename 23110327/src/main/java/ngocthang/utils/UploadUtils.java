package ngocthang.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

/**
 * Utility class để xử lý Upload File sử dụng Jakarta Servlet API
 */
public class UploadUtils {
    
    /**
     * Lấy giá trị của form field từ multipart request
     * @param request HttpServletRequest
     * @param fieldName tên field
     * @return giá trị field hoặc null nếu không tìm thấy
     * @throws ServletException
     * @throws IOException
     */
    public static String getFieldValue(HttpServletRequest request, String fieldName) 
            throws ServletException, IOException {
        Part part = request.getPart(fieldName);
        if (part != null && part.getSize() > 0) {
            try (InputStream inputStream = part.getInputStream()) {
                return new String(inputStream.readAllBytes(), "UTF-8");
            }
        }
        return null;
    }
    
    /**
     * Upload file và trả về tên file đã lưu
     * @param request HttpServletRequest
     * @param fieldName tên field file
     * @param uploadDir thư mục upload (relative to Constant.DIR)
     * @return tên file đã lưu hoặc null nếu không có file
     * @throws ServletException
     * @throws IOException
     */
    public static String uploadFile(HttpServletRequest request, String fieldName, String uploadDir) 
            throws ServletException, IOException {
        Part filePart = getFilePart(request, fieldName);
        if (filePart != null) {
            String originalFileName = filePart.getSubmittedFileName();
            String fileExtension = getFileExtension(originalFileName);
            String newFileName = generateFileName(fileExtension);
            
            // Tạo đường dẫn đầy đủ
            String fullUploadDir = Constant.DIR + "/" + uploadDir;
            Path uploadPath = Paths.get(fullUploadDir);
            
            // Tạo thư mục nếu chưa tồn tại
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // Lưu file
            Path filePath = uploadPath.resolve(newFileName);
            try (InputStream inputStream = filePart.getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }
            
            return uploadDir + "/" + newFileName;
        }
        return null;
    }
    
    /**
     * Xóa file đã upload
     * @param relativePath đường dẫn tương đối từ Constant.DIR
     * @return true nếu xóa thành công
     */
    public static boolean deleteFile(String relativePath) {
        try {
            Path filePath = Paths.get(Constant.DIR, relativePath);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Validate upload request
     * @param request HttpServletRequest
     * @param fieldName tên field file
     * @param maxSizeInMB kích thước tối đa (MB)
     * @param allowedExtensions extension được phép
     * @return thông báo lỗi hoặc null nếu hợp lệ
     * @throws ServletException
     * @throws IOException
     */
    public static String validateUpload(HttpServletRequest request, String fieldName, 
            int maxSizeInMB, String[] allowedExtensions) throws ServletException, IOException {
        Part filePart = request.getPart(fieldName);
        if (filePart == null || filePart.getSize() == 0) {
            return null; // Không có file, không cần validate
        }
        
        String fileName = filePart.getSubmittedFileName();
        long maxSizeInBytes = maxSizeInMB * 1024 * 1024;
        long fileSizeInMB = filePart.getSize() / (1024 * 1024);
        
        // Kiểm tra tên file
        if (fileName == null || fileName.trim().isEmpty()) {
            return "Tên file không hợp lệ";
        }
        
        // Kiểm tra kích thước
        if (!isValidFileSize(filePart, maxSizeInBytes)) {
            return "File quá lớn (" + fileSizeInMB + "MB). Kích thước tối đa cho phép: " + maxSizeInMB + "MB";
        }
        
        // Kiểm tra extension
        if (!isValidFileExtension(fileName, allowedExtensions)) {
            String currentExt = getFileExtension(fileName);
            return "File định dạng ." + currentExt + " không được hỗ trợ. Chỉ chấp nhận: " + String.join(", ", allowedExtensions);
        }
        
        // Kiểm tra có phải ảnh không (dựa vào MIME type)
        if (!isImageFile(filePart)) {
            return "File được chọn không phải là ảnh hợp lệ. Vui lòng chọn file ảnh (JPG, PNG, GIF, WebP)";
        }
        
        return null; // Hợp lệ
    }
    
    // Helper methods - private
    
    /**
     * Lấy file part từ multipart request
     */
    private static Part getFilePart(HttpServletRequest request, String fieldName) 
            throws ServletException, IOException {
        Part part = request.getPart(fieldName);
        if (part != null && part.getSize() > 0 && part.getSubmittedFileName() != null) {
            return part;
        }
        return null;
    }
    
    /**
     * Kiểm tra file có phải là ảnh không
     */
    private static boolean isImageFile(Part part) {
        String contentType = part.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
    
    /**
     * Kiểm tra kích thước file
     */
    private static boolean isValidFileSize(Part part, long maxSizeInBytes) {
        return part.getSize() <= maxSizeInBytes;
    }
    
    /**
     * Kiểm tra extension file có được phép không
     */
    private static boolean isValidFileExtension(String fileName, String[] allowedExtensions) {
        String extension = getFileExtension(fileName).toLowerCase();
        for (String allowedExt : allowedExtensions) {
            if (extension.equals(allowedExt.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Lấy extension của file
     */
    private static String getFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return "";
    }
    
    /**
     * Tạo tên file mới với timestamp
     */
    private static String generateFileName(String extension) {
        return System.currentTimeMillis() + "." + extension;
    }
} 