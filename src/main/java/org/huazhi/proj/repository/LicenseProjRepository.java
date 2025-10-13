package org.huazhi.proj.repository;

import org.huazhi.proj.entity.LicenseProj;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LicenseProjRepository implements PanacheRepository<LicenseProj> {

}
