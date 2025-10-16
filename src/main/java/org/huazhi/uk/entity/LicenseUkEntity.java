package org.huazhi.uk.entity;

import lombok.Data;

@Data
public class LicenseUkEntity {
    // 私钥文件
    private String privateKey;

    // 公钥文件
    private String publicKey;

    // 项目字符串
    private String projString;
}
