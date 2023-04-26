package com.fit.util;

import com.fit.entity.PageData;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 接口参数校验
 */
@Slf4j
public class AppUtil {

    /**
     * 检查参数是否完整
     *
     * @param method
     * @param pd
     */
    public static boolean checkParam(String method, PageData pd) {
        boolean result = false;

        int falseCount = 0;
        String[] paramArray = new String[20];
        String[] valueArray = new String[20];
        String[] tempArray = new String[20]; // 临时数组

        if (method == "registered") {// 注册
            paramArray = Const.APP_REGISTERED_PARAM_ARRAY; // 参数
            valueArray = Const.APP_REGISTERED_VALUE_ARRAY; // 参数名称

        } else if (method == "getAppuserByUsernmae") {// 根据用户名获取会员信息
            paramArray = Const.APP_GETAPPUSER_PARAM_ARRAY;
            valueArray = Const.APP_GETAPPUSER_VALUE_ARRAY;
        }
        int size = paramArray.length;
        for (int i = 0; i < size; i++) {
            String param = paramArray[i];
            if (!pd.containsKey(param)) {
                tempArray[falseCount] = valueArray[i] + "--" + param;
                falseCount += 1;
            }
        }
        if (falseCount > 0) {
            log.error(method + "接口，请求协议中缺少 " + falseCount + "个 参数");
            for (int j = 1; j <= falseCount; j++) {
                log.error("   第" + j + "个：" + tempArray[j - 1]);
            }
        } else {
            result = true;
        }

        return result;
    }

    /**
     * 设置分页的参数
     *
     * @param pd
     */
    public static PageData setPageParam(PageData pd) {
        String page_now_str = pd.get("page_now").toString();
        int pageNowInt = Integer.parseInt(page_now_str) - 1;
        String page_size_str = pd.get("page_size").toString(); // 每页显示记录数
        int pageSizeInt = Integer.parseInt(page_size_str);
        String page_now = pageNowInt + "";
        String page_start = (pageNowInt * pageSizeInt) + "";
        pd.put("page_now", page_now);
        pd.put("page_start", page_start);
        return pd;
    }

    /**
     * 设置list中的distance
     *
     * @param list
     * @param pd
     */
    public static List<PageData> setListDistance(List<PageData> list, PageData pd) {
        List<PageData> listReturn = new ArrayList<PageData>();
        String user_longitude = "";
        String user_latitude = "";
        try {
            user_longitude = pd.get("user_longitude").toString(); // "117.11811";
            user_latitude = pd.get("user_latitude").toString(); // "36.68484";
        } catch (Exception e) {
            log.error("缺失参数--user_longitude和user_longitude");
            log.error("lost param：user_longitude and user_longitude");
        }
        PageData pdTemp = new PageData();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            pdTemp = list.get(i);
            String longitude = pdTemp.get("longitude").toString();
            String latitude = pdTemp.get("latitude").toString();
            String distance = MapDistance.getDistance(user_longitude, user_latitude, longitude, latitude);
            pdTemp.put("distance", distance);
            pdTemp.put("size", distance.length());
            listReturn.add(pdTemp);
        }
        return listReturn;
    }

    /**
     * @param pd
     * @param map
     */
    public static Object returnObject(PageData pd, Map<?, ?> map) {
        if (pd.containsKey("callback")) {
            String callback = pd.get("callback").toString();
            Map<String, Object> result = new HashMap<String, Object>();
            result.put(callback, JsonUtils.toJson(map));
            return result;
        } else {
            return map;
        }
    }

    /**
     * 去除map中空值key
     */
    public static Map<String, Object> removeMapNull(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (StringUtil.isEmpty(entry.getValue())) {
                map.remove(entry.getKey());
            }
        }
        return map;
    }

    /**
     * @param code
     * @param message
     * @return
     */
    static public HashMap<String, Object> returnObject(String code, String message, String token, Object obj) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("message", message);
        if (!(StringUtil.isEmpty(token))) {
            map.put("token", token);
        }
        if (obj != null) {
            map.put("user", obj);
        }
        return map;
    }

    /**
     * @param code
     * @param message
     * @return
     */
    static public HashMap<String, Object> returnObject(String code, String message) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("message", message);
        return map;
    }

    /**
     * key map大写转换器
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Object mapConvert(Object object) {
        if (object instanceof List) {
            List<PageData> temp = new ArrayList<PageData>();
            for (int i = 0; i < ((List<PageData>) object).size(); i++) {
                PageData pd = ((List<PageData>) object).get(i);
                PageData tempPD = new PageData();
                Iterator<?> iter = pd.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = (String) entry.getKey();
                    tempPD.put(key.toUpperCase(), entry.getValue());
                }
                pd = null;
                temp.add(tempPD);
            }
            object = null;
            return temp;
        } else if (object instanceof Map) {
            PageData pd = (PageData) object;
            PageData temp = new PageData();
            Iterator iter = pd.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                temp.put(key.toUpperCase(), entry.getValue());
            }
            object = null;
            return temp;
        }
        return null;

    }
}
