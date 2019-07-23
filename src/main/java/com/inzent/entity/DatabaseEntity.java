package com.inzent.entity;

public class DatabaseEntity {

    private String driver;

    private String url;

    private String user;

    private String password;

    private int connectionPoolCount;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getConnectionPoolCount() {
        return connectionPoolCount;
    }

    public void setConnectionPoolCount(int connectionPoolCount) {
        this.connectionPoolCount = connectionPoolCount;
    }

    @Override
    public String toString() {
        return "DatabaseEntity{" +
                "driver='" + driver + '\'' +
                ", url='" + url + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
