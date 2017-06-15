package com.atejandro.examples.proxy.config;

import org.springframework.stereotype.Component;

/**
 * Created by atejandro on 12/06/17.
 */
@Component
public class CubeSummationConfig {

    private String url;

    private int port;

    public CubeSummationConfig(String url, int port) {
        this.url = url;
        this.port = port;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
