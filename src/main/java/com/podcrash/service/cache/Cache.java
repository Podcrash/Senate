package com.podcrash.service.cache;

public interface Cache {

    void set(String key, String value);

    String get(String key);
}
