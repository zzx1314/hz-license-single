package org.huazhi.license.features.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
public class LicenseFeatures extends PanacheEntityBase{
    @Id
    @GeneratedValue
	private Long id;

    private String featuresName;

    private String featuresVersion;
}
