package org.huazhi.device.repository;

import org.huazhi.device.entity.LicenseDevice;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LicenseDeviceRepository implements PanacheRepository<LicenseDevice>{

}
