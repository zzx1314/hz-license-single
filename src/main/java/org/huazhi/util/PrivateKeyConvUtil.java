package org.huazhi.util;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.bc.BcPEMDecryptorProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.operator.InputDecryptorProvider;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;
import org.bouncycastle.pkcs.PKCSException;
import org.bouncycastle.pkcs.jcajce.JcePKCSPBEInputDecryptorProviderBuilder;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateException;

/**
 * pem的私钥转化成java私钥
 */
public class PrivateKeyConvUtil {

    public static PrivateKey getPrivateKey(String privateKeyPath, String privateKeyPassword) throws IOException, PKCSException, CertificateException {
        Security.addProvider(new BouncyCastleProvider());
        char[] password = privateKeyPassword.toCharArray();

        String fileStr = FileUtil.readString(privateKeyPath, StandardCharsets.UTF_8);

        PrivateKey rsaPrivateKey = stringToPrivateKey(fileStr, password);

        return rsaPrivateKey;
    }

    static public PrivateKey stringToPrivateKey(String s, char[] password)
            throws IOException, PKCSException {
        PrivateKeyInfo pki;
        try (PEMParser pemParser = new PEMParser(new StringReader(s))) {
            Object o = pemParser.readObject();
            if (o instanceof PKCS8EncryptedPrivateKeyInfo) { // encrypted private key in pkcs8-format
                System.out.println("key in pkcs8 encoding");
                PKCS8EncryptedPrivateKeyInfo epki = (PKCS8EncryptedPrivateKeyInfo) o;
                JcePKCSPBEInputDecryptorProviderBuilder builder =
                        new JcePKCSPBEInputDecryptorProviderBuilder().setProvider("BC");
                InputDecryptorProvider idp = builder.build(password);
                pki = epki.decryptPrivateKeyInfo(idp);
            } else if (o instanceof PEMEncryptedKeyPair) { // encrypted private key in pkcs1-format
                System.out.println("key in pkcs1 encoding");
                PEMEncryptedKeyPair epki = (PEMEncryptedKeyPair) o;
                PEMKeyPair pkp = epki.decryptKeyPair(new BcPEMDecryptorProvider(password));
                pki = pkp.getPrivateKeyInfo();
            } else if (o instanceof PEMKeyPair) { // unencrypted private key
                System.out.println("key unencrypted");
                PEMKeyPair pkp = (PEMKeyPair) o;
                pki = pkp.getPrivateKeyInfo();
            } else {
                throw new PKCSException("Invalid encrypted private key class: " + o.getClass().getName());
            }
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
            return converter.getPrivateKey(pki);
        }
    }
}
