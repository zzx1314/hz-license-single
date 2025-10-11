package org.huazhi.license.proj.repository;

import org.huazhi.license.proj.entity.LicenseProj;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LicenseProjRepository implements PanacheRepository<LicenseProj> {

}
