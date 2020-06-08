package com.podcrash.service.connection;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConnection implements Connection<Jedis> {

    private final RedisCredentials redisCredentials;
    private JedisPool jedisPool;

    public RedisConnection(RedisCredentials redisCredentials) {
        this.redisCredentials = redisCredentials;
    }

    @Override
    public void connect() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        this.jedisPool = new JedisPool(jedisPoolConfig,
                this.redisCredentials.getHost(),
                this.redisCredentials.getPort(),
                2000,
                this.redisCredentials.getPassword());
    }

    @Override
    public Jedis getConnection() {
        return this.jedisPool.getResource();
    }

    @Override
    public void close() {
        this.jedisPool.close();
    }
}
