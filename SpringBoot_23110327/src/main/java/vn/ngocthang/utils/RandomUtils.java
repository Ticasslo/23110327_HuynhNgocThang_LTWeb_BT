package vn.ngocthang.utils;

import java.util.Random;

public class RandomUtils {
    private static final Random random = new Random();
    
    /**
     * Tạo ID ngẫu nhiên cho user
     * @param min giá trị nhỏ nhất
     * @param max giá trị lớn nhất
     * @return ID ngẫu nhiên
     */
    public static int generateUserId(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
    
    /**
     * Tạo chuỗi ngẫu nhiên
     * @param length độ dài chuỗi
     * @return chuỗi ngẫu nhiên
     */
    public static String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
