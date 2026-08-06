package org.ajeet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileStorageConfig {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String getUploadDir() {
        return this.uploadDir;
    }
}
