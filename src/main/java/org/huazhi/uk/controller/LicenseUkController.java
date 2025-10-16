package org.huazhi.uk.controller;


import org.huazhi.uk.entity.LicenseUkEntity;
import org.huazhi.uk.service.LicenseUkService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/licenseUkCer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LicenseUkController {
    @Inject
    LicenseUkService licenseUkService;

    @POST
    @Path("/importFile")
    public Object importFile(LicenseUkEntity licenseUkEntity) {
        return licenseUkService.importFile(licenseUkEntity);
    }
}
