package org.huazhi.client.entity;

import lombok.Data;

@Data
public class LicActiviteDto {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 项目编号
     */
    private String projectCode;

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
     * 产品名称
     */
    private String productName;
}
