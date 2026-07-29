package com.aurix.platform.banking.salario.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aurix.salario.cnab")
public class CnabConfig {
    private String dirUpload = "./data/cnab";
    private String maxFileSize = "10MB";

    public String getDirUpload() { return dirUpload; }
    public void setDirUpload(String dirUpload) { this.dirUpload = dirUpload; }
    public String getMaxFileSize() { return maxFileSize; }
    public void setMaxFileSize(String maxFileSize) { this.maxFileSize = maxFileSize; }
}
