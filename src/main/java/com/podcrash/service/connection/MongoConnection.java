package com.podcrash.service.connection;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Collections;

/**
 * Class to manage a mongodb connection for a specific service
 */
public class MongoConnection implements Connection<MongoClient> {

    //todo log stuff
    //todo shard this for more than one server

    private MongoClient mongoClient;
    private String dbName;
    private String host;
    private String port;
    private PojoCodecProvider provider;

    /**
     * Provides the database name and any pojos for the database to use.
     * @param dbName The name of the database to use for the specific microservice
     * @param provider The PojoCodecProvider that supplies the pojos to use for the specific microservice. It should
     *                 be constructed from the ClassModels of all the needed pojos.
     */
    public MongoConnection(String dbName, PojoCodecProvider provider) {
        this.dbName = dbName;
        this.host = System.getenv("MONGO_HOST_" + dbName);
        this.port = System.getenv("MONGO_PORT");
        //maybe catch NPE's from the variables not existing?
        this.provider = provider;
    }

    private MongoCredential getCredentials() {
        String user = System.getenv("MONGO_USER");
        String pwd = System.getenv("MONGO_PASSWORD");
                //todo make this a credential server
        return MongoCredential.createCredential(user, dbName, pwd.toCharArray());
    }

    /**
     * Initializes the database connection using the data supplied in the constructor
     */
    @Override
    public void connect() {

        int portint = Integer.parseInt(port);
        ServerAddress address = new ServerAddress(host, portint);
        CodecRegistry codecRegistry =
                CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                        CodecRegistries.fromCodecs(new UuidCodec(UuidRepresentation.STANDARD)),
                        CodecRegistries.fromProviders(provider));

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder.hosts(Collections.singletonList(address)))
                .codecRegistry(codecRegistry)
                .credential(getCredentials())
                .build();

        this.mongoClient = MongoClients.create(settings);
    }

    /**
     * Gives the database connection to be used by the microservice
     * @return The client connection
     */
    @Override
    public MongoClient getConnection() {
        return this.mongoClient;
    }

    /**
     * Gives the database interface used by the client connection
     * @return The database
     */
    public MongoDatabase getDatabase() {
        return this.mongoClient.getDatabase(dbName);
    }

    @Override
    public void close() {
        this.mongoClient.close();
    }
}
