package org.huazhi.util;


import java.security.MessageDigest;

public class MD5PasswordEncoder  {

    private static final String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f"};


    /**
     * 加密
     * @param rawPassword
     * @return
     */
    public String encode(CharSequence rawPassword) {
        rawPassword = md5Hex(rawPassword+ "");
        return "{MD5}" + rawPassword;
    }


    /**
     * MD5 解密
     *
     * @param rawPassword    原密码
     * @param encodePassword 加密后的密码
     * @return
     */
    public boolean matches(CharSequence rawPassword, String encodePassword) {
        // 跳过token的校验
        if (!"th".equals(rawPassword)){
            // 截取加密后的密码
            encodePassword = encodePassword.substring(encodePassword.lastIndexOf("}") + 1);
            return md5Hex(rawPassword + "").equals(encodePassword);
        } else {
            return true;
        }

    }

    /**
     * md5 加密
     *
     * @param rawPassword
     * @return
     */
    private static String md5Hex(String rawPassword) {
        String resultString = null;
        try {
            resultString = String.valueOf(rawPassword);
            MessageDigest md = MessageDigest.getInstance("MD5");

            resultString = byteArrayToString(md.digest(resultString.getBytes()));
        } catch (Exception ex) {
        }
        return resultString;
    }

    public static String byteArrayToString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
}
