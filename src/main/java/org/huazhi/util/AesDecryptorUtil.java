package org.huazhi.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class AesDecryptorUtil {
    private static String keyStr = "461a5ffd9df79171";
    private static String ivStr = "358e9e0177d84eaa";

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static String decrypt(String hexCiphertext) {
        try {
            // 将十六进制字符串转换为字节数组
            byte[] ciphertext = hexStringToByteArray(hexCiphertext);

            // 密钥和IV
            byte[] key = keyStr.getBytes(StandardCharsets.UTF_8);
            byte[] iv = ivStr.getBytes(StandardCharsets.UTF_8);

            // 创建密钥和IV参数
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            // 创建Cipher对象
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            // 解密
            byte[] decrypted = cipher.doFinal(ciphertext);

            // 输出解密后的明文
            System.out.println("Decrypted text: " + new String(decrypted, StandardCharsets.UTF_8));
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
