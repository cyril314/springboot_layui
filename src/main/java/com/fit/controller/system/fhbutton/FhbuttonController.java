package com.fit.controller.system.fhbutton;

import com.fit.config.shiro.Jurisdiction;
import com.fit.controller.base.BaseController;
import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.service.system.fhbutton.FhbuttonManager;
import com.fit.util.*;
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
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 说明：按钮管理
 * 创建人：
 * 创建时间：2016-01-15
 */
@Controller
@RequestMapping(value = "/fhbutton")
public class FhbuttonController extends BaseController {

    String menuUrl = "fhbutton/list.do"; //菜单地址(权限用)
    @Resource(name = "fhbuttonService")
    private FhbuttonManager fhbuttonService;

    /**
     * 保存
     */
    @RequestMapping(value = "/save")
    public ModelAndView save() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "新增button");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        pd.put("FHBUTTON_ID", this.get32UUID());    //主键
        fhbuttonService.save(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    public void delete(PrintWriter out) throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "删除button");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } //校验权限
        PageData pd = this.getPageData();
        fhbuttonService.delete(pd);
        out.write(JsonUtils.toJson("success"));
        out.close();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/edit")
    public ModelAndView edit() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "修改button");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        fhbuttonService.edit(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request) throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "列表button");
        ModelAndView mv = this.getModelAndView(request);
        PageData pd = this.getPageData();
        mv.setViewName("system/fhbutton/button_list");
        mv.addObject("pd", pd.toHash());
        mv.addObject("QX", Jurisdiction.getHC());    //按钮权限
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
            page.setPd(pd);
            List<PageData> varList = fhbuttonService.list(page);    //列出Fhbutton列表
            Object o = fhbuttonService.listCount(page);
            map.put("varList", varList); // 返回结果
            map.put("count", ConvertUtils.toInt(o)); // 返回结果
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
     * 去新增页面
     */
    @RequestMapping(value = "/goAdd")
    public ModelAndView goAdd() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        mv.setViewName("system/fhbutton/button_edit");
        mv.addObject("msg", "save");
        mv.addObject("pd", pd.toHash());
        return mv;
    }

    /**
     * 去修改页面
     */
    @RequestMapping(value = "/goEdit")
    public ModelAndView goEdit() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        pd = fhbuttonService.findById(pd);    //根据ID读取
        mv.setViewName("system/fhbutton/button_edit");
        mv.addObject("msg", "edit");
        mv.addObject("pd", pd.toHash());
        return mv;
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteAll")
    @ResponseBody
    public Object deleteAll() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "批量删除Fhbutton");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return null;
        } //校验权限
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = this.getPageData();
        List<PageData> pdList = new ArrayList<PageData>();
        String DATA_IDS = pd.getString("DATA_IDS");
        if (null != DATA_IDS && !"".equals(DATA_IDS)) {
            String ArrayDATA_IDS[] = DATA_IDS.split(",");
            this.fhbuttonService.deleteAll(ArrayDATA_IDS);
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
     */
    @RequestMapping(value = "/excel")
    public ModelAndView exportExcel() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "导出Fhbutton到excel");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        }
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<String> titles = new ArrayList<String>();
        titles.add("名称");    //1
        titles.add("权限标识");    //2
        titles.add("备注");    //3
        dataMap.put("titles", titles);
        List<PageData> varOList = fhbuttonService.listAll(pd);
        List<PageData> varList = new ArrayList<PageData>();
        for (int i = 0; i < varOList.size(); i++) {
            PageData vpd = new PageData();
            vpd.put("var1", varOList.get(i).getString("NAME"));    //1
            vpd.put("var2", varOList.get(i).getString("QX_NAME"));    //2
            vpd.put("var3", varOList.get(i).getString("BZ"));    //3
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
