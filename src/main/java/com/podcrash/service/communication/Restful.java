package com.podcrash.service.communication;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface Restful {


    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Endpoint {

        String route();

        RequestType reqType();
    }

    enum RequestType {

        GET, POST, PUT, PATCH, DELETE
    }
}
