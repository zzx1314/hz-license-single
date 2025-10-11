package org.huazhi.license.user.repository;

import org.huazhi.license.user.entity.SysUser;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SysUserRepository implements PanacheRepository<SysUser> {

}
