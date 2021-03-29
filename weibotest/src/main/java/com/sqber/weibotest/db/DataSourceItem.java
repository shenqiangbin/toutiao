package com.sqber.weibotest.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceItem {

    private String driver;
    private String url;
    private String user;
    private String pwd;
    private String schema;
    private String dbType;

    @Override
    public String toString() {
        return "DataSourceItem{" +
                "driver='" + driver + '\'' +
                ", url='" + url + '\'' +
                ", user='" + user + '\'' +
                ", pwd='" + pwd + '\'' +
                ", schema='" + schema + '\'' +
                ", dbType='" + dbType + '\'' +
                '}';
    }

    public Connection getConn() throws SQLException, ClassNotFoundException {
        Class.forName(this.getDriver());
        Connection conn = DriverManager.getConnection(this.getUrl(),this.getUser(),this.getPwd());
        return conn;
    }

    public boolean isPostgresql(){
        return this.driver.equalsIgnoreCase("org.postgresql.Driver");
    }

    public boolean isMySQL(){
        return this.driver.equalsIgnoreCase("com.mysql.jdbc.Driver") ||
                this.driver.equalsIgnoreCase("com.mysql.cj.jdbc.Driver");
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }
}
