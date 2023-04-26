package com.fit.util;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信验证平台
 *
 * @author chenwt
 */
public class SmsUtil {

    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    public static final String APPKEY = "8d5fcd513c639298a7c08e8f8e6f8601";//APPKEY
    public static String url = "http://v.juhe.cn/sms/send";//请求接口地址

    //测试
    static public void main(String args[]) {

        String code = createSmsCode();
        sendOut("39488", "15820242077", code);
        //sendOut("39488","13238398607",code);
        System.out.println("code ===" + code);
    }

    public static String createSmsCode() {
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        return String.valueOf(code);
    }

    /**
     * @param tpl_id      短信模板id
     * @param phoneNumber 手机号码
     * @param code        验证码
     */
    public static int sendOut(String tpl_id, String phoneNumber, String code) {
        String result = null;
        int ret = 0;
        Map<Object, Object> p = new HashMap<Object, Object>();
        p.put("mobile", phoneNumber);//接收短信的手机号码
        p.put("tpl_id", tpl_id);//短信模板ID
        p.put("tpl_value", "#code#=" + code);//验证码
        p.put("key", APPKEY);//APPKEY
        p.put("dtype", "json");//返回数据的格式,xml或json
        try {
            result = net(url, p, "GET");
            JSONObject object = JSONObject.parseObject(result);
            ret = object.getIntValue("error_code");
            if (object.getIntValue("error_code") == 0) {
                System.out.println(object.get("result"));
            } else {
                System.out.println(object.get("error_code") + ":" + object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return 网络请求字符串
     * @throws Exception
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static String net(String strUrl, Map params, String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if (method == null || method.equals("GET")) {
                strUrl = strUrl + "?" + urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params != null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    /**
     * 将map型转为请求参数型
     *
     * @param data
     * @return
     */
    public static String urlencode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (@SuppressWarnings("rawtypes") Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}

