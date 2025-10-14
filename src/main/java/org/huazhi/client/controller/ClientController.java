package org.huazhi.client.controller;

import java.io.IOException;

import org.huazhi.client.entity.LicActiviteDto;
import org.huazhi.client.entity.LicReportDto;
import org.huazhi.client.service.LicenseClientService;
import org.huazhi.util.R;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/licenseBusCer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientController {
    @Inject
    LicenseClientService licenseClientService;

    @POST
    @Path("/activate")
    public R<Object> activate(LicActiviteDto licActiviteDto) {
        return R.ok(licenseClientService.activate(licActiviteDto));
    }

    @GET
    @Path("/downloadCer/{cerPath}/{activateCode}")
	public Object downloadCer(@PathParam("cerPath") String cerPath, @PathParam("activateCode") String activateCode) throws IOException {
		return licenseClientService.downloadCer(cerPath, activateCode);
	}

    @POST
    @Path("/reportServer")
    public Object reportServer(LicReportDto licReportDto) {
        return licenseClientService.reportServer(licReportDto);
    }



}
