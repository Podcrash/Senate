package com.podcrash.service;

import com.google.gson.Gson;
import com.podcrash.service.communication.Restful;
import spark.Spark;

import java.lang.reflect.Method;

public class Service {

    private final static Gson GSON = new Gson();

    public void registerRestful(Restful restful) {
        Method[] methods = restful.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(Restful.Endpoint.class))
                continue;
            Restful.Endpoint endpoint = method.getDeclaredAnnotation(Restful.Endpoint.class);
            switch (endpoint.reqType()) {
                case GET:
                    Spark.get(endpoint.route(), (req, res) -> method.invoke(restful), GSON::toJson);
                case POST:
                    Spark.post(endpoint.route(), (req, res) -> method.invoke(restful), GSON::toJson);
                case PUT:
                    Spark.put(endpoint.route(), (req, res) -> method.invoke(restful), GSON::toJson);
                case PATCH:
                    Spark.patch(endpoint.route(), (req, res) -> method.invoke(restful), GSON::toJson);
                case DELETE:
                    Spark.delete(endpoint.route(), (req, res) -> method.invoke(restful), GSON::toJson);
            }
        }
    }
}
