package ngocthang.utils;

import java.security.SecureRandom;
import java.util.Random;

public class RandomUtils {
    
    private static final Random random = new SecureRandom();
    
    /**
     * Tạo ID ngẫu nhiên cho user (số nguyên dương)
     * @param min giá trị tối thiểu
     * @param max giá trị tối đa
     * @return ID ngẫu nhiên
     */
    public static int generateUserId(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}
