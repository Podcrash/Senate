package com.podcrash.service.cache;

import com.podcrash.service.connection.ShardedRedisConnection;
import redis.clients.jedis.ShardedJedis;

public class RedisCache implements Cache {

    private final ShardedRedisConnection redisConnection;

    public RedisCache(ShardedRedisConnection redisConnection) {
        this.redisConnection = redisConnection;
    }

    @Override
    public void set(String key, String value) {
        try (ShardedJedis shardedJedis = redisConnection.getConnection()) {
            shardedJedis.set(key, value);
        }
    }

    @Override
    public String get(String key) {
        try (ShardedJedis shardedJedis = redisConnection.getConnection()) {
            return shardedJedis.get(key);
        }
    }
}
