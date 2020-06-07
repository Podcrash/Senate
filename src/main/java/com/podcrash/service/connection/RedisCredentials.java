package com.podcrash.service.connection;

public class RedisCredentials {

    private final String host;
    private final int port;
    private final String password;

    public RedisCredentials(String host, int port, String password) {
        this.host = host;
        this.port = port;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }
}
