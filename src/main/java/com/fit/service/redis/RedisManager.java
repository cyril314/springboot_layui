package com.fit.service.redis;

import com.fit.entity.PageData;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 说明： 第2数据源例子接口
 */
public interface RedisManager {

    /**
     * 新增(存储字符串)
     *
     * @param key
     * @param value
     */
    public boolean addString(String key, String value);

    /**
     * 拼接字符串
     *
     * @param key
     * @param value
     */
    public boolean appendString(String key, String value);

    /**
     * 新增(存储Map)
     *
     * @param key
     * @param map
     */
    public void addMap(String key, Map<String, String> map);

    /**
     * 获取map
     *
     * @param key
     */
    public Map<String, String> getMap(String key);

    /**
     * 新增(存储List)
     *
     * @param key
     * @param list
     */
    public void addList(String key, List<String> list);

    /**
     * 获取List
     *
     * @param key
     * @return
     */
    public List<PageData> getList(String key);

    /**
     * 新增(存储set)
     *
     * @param key
     * @param set
     */
    public void addSet(String key, Set<String> set);

    /**
     * 获取Set
     *
     * @param key
     */
    public Set<String> getSet(String key);

    /**
     * 删除
     *
     * @param key
     */
    public boolean delete(String key);

    /**
     * 删除多个
     *
     * @param keys
     */
    public void delete(List<String> keys);

    /**
     * 修改
     *
     * @param key
     * @param value
     */
    public boolean eidt(String key, String value);

    /**
     * 通过ket获取数据
     *
     * @param keyId
     */
    public String get(String keyId);

}
