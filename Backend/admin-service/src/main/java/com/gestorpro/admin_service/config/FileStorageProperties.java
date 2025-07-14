package com.gestorpro.admin_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;
    private String uploadDirRestricted;

    public String getUploadDir(){
        return uploadDir;
    }

    public void setUploadDir(String uploadDir){
        this.uploadDir = uploadDir;
    }

    public String getUploadDirRestricted(){
        return uploadDirRestricted;
    }

    public void setUploadDirRestricted(String uploadDirRestricted){
        this.uploadDirRestricted = uploadDirRestricted;
    }
}
