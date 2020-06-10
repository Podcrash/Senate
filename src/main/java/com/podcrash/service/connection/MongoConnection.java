package com.podcrash.service.connection;

import com.mongodb.MongoCredential;
//import com.mongodb.reactivestreams.client.MongoClient;
//import com.mongodb.reactivestreams.client.MongoClients;

public class MongoConnection {

    private final MongoCredentials mongoCredentials;
//    private MongoClient mongoClient;

    public MongoConnection(MongoCredentials mongoCredentials) {
        this.mongoCredentials = mongoCredentials;
    }

//    @Override
//    public void connect() {
//        MongoCredential mongoCredential = MongoCredential.createCredential(
//                mongoCredentials.getUsername(),
//                mongoCredentials.getDatabase(),
//                mongoCredentials.getPassword().toCharArray());
//        this.mongoClient = MongoClients.create(String.format(
//                "mongodb://%s:%s@%s"));
//    }
//
//    @Override
//    public MongoClient getConnection() {
//        return this.mongoClient;
//    }
//
//    @Override
//    public void close() {
//        this.mongoClient.close();
//    }
}
