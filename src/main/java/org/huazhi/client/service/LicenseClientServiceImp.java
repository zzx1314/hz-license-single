package org.huazhi.client.service;

import java.io.File;
import java.security.PrivateKey;

import org.huazhi.client.entity.LicActiviteDto;
import org.huazhi.client.entity.LicReportDto;
import org.huazhi.proj.entity.LicenseProj;
import org.huazhi.proj.repository.LicenseProjRepository;
import org.huazhi.util.AesDecryptorUtil;
import org.huazhi.util.JsonUtil;
import org.huazhi.util.MD5PasswordEncoder;
import org.huazhi.util.PrivateKeyConvUtil;
import org.huazhi.util.R;
import org.huazhi.util.SignDataUtil;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class LicenseClientServiceImp implements LicenseClientService {
    private static final Logger log = Logger.getLogger(LicenseClientServiceImp.class);

    String basePath = "/opt/hz/ota/otacert/temp";
    @Inject
    LicenseProjRepository licenseProjRepository;


    /**
     * 激活
     */
    @Override
    public Object activate(LicActiviteDto licActiviteDto) {
        log.info("Activating license: " + JsonUtil.toJson(licActiviteDto));
        return null;
    }

    /**
     * 下载证书
     */
    @Override
    public Object downloadCer(String cerPath, String activaCode) {
        log.info("Downloading certificate: " + cerPath + " with activation code: " + activaCode);
        return null;
    }

    /**
     * 上报请求
     */
    @Override
    public Object reportServer(LicReportDto licReportDto) {
        log.info("Reporting to server: " + JsonUtil.toJson(licReportDto));
        return null;
    }

    /**
     * 检查用户名和密码
     */
    private R<Integer> checkUserPassword(String username, String password) {
        String decrypt = AesDecryptorUtil.decrypt(password);
        LicenseProj licenseProj = licenseProjRepository.findById(1L);
        // 校验用户名
        if (!username.equals(licenseProj.getUsername())) {
            return R.failed("username or password error");
        }
        // 校验密码
        MD5PasswordEncoder md5PasswordEncoder = new MD5PasswordEncoder();
        String result = md5PasswordEncoder.encode(decrypt);
        if (!result.equals(licenseProj.getPassword())){
            return R.failed("username or password error");
        }
        return R.ok(1);
    }

    /**
     * 获取签名文件
     */
    private File getSignFile(LicenseProj licenseProj) {
        String licenseFileStr = basePath + "/license.json";
        try {
            // 从key里面获取私钥
            PrivateKey privateKey = PrivateKeyConvUtil.getPrivateKey("clientPassword.key", licenseProj.getCerPassword());
            SignDataUtil.signFile(privateKey, licenseFileStr, basePath + "/license.sign");
            return new File(basePath + "/license.sign");
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void updateStatusActivation(String activateCode) {
    }


}
