package org.huazhi.device.repository;

import org.huazhi.device.entity.LicenseDevice;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Transient;

@ApplicationScoped
public class LicenseDeviceRepository implements PanacheRepository<LicenseDevice>{
    @Transient
    public void updateById(LicenseDevice dto) {
        LicenseDevice entity = this.findById(dto.getId());
        if (entity != null) {
            entity.setProjId(dto.getProjId());
            entity.setUserId(dto.getUserId());
            entity.setActivationCode(dto.getActivationCode());
            entity.setHardwareInfo(dto.getHardwareInfo());
            entity.setOsVersion(dto.getOsVersion());
            entity.setProcessArch(dto.getProcessArch());
            entity.setProductName(dto.getProductName());
            entity.setCerFailureTime(dto.getCerFailureTime());
            entity.setCerStatus(dto.getCerStatus());
            entity.setActivationTime(dto.getActivationTime());
        }
    }
}
