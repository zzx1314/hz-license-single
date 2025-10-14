package org.huazhi.client.service;

import java.io.IOException;

import org.huazhi.client.entity.LicActiviteDto;
import org.huazhi.client.entity.LicReportDto;

public interface LicenseClientService {
    /**
     * 激活
     */
    Object activate(LicActiviteDto licActiviteDto);

    /**
     * 下载证书
     */
    Object downloadCer(String cerPath, String activaCode) throws IOException;

    /**
     * 上报请求
     */
    Object reportServer(LicReportDto licReportDto);
}
