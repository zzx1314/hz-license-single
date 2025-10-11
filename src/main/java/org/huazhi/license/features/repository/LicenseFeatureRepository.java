package org.huazhi.license.features.repository;

import org.huazhi.license.features.entity.LicenseFeatures;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LicenseFeatureRepository implements PanacheRepository<LicenseFeatures> {

}
