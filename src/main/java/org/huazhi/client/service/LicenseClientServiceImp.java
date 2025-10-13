package org.huazhi.client.service;

import java.io.File;
import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.huazhi.client.entity.LicActiviteDto;
import org.huazhi.client.entity.LicFeatureDto;
import org.huazhi.client.entity.LicFileDto;
import org.huazhi.client.entity.LicReportDto;
import org.huazhi.device.entity.LicenseDevice;
import org.huazhi.device.repository.LicenseDeviceRepository;
import org.huazhi.proj.entity.LicenseProj;
import org.huazhi.proj.repository.LicenseProjRepository;
import org.huazhi.util.AesDecryptorUtil;
import org.huazhi.util.Base64;
import org.huazhi.util.CommonConstants;
import org.huazhi.util.DateUtil;
import org.huazhi.util.FileUtil;
import org.huazhi.util.JsonUtil;
import org.huazhi.util.MD5PasswordEncoder;
import org.huazhi.util.PrivateKeyConvUtil;
import org.huazhi.util.R;
import org.huazhi.util.SignDataUtil;
import org.jboss.logging.Logger;

import io.netty.util.CharsetUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class LicenseClientServiceImp implements LicenseClientService {
    private static final Logger log = Logger.getLogger(LicenseClientServiceImp.class);

    String basePath = "/opt/hz/ota/otacert/temp";
    @Inject
    LicenseProjRepository licenseProjRepository;

    @Inject
    LicenseDeviceRepository licenseDeviceRepository;


    /**
     * 激活
     */
    @Override
    public Object activate(LicActiviteDto licActiviteDto) {
        log.info("Activating license: " + JsonUtil.toJson(licActiviteDto));
        Map<String, String> result = new HashMap<>();
        // 1. 校验用户名和密码
        R<Integer> checkResult = checkUserPassword(licActiviteDto.getUsername(), licActiviteDto.getPassword());
        if (checkResult.getCode() != CommonConstants.SUCCESS) {
            return checkResult;
        }
        log.info("username and password check success");
        // 2. 获取项目信息
        LicenseProj licenseProj = licenseProjRepository.findById(1L);
        if (licenseProj == null) {
            return R.failed("project not found");
        }
        log.info("find proj" + JsonUtil.toJson(licenseProj));
        // 3. 检查授权点数
        R checkAuthNumResult = checkAuthNum(licenseProj, licActiviteDto.getActivateCode());
        if (checkAuthNumResult.getCode() != CommonConstants.SUCCESS) {
            return checkAuthNumResult;
        }
        log.info("auth num check success");
        // 4. 检查证书有效期限
        String endTime = licenseProj.getLiceTime().split(",")[1] + " 23:59:59";
        if (DateUtil.compare(DateUtil.parse(endTime), DateUtil.date()) < 0) {
            return R.failed("license time expire");
        }
        log.info("license time check success");
        // 5. 获取签名文件
        File signFile = getSignFile(licenseProj);
        result.put("signFileBase64Str",  Base64.encode(signFile));
        result.put("dataTime", DateUtil.now());
        // 6. 获取license文件
        File licenseFile = getLicenseString(licActiviteDto);
        result.put("licenseFileBase64Str", Base64.encode(licenseFile));
        // 7. 保存设备信息
        LicenseDevice device = licenseDeviceRepository.find("activationCode", licActiviteDto.getActivateCode()).firstResult();
        int activitionNum = 0;
        if (device != null){
            // 删除之前的设备
            activitionNum = device.getActivationNum() + 1;
            licenseDeviceRepository.deleteById(device.getId());
        }
        // 将请求保存
        LicenseDevice licenseBusDevice = new LicenseDevice();
        licenseBusDevice.setUserId(checkResult.getData());
        licenseBusDevice.setOsVersion(licActiviteDto.getVersion());
        licenseBusDevice.setProcessArch(licActiviteDto.getArch());
        licenseBusDevice.setCerFailureTime(licenseProj.getLiceTime());
        licenseBusDevice.setCommTime(LocalDateTime.now());
        licenseBusDevice.setActivationCode(licActiviteDto.getActivateCode());
        licenseBusDevice.setProductName(licActiviteDto.getProductName());
        licenseBusDevice.setActivationNum(activitionNum);
        licenseBusDevice.setCerStatus("待激活");
        licenseDeviceRepository.persist(licenseBusDevice);
        return result;
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
     * 检查授权点数
     */
    private R checkAuthNum(LicenseProj licenseProj, String activateCode) {
        if (licenseProj.getUseLicNum() == null) {
            licenseProj.setUseLicNum(0);
        }
        // 如果设备已记录，则不进行对授权点数的检查，必须是新的设备 （新的设备才做检查）
        LicenseDevice device =  licenseDeviceRepository.find("activateCode", activateCode).firstResult();
        if (device != null) {
            return R.ok();
        }
        if (licenseProj.getUseLicNum() + 1 > licenseProj.getLiceNum()) {
            return R.failed("auth num not enough");
        }
        return R.ok();
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

    /**
     * 获取licens文件
     */
    private File getLicenseString(LicActiviteDto licActiviteDto) {
        // 获取项目
        LicenseProj proj = licenseProjRepository.findById(1L);
        // 获取证书字符串
        LicFileDto licFileDto = new LicFileDto();
        licFileDto.setProjectCode(proj.getProjCode());
        licFileDto.setLicenseTime(proj.getLiceTime());
        String startTime = proj.getLiceTime().split(",")[0] + " 00:00:00";
        String endTime = proj.getLiceTime().split(",")[1] + " 23:59:59";
        licFileDto.setLicenseTime(startTime + "," + endTime);
        licFileDto.setActivateCode(licActiviteDto.getActivateCode());
        licFileDto.setVersion(licActiviteDto.getVersion());
        licFileDto.setArch(licActiviteDto.getArch());
        licFileDto.setUsername(licActiviteDto.getUsername());
        licFileDto.setProductName(licActiviteDto.getProductName());
        String featuresName = proj.getFeaturesName();
        List<String> features = List.of(featuresName.split(","));
        List<LicFeatureDto> featureDtos = new ArrayList<>();
        for (String feature : features) {
            LicFeatureDto featureDto = new LicFeatureDto();
            featureDto.setName(feature);
            featureDtos.add(featureDto);
        }
        licFileDto.setFeatures(featureDtos);
        String jsonString = JsonUtil.toJson(licFileDto);
        return FileUtil.writeString(jsonString, basePath + "/license.json", CharsetUtil.UTF_8);
    }

    /**
     * 更新设备激活状态
     */
    private void updateStatusActivation(String activateCode) {
         LicenseDevice device =  licenseDeviceRepository.find("activateCode", activateCode).firstResult();
         if (device != null && device.getCerStatus().equals("待激活")) {
            // 需要判断这个设备是否已经激活过，如果已经激活过则不计数
            if (device.getActivationNum() == 0) {
                // 代表没有激活过
                LicenseProj proj = licenseProjRepository.findById(1L);
                if (proj.getUseLicNum() == null){
                    proj.setUseLicNum(1);
                } else {
                    proj.setUseLicNum(proj.getUseLicNum()+ 1);
                }
                licenseProjRepository.updateById(proj);
            }
            device.setCerStatus("已激活");
            device.setActivationTime(LocalDateTime.now());
            licenseDeviceRepository.updateById(device);
        } else if (device != null && device.getCerStatus().equals("待更新授权证书")) {
            device.setCerStatus("已激活");
            device.setActivationTime(LocalDateTime.now());
            licenseDeviceRepository.updateById(device);
        }

    }


}
