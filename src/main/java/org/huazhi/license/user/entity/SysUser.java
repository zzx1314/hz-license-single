package org.huazhi.license.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
public class SysUser extends PanacheEntityBase{
    /**
	 * 主键
	 */
	@Id
    @GeneratedValue
	private Long id;

	/**
	 * 账号
	 */
	private String username;

    /**
     * 密码
     */
    @JsonIgnore
	private String password;
}
