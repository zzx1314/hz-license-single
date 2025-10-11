package org.huazhi.client.service;

import java.io.File;

import org.huazhi.client.entity.LicActiviteDto;
import org.huazhi.client.entity.LicReportDto;
import org.huazhi.license.user.entity.SysUser;
import org.huazhi.license.user.repository.SysUserRepository;
import org.huazhi.util.AesDecryptorUtil;
import org.huazhi.util.JsonUtil;
import org.huazhi.util.MD5PasswordEncoder;
import org.huazhi.util.R;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class LicenseClientServiceImp implements LicenseClientService {
    private static final Logger log = Logger.getLogger(LicenseClientServiceImp.class);
    @Inject
    SysUserRepository sysUserRepository;


    @Override
    public Object activate(LicActiviteDto licActiviteDto) {
        log.info("Activating license: " + JsonUtil.toJson(licActiviteDto));
        return null;
    }

    @Override
    public Object downloadCer(String cerPath, String activaCode) {
        log.info("Downloading certificate: " + cerPath + " with activation code: " + activaCode);
        return null;
    }

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
        SysUser sysUser = sysUserRepository.findById(1L);
        // 校验用户名
        if (!username.equals(sysUser.getUsername())) {
            return R.failed("username or password error");
        }
        // 校验密码
        MD5PasswordEncoder md5PasswordEncoder = new MD5PasswordEncoder();
        String result = md5PasswordEncoder.encode(decrypt);
        if (!result.equals(sysUser.getPassword())){
            return R.failed("username or password error");
        }
        return R.ok(1);
    }

    /**
     * 获取签名文件
     */
    private File getSignFile(String path) {
        File file = new File(path);
        return file;
    }

}
