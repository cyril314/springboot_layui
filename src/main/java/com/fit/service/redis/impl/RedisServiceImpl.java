package com.fit.service.redis.impl;

import com.fit.dao.AbstractBaseRedisDao;
import com.fit.service.redis.RedisManager;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("redisDaoImpl")
public class RedisServiceImpl extends AbstractBaseRedisDao<String, Object> implements RedisManager {

    /**
     * 新增(存储字符串)
     *
     * @param key
     * @param value
     */
    @Override
    public boolean addString(final String key, final String value) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] jkey = serializer.serialize(key);
                byte[] jvalue = serializer.serialize(value);
                return connection.setNX(jkey, jvalue);
            }
        });
        return result;
    }

    /**
     * 新增(拼接字符串)
     *
     * @param key
     * @param value
     */
    @Override
    public boolean appendString(final String key, final String value) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] jkey = serializer.serialize(key);
                byte[] jvalue = serializer.serialize(value);
                if (connection.exists(jkey)) {
                    connection.append(jkey, jvalue);
                    return true;
                } else {
                    return false;
                }
            }
        });
        return result;
    }

    /**
     * 新增(存储Map)
     *
     * @param key
     * @param map
     */
    @Override
    public void addMap(String key, Map<String, String> map) {
        if(map != null){
            redisTemplate.opsForHash().putAll(key, map);
        }
    }

    /**
     * 获取map
     *
     * @param key
     */
    @Override
    public Map getMap(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 新增(存储List)
     *
     * @param key
     * @param list
     */
    @Override
    public void addList(String key, List<String> list) {
        if (list.size() > 0) {
            redisTemplate.opsForList().leftPush(key, list);
        }
    }

    /**
     * 获取List
     *
     * @param key
     * @return
     */
    @Override
    public List getList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 新增(存储set)
     *
     * @param key
     * @param set
     */
    @Override
    public void addSet(String key, Set<String> set) {
        for (String value : set) {
            redisTemplate.opsForSet().add(key, value);
        }
    }

    /**
     * 获取Set
     *
     * @param key
     */
    @Override
    public Set<String> getSet(String key) {
        return redisTemplate.keys(key);
    }

    /**
     * 删除
     *
     * @param key
     */
    @Override
    public boolean delete(final String key) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] jkey = serializer.serialize(key);
                if (connection.exists(jkey)) {
                    connection.del(jkey);
                    return true;
                } else {
                    return false;
                }
            }
        });
        return result;
    }

    /**
     * 删除多个
     *
     * @param keys
     */
    @Override
    public void delete(List<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 修改
     *
     * @param key
     * @param value
     */
    @Override
    public boolean eidt(String key, String value) {
        if (delete(key)) {
            addString(key, value);
            return true;
        }
        return false;
    }

    /**
     * 通过key获取值
     *
     * @param keyId
     */
    @Override
    public String get(final String keyId) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] jkey = serializer.serialize(keyId);
                byte[] jvalue = connection.get(jkey);
                if (jvalue == null) {
                    return null;
                }
                return serializer.deserialize(jvalue);
            }
        });
        return result;
    }
}
