package com.fit.controller.base;

import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.entity.PageDataCapital;
import com.fit.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * new PageData对象
     */
    public PageData getPageData() {
        return new PageData(this.getRequest());
    }

    /**
     * new PageDataCapital对象 获取大写的map
     */
    public PageDataCapital getPageDataCapital() {
        return new PageDataCapital(this.getRequest());
    }

    /**
     * 得到ModelAndView
     */
    public ModelAndView getModelAndView(HttpServletRequest request) {
        String contextPath = request.getContextPath().equals("/") ? "" : request.getContextPath();
        String webPath = WebUtil.getURL(request);
        String scheme = request.getScheme();
        String port = ":" + ConvertUtils.toInt(Integer.valueOf(request.getServerPort()));
        if (WebUtil.generic_domain(request).equals("127.0.0.1")) {
        } else if (!WebUtil.generic_domain(request).equals("localhost")) {
            webPath = scheme + "://" + WebUtil.generic_domain(request) + port + contextPath;
        }
        return new ModelAndView().addObject("webPath", webPath);
    }

    /**
     * 得到ModelAndView
     */
    public ModelAndView getModelAndView() {
        return new ModelAndView();
    }

    /**
     * 得到request对象
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * 得到32位的uuid
     */
    public String get32UUID() {
        return UuidUtil.get32UUID();
    }

    /**
     * 得到分页列表的信息
     */
    public Page getPage() {
        return new Page();
    }

    public static void logBefore(Logger logger, String interfaceName) {
        logger.info("");
        logger.info("start");
        logger.info(interfaceName);
    }

    public static void logAfter(Logger logger) {
        logger.info("end");
        logger.info("");
    }

    public List<HashMap<String, Object>> getListHash(List<PageData> list) {
        List<HashMap<String, Object>> resutl = new ArrayList<>();
        for (PageData pd : list) {
            resutl.add(pd.toHash());
        }
        return resutl;
    }

    /**
     * 获取书写器
     */
    protected PrintWriter getPrintWriter(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        return response.getWriter();
    }

    /**
     * 将数据以JSON格式写到客户端
     */
    protected void writeObjectAsJson(HttpServletResponse response, Object obj) {
        try {
            logger.info("服务器返回信息：==>" + JsonUtils.toJson(obj));
            this.getPrintWriter(response).write(JsonUtils.toJson(obj));
            this.getPrintWriter(response).flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
