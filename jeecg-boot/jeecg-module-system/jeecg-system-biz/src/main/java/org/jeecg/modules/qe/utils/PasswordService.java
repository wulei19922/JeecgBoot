package org.jeecg.modules.qe.utils;

// 使用 bcrypt 实现（需引入 jBCrypt 库）
import org.mindrot.jbcrypt.BCrypt;

public class PasswordService {
    // 生成哈希密码（自动包含盐值）
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // 验证密码
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}