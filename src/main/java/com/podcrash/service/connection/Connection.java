package com.podcrash.service.connection;

public interface Connection<T> {

    void connect();

    T getConnection();

    void close();
}
