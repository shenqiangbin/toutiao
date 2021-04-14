package com.sqber.weibotest.model;

import org.openqa.selenium.Cookie;

import java.util.Date;

public class MyCookie {
    private String name;
    private String value;
    private String path;
    private String domain;
    private Date expiry;
    private boolean isSecure;
    private boolean isHttpOnly;

    public Cookie toSeleCookie() {
        expiry = null;
        isHttpOnly = false;
        return new Cookie(name, value, domain, path, expiry, isSecure, isHttpOnly);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public boolean isSecure() {
        return isSecure;
    }

    public void setSecure(boolean secure) {
        isSecure = secure;
    }

    public boolean isHttpOnly() {
        return isHttpOnly;
    }

    public void setHttpOnly(boolean httpOnly) {
        isHttpOnly = httpOnly;
    }
}
