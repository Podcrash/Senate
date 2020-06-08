package com.podcrash.service.connection;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

public class MongoConnection implements Connection<MongoClient> {

    private MongoClient mongoClient;

    public MongoConnection() {
        //
    }

    @Override
    public void connect() {
        this.mongoClient = MongoClients.create();
    }

    @Override
    public MongoClient getConnection() {
        return this.mongoClient;
    }

    @Override
    public void close() {
        this.mongoClient.close();
    }
}
