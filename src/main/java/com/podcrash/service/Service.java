package com.podcrash.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
            if (method.getParameterCount() != 1)
                continue;
            if (method.getParameterTypes()[0] != JsonObject.class)
                continue;
            Restful.Endpoint endpoint = method.getDeclaredAnnotation(Restful.Endpoint.class);
            switch (endpoint.reqType()) {
                case GET:
                    Spark.get(endpoint.route(), (req, res) -> method.invoke(restful, GSON.fromJson(req.body(), JsonObject.class)), GSON::toJson);
                case POST:
                    Spark.post(endpoint.route(), (req, res) -> method.invoke(restful, GSON.fromJson(req.body(), JsonObject.class)), GSON::toJson);
                case PUT:
                    Spark.put(endpoint.route(), (req, res) -> method.invoke(restful, GSON.fromJson(req.body(), JsonObject.class)), GSON::toJson);
                case PATCH:
                    Spark.patch(endpoint.route(), (req, res) -> method.invoke(restful, GSON.fromJson(req.body(), JsonObject.class)), GSON::toJson);
                case DELETE:
                    Spark.delete(endpoint.route(), (req, res) -> method.invoke(restful, GSON.fromJson(req.body(), JsonObject.class)), GSON::toJson);
            }
        }
    }
}
