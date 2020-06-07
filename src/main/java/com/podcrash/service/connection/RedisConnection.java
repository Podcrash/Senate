package com.podcrash.service.connection;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

public class RedisConnection implements Connection<ShardedJedis> {

    private final RedisCredentials[] redisCredentials;
    private ShardedJedisPool shardedJedisPool;

    public RedisConnection(RedisCredentials[] redisCredentials) {
        this.redisCredentials = redisCredentials;
    }

    @Override
    public void connect() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        List<JedisShardInfo> jedisShards = new ArrayList<>();
        for (RedisCredentials credentials : redisCredentials) {
            JedisShardInfo jedisShardInfo =
                    new JedisShardInfo(
                            credentials.getHost(),
                            credentials.getPort());
            jedisShardInfo.setPassword(credentials.getPassword());
            jedisShards.add(jedisShardInfo);
        }
        this.shardedJedisPool = new ShardedJedisPool(jedisPoolConfig,
                jedisShards);
    }

    @Override
    public ShardedJedis getConnection() {
        return this.shardedJedisPool.getResource();
    }

    @Override
    public void close() {
        this.shardedJedisPool.close();
    }
}
