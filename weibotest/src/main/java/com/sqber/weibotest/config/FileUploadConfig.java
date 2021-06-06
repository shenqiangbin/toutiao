package com.sqber.weibotest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileUploadConfig {

    @Value("${fileupload.savePath}")
    private String savePath;

    @Value("${fileupload.sitemapPath}")
    private String sitemapPath;

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getSitemapPath() {
        return sitemapPath;
    }

    public void setSitemapPath(String sitemapPath) {
        this.sitemapPath = sitemapPath;
    }
}
