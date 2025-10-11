package org.huazhi.device.entity;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
public class LicenseDevice extends PanacheEntity{
    @Id
    @GeneratedValue
	private Long id;

    /**
     * 项目ID
     */
    private Integer projId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 激活码
     */
    private String activationCode;

    /**
     * 硬件信息
     */
    private String hardwareInfo;

    /**
     * 操作系统版本
     */
    private String osVersion;

    /**
     * 处理器架构
     */
    private String processArch;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 证书失效时间
     */
    private String cerFailureTime;

    /**
     * 最后一次通讯时间
     */
    private LocalDateTime commTime;


    /**
     * 激活时间
     */
    private LocalDateTime activationTime;


    /**
     * 激活次数
     */
    private Integer activationNum;

    /**
     * 证书状态
     */
    private String cerStatus;
}
