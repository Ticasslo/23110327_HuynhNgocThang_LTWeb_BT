package ngocthang.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Utility class để xử lý Download File
 */
public class DownloadUtils {
    
    /**
     * Download file với tên file từ parameter
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param paramName tên parameter chứa tên file
     * @throws IOException
     */
    public static void downloadImageFromParam(HttpServletRequest request, HttpServletResponse response, String paramName) 
            throws IOException {
        String fileName = request.getParameter(paramName);
        if (fileName == null || fileName.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu tên file");
            return;
        }
        
        downloadImage(request, response, fileName);
    }
    
    // Helper methods - private
    
    /**
     * Download file ảnh với tên file
     */
    private static void downloadImage(HttpServletRequest request, HttpServletResponse response, String fileName) 
            throws IOException {
        Path filePath = Paths.get(Constant.DIR, fileName);
        
        if (!Files.exists(filePath)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File không tồn tại");
            return;
        }
        
        // Kiểm tra có phải download hay hiển thị
        String downloadParam = request.getParameter("download");
        boolean isDownload = "1".equals(downloadParam);
        
        // Xác định content type dựa trên extension
        String contentType = getImageContentType(fileName);
        response.setContentType(contentType);
        
        // Nếu là download, set header để force download
        if (isDownload) {
            String originalFileName = getOriginalFileName(fileName);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + originalFileName + "\"");
        }
        
        // Set content length
        response.setContentLengthLong(Files.size(filePath));
        
        // Copy file content to response
        Files.copy(filePath, response.getOutputStream());
        response.getOutputStream().flush();
    }
    
    /**
     * Xác định content type dựa trên extension file
     */
    private static String getImageContentType(String fileName) {
        String extension = getFileExtension(fileName).toLowerCase();
        
        return switch (extension) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "webp" -> "image/webp";
            case "bmp" -> "image/bmp";
            case "svg" -> "image/svg+xml";
            default -> "image/jpeg"; // default
        };
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
     * Tạo tên file gốc cho download (loại bỏ timestamp)
     */
    private static String getOriginalFileName(String fileName) {
        if (fileName != null && fileName.contains("/")) {
            String justFileName = fileName.substring(fileName.lastIndexOf("/") + 1);
            String extension = getFileExtension(justFileName);
            return "category_image." + extension; // Tên file có ý nghĩa cho download
        }
        String extension = getFileExtension(fileName);
        return extension.isEmpty() ? "category_image.jpg" : "category_image." + extension;
    }
}
