package vn.ngocthang.utils;

import java.util.Random;

public class RandomUtils {
    private static final Random random = new Random();
    
    public static int getRandomId() {
        // Tạo ID ngẫu nhiên từ 100000 đến 999999
        return 100000 + random.nextInt(900000);
    }
    
    public static String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
