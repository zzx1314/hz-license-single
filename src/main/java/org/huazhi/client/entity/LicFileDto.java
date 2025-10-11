package org.huazhi.client.entity;

import lombok.Data;

import java.util.List;


/**
 * license文件
 */
@Data
public class LicFileDto {
    /**
     * 用户名
     */
    private String username;
    /**
     * os版本
     */
    private String version;
    /**
     * 架构
     */
    private String arch;
    /**
     * 激活码
     */
    private String activateCode;
    /**
     * 项目编码
     */
    private String projectCode;

    /**
     * 特性列表
     */
    private List<LicFeatureDto> features;

    /**
     * 有效时间
     */
    private String licenseTime;

    /**
     * 产品名称
     */
    private String productName;
}
