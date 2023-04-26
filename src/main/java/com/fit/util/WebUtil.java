package com.fit.util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @AUTO 网络工具
 * @FILE WebUtil.java
 * @DATE 2017-9-14 下午10:02:45
 * @Author AIM
 */
public class WebUtil {

    /**
     * 获取请求头的信息
     */
    public static Map<String, Object> getReqHeaderMsg(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Enumeration<?> enum1 = request.getHeaderNames();
        while (enum1.hasMoreElements()) {
            String key = (String) enum1.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * 获取请求内容的信息
     */
    public static String getReqBodyMsg(HttpServletRequest request) {
        String inputLine, str = "";
        try {
            BufferedReader br = request.getReader();
            while ((inputLine = br.readLine()) != null) {
                str += inputLine;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 请求地址
     */
    public static String getURL(HttpServletRequest request) {
        String contextPath = request.getContextPath().equals("/") ? "" : request.getContextPath();
        String scheme = request.getScheme();
        String url = scheme + "://" + request.getServerName();
        if (ConvertUtils.toInt(Integer.valueOf(request.getServerPort())) != 80)
            url = url + ":" + ConvertUtils.toInt(Integer.valueOf(request.getServerPort())) + contextPath;
        else {
            url = url + contextPath;
        }
        // logger.info("请求地址为："+url);
        return url;
    }

    /**
     * 字符串首位去空
     */
    public static String trimSpaces(String str) {
        while (str.startsWith(" ")) {
            str = str.substring(1, str.length()).trim();
        }
        while (str.endsWith(" ")) {
            str = str.substring(0, str.length() - 1).trim();
        }
        return str;
    }

    /**
     * 判断是不是IP地址
     */
    public static boolean isIp(String IP) {
        boolean b = false;
        IP = trimSpaces(IP);
        if (IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            String[] s = IP.split("\\.");
            if ((Integer.parseInt(s[0]) < 255) && (Integer.parseInt(s[1]) < 255) && (Integer.parseInt(s[2]) < 255)
                    && (Integer.parseInt(s[3]) < 255))
                b = true;
        }
        return b;
    }

    /**
     * 获取请求的域名或IP
     */
    public static String generic_domain(HttpServletRequest request) {
        String system_domain = "localhost";
        String serverName = request.getServerName();
        if (isIp(serverName))
            system_domain = serverName;
        else {
            system_domain = serverName.substring(serverName.indexOf(".") + 1);
        }

        return system_domain;
    }

    /**
     * 方法用途: 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串<br>
     * 实现步骤: <br>
     *
     * @param paraMap    要排序的Map对象
     * @param urlEncode  是否需要URLENCODE
     * @param keyToLower 是否需要将Key转换为全小写 true:key转化成小写，false:不转化
     */
    public static String formatUrlMap(Map<String, Object> paraMap, boolean urlEncode, boolean keyToLower) {
        String buff = "";
        Map<String, Object> tmpMap = paraMap;
        try {
            List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(tmpMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
                public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造URL 键值对的格式
            StringBuilder buf = new StringBuilder();
            for (Map.Entry<String, Object> item : infoIds) {
                if (StringUtil.isNotBlank(item.getKey())) {
                    String key = item.getKey();
                    String val = item.getValue().toString().trim();
                    if (urlEncode) {
                        val = URLEncoder.encode(val, "utf-8");
                    }
                    if (keyToLower) {
                        buf.append(key.toLowerCase() + "=" + val);
                    } else {
                        buf.append(key + "=" + val);
                    }
                    buf.append("&");
                }
            }
            buff = buf.toString();
            if (buff.isEmpty() == false) {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e) {
            return null;
        }
        return buff;
    }
}
