package org.huazhi.util;


import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

public class SignDataUtil {
    private static byte[] signData(byte[] data, PrivateKey privateKey) throws Exception {
        // 创建Signature对象
        Signature signature = Signature.getInstance("SHA256withRSA");
        // 初始化Signature对象
        signature.initSign(privateKey);
        // 更新要签名的数据
        signature.update(data);
        // 签名
        return signature.sign();
    }

    public static void signFile(PrivateKey privateKey, String inputFilePath, String signFilePath) throws Exception {
        // 读取输入文件
        byte[] data = FileUtil.readBytes(inputFilePath);
        // 签名
        byte[] signature = signData(data, privateKey);

        // 将签名写入输出文件
        FileUtil.writeBytes(signature, signFilePath);
    }

    /**
     * 文件签名
     */
    public static void signFile(String privateKeyPath, String inputFilePath, String signFilePath) throws Exception {
        // 读取私钥文件
        byte[] privateKeyBytes = FileUtil.readBytes(privateKeyPath);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        // 读取输入文件
        byte[] data = FileUtil.readBytes(inputFilePath);

        // 签名
        byte[] signature = signData(data, privateKey);

        // 将签名写入输出文件
        FileUtil.writeBytes(signature, signFilePath);
    }
}
