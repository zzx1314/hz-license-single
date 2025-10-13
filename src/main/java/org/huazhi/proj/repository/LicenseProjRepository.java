package org.huazhi.proj.repository;

import org.huazhi.proj.entity.LicenseProj;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Transient;

@ApplicationScoped
public class LicenseProjRepository implements PanacheRepository<LicenseProj> {
    @Transient
    public void updateById(LicenseProj dto) {
        LicenseProj entity = this.findById(dto.getId());
        if (entity != null) {
            entity.setUsername(dto.getUsername());
            entity.setPassword(dto.getPassword());
            entity.setCerPassword(dto.getCerPassword());
            entity.setFeaturesName(dto.getFeaturesName());
            entity.setLiceNum(dto.getLiceNum());
            entity.setLiceTime(dto.getLiceTime());
            entity.setProjName(dto.getProjName());
            entity.setProjCode(dto.getProjCode());
        }
    }
}