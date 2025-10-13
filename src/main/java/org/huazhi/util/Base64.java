package org.huazhi.util;

import java.io.File;

public class Base64 {
    public static String encode(File file) {
        return encode(FileUtil.readBytes(file));
    }

    public static String encode(byte[] source) {
        return source == null ? null : java.util.Base64.getEncoder().encodeToString(source);
    }
}
