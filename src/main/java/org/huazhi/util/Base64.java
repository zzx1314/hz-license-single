package org.huazhi.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Base64 {
    /**
     * 将文件内容编码为Base64字符串
     *
     * @param file 输入文件
     * @return Base64编码字符串
     */
    public static String encode(File file) {
        return encode(FileUtil.readBytes(file));
    }

    /**
     * 将字节数组编码为Base64字符串
     *
     * @param source 输入字节数组
     * @return Base64编码字符串
     */
    public static String encode(byte[] source) {
        return source == null ? null : java.util.Base64.getEncoder().encodeToString(source);
    }

    /**
     * 将Base64字符串解码并写入到文件中
     *
     * @param base64Str Base64编码字符串
     * @param outputFile 输出文件路径
     * @return 输出的文件对象
     */
    public static File decodeToFile(String base64Str, File outputFile) {
        if (base64Str == null) {
            throw new IllegalArgumentException("Base64 string cannot be null");
        }
        try {
            byte[] data = java.util.Base64.getDecoder().decode(base64Str);
            Files.write(outputFile.toPath(), data);
            return outputFile;
        } catch (IOException e) {
            throw new RuntimeException("Failed to write decoded data to file: " + outputFile, e);
        }
    }
}
