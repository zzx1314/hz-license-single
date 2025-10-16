package org.huazhi.uk.service;

import java.io.File;

import org.huazhi.uk.entity.LicenseUkEntity;
import org.huazhi.util.R;

import org.huazhi.util.Base64;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LicenseUkService {
    String basePath = "/opt/hz/ota/otacert/temp";

    public Object importFile(LicenseUkEntity licenseUkEntity) {
        // 保存私钥
        Base64.decodeToFile(licenseUkEntity.getPrivateKey(), new File(basePath + "/clientPassword.key"));
        // 保存公钥
        Base64.decodeToFile(licenseUkEntity.getPublicKey(), new File(basePath + "/client_pub.key"));
        // 保存项目字符串
        Base64.decodeToFile(licenseUkEntity.getProjString(), new File(basePath + "/projString.txt"));
        return R.ok();
    }
}
