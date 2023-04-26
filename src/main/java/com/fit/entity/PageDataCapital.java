package com.fit.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * 参数封装Map
 */
public class PageDataCapital extends HashMap implements Map {

    private static final long serialVersionUID = 1L;

    Map<String, Object> map = null;
    HttpServletRequest request;

    public PageDataCapital(HttpServletRequest request) {
        this.request = request;
        Map properties = request.getParameterMap();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Iterator entries = properties.entrySet().iterator();
        Entry<String, Object> entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Entry) entries.next();
            name = entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name.toUpperCase(), value);
        }
        map = returnMap;
    }

    public PageDataCapital() {
        map = new HashMap<String, Object>();
    }

    @Override
    public Object get(Object key) {
        Object obj = null;
        if (map.get(key) instanceof Object[]) {
            Object[] arr = (Object[]) map.get(key);
            obj = request == null ? arr : (request.getParameter((String) key) == null ? arr : arr[0]);
        } else {
            obj = map.get(key);
        }
        return obj;
    }

    public String getString(Object key) {
        return (String) get(key);
    }

    @Override
    public Object put(Object key, Object value) {
        return map.put(((String) key).toUpperCase(), value);
    }

    @Override
    public Object remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean containsKey(Object key) {

        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {

        return map.containsValue(value);
    }

    @Override
    public Set<?> entrySet() {

        return map.entrySet();
    }

    @Override
    public boolean isEmpty() {

        return map.isEmpty();
    }

    @Override
    public Set<String> keySet() {

        return map.keySet();
    }

    @Override
    public void putAll(Map t) {

        map.putAll(t);
    }

    @Override
    public int size() {

        return map.size();
    }

    @Override
    public Collection values() {

        return map.values();
    }

}
