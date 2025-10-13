package org.huazhi.proj.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
public class LicenseProj extends PanacheEntityBase{
    @Id
    @GeneratedValue
	private Long id;

    /**
     * 客户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 证书密码
     */
    private String cerPassword;

    /**
     * 特性名称
     */
    private String featuresName;

    /**
     * 许可证数量
     */
    private Integer liceNum;

    /**
     * 许可证时间
     */
    private String liceTime;

    /**
     * 项目名称
     */
    private String projName;

    /**
     * 项目编码
     */
    private String projCode;

    /**
     * 已使用授权数
     */
    private Integer useLicNum;


}
