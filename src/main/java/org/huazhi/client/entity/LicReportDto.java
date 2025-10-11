package org.huazhi.client.entity;
import lombok.Data;

import java.util.List;

@Data
public class LicReportDto {
    /**
     * 用户名
     */
    private String username;
    /**
     * 版本
     */
    private String version;

    /**
     * 架构
     */
    private String arch;

    /**
     * 产品名称
     */
    private String productName;
    /**
     * 项目编号
     */
    private String projectCode;

    /**
     * 激活码
     */
    private String activateCode;

    /**
     * 验证签名结果
     */
    private Boolean verifySigResult;

    /**
     * 证书有效期
     */
    private String licenseTime;

    /**
     * 上报错误类型
     */
    private Integer type;

    /**
     * 特性列表
     */
    private List<LicFeatureDto> features;
}
