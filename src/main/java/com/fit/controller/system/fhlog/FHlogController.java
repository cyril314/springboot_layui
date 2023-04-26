package com.fit.controller.system.fhlog;

import com.fit.controller.base.BaseController;
import com.fit.entity.Page;
import com.fit.service.system.fhlog.FHlogManager;
import com.fit.util.AppUtil;
import com.fit.config.shiro.Jurisdiction;
import com.fit.util.ObjectExcelView;
import com.fit.entity.PageData;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 说明：操作日志记录
 */
@Controller
@RequestMapping(value = "/fhlog")
public class FHlogController extends BaseController {

    String menuUrl = "fhlog/list.do"; //菜单地址(权限用)
    @Resource(name = "fhlogService")
    private FHlogManager fhlogService;

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    public void delete(HttpServletResponse response) throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "删除FHlog");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } //校验权限
        PageData pd = new PageData();
        pd = this.getPageData();
        fhlogService.delete(pd);
        writeObjectAsJson(response, "success");
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request) throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "列表FHlog");
        ModelAndView mv = this.getModelAndView(request);
        mv.setViewName("system/fhlog/fhlog_list");
        mv.addObject("QX", Jurisdiction.getHC());                //按钮权限
        return mv;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Object list(Page page) {
        Map<String, Object> map = new HashMap<String, Object>();
        Integer errCode = 0;
        String errInfo = "success";
        try {
            PageData pd = this.getPageData();
            String keywords = pd.getString("keywords");                //关键词检索条件
            if (null != keywords && !"".equals(keywords)) {
                pd.put("keywords", keywords.trim());
            }
            String lastStart = pd.getString("lastStart");    //开始时间
            String lastEnd = pd.getString("lastEnd");        //结束时间
            if (lastStart != null && !"".equals(lastStart)) {
                pd.put("lastStart", lastStart + " 00:00:00");
            }
            if (lastEnd != null && !"".equals(lastEnd)) {
                pd.put("lastEnd", lastEnd + " 00:00:00");
            }
            page.setPd(pd);
            List<PageData> varList = fhlogService.list(page);
            map.put("varList", varList); // 返回结果
            map.put("pd", pd.toHash());
            map.put("count", page.getTotalResult());
        } catch (Exception e) {
            e.printStackTrace();
            errInfo = "error";
            errCode = 1000;
        }
        map.put("result", errInfo); // 返回结果
        map.put("code", errCode); // 返回结果
        return AppUtil.returnObject(new PageData(), map);
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteAll")
    @ResponseBody
    public Object deleteAll() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "批量删除FHlog");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return null;
        } //校验权限
        PageData pd = new PageData();
        Map<String, Object> map = new HashMap<String, Object>();
        pd = this.getPageData();
        List<PageData> pdList = new ArrayList<PageData>();
        String DATA_IDS = pd.getString("DATA_IDS");
        if (null != DATA_IDS && !"".equals(DATA_IDS)) {
            String ArrayDATA_IDS[] = DATA_IDS.split(",");
            fhlogService.deleteAll(ArrayDATA_IDS);
            pd.put("msg", "ok");
        } else {
            pd.put("msg", "no");
        }
        pdList.add(pd);
        map.put("list", pdList);
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 导出到excel
     *
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/excel")
    public ModelAndView exportExcel() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "导出FHlog到excel");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        }
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<String> titles = new ArrayList<String>();
        titles.add("用户名");    //1
        titles.add("操作时间");    //2
        titles.add("事件");    //3
        dataMap.put("titles", titles);
        List<PageData> varOList = fhlogService.listAll(pd);
        List<PageData> varList = new ArrayList<PageData>();
        for (int i = 0; i < varOList.size(); i++) {
            PageData vpd = new PageData();
            vpd.put("var1", varOList.get(i).getString("USERNAME"));        //1
            vpd.put("var2", varOList.get(i).getString("CZTIME"));        //2
            vpd.put("var3", varOList.get(i).getString("CONTENT"));        //3
            varList.add(vpd);
        }
        dataMap.put("varList", varList);
        ObjectExcelView erv = new ObjectExcelView();
        mv = new ModelAndView(erv, dataMap);
        return mv;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
    }
}
