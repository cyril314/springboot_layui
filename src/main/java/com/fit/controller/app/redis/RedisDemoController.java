package com.fit.controller.app.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fit.controller.base.BaseController;
import com.fit.service.redis.RedisManager;
import com.fit.util.AppUtil;
import com.fit.entity.PageData;

/**
 * RedisDemo
 * <p>
 * 2016.5.8
 */
@Controller
@RequestMapping(value = "/appRedisDemo")
public class RedisDemoController extends BaseController {

    @Resource(name = "redisDaoImpl")
    private RedisManager redisManagerImpl;

    /**
     * 请讲接口 http://127.0.0.1:8080/项目名称/appRedisDemo/redisDemo.do
     * demo展示的在redis存储读取数据的方式，本系统暂时用不到redis，此redis接口可根据实际业务需求选择使用
     * 具体redis的应用场景->百度下即可
     */
    @RequestMapping(value = "/redisDemo")
    @ResponseBody
    public Object redis() {

        Map<String, Object> map = new HashMap<String, Object>();
        String result = "";

        redisManagerImpl.delete("fh0");                                            //删除
        redisManagerImpl.delete("fh");                                            //删除
        redisManagerImpl.delete("fh1");                                            //删除
        redisManagerImpl.delete("fh2");                                            //删除

        System.out.println(redisManagerImpl.addString("fh0", "opopopo"));        //存储字符串
        System.out.println("获取字符串:" + redisManagerImpl.get("fh0"));            //获取字符串

        result += "获取字符串:" + redisManagerImpl.get("fh0") + ",";

        Map<String, String> jmap = new HashMap<String, String>();
        jmap.put("name", "fhadmin");
        jmap.put("age", "22");
        jmap.put("qq", "313596790");
        redisManagerImpl.addMap("fh", jmap);                //存储Map
        System.out.println("获取Map:" + redisManagerImpl.getMap("fh"));            //获取Map

        result += "获取Map:" + redisManagerImpl.getMap("fh") + ",";

        List<String> list = new ArrayList<String>();
        list.add("ssss");
        list.add("bbbb");
        list.add("cccc");
        redisManagerImpl.addList("fh1", list);                                    //存储List
        System.out.println("获取List:" + redisManagerImpl.getList("fh1"));            //获取List

        result += "获取List:" + redisManagerImpl.getList("fh1") + ",";

        Set<String> set = new HashSet<String>();
        set.add("wwww");
        set.add("eeee");
        set.add("rrrr");
        redisManagerImpl.addSet("fh2", set);                                    //存储Set
        System.out.println("获取Set:" + redisManagerImpl.getSet("fh2"));            //获取Set

        result += "获取Set:" + redisManagerImpl.getSet("fh2") + ",";

        map.put("result", result);

        return AppUtil.returnObject(new PageData(), map);
    }

}
