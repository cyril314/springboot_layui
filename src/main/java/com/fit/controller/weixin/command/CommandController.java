package com.fit.controller.weixin.command;

import com.fit.config.shiro.Jurisdiction;
import com.fit.controller.base.BaseController;
import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.service.weixin.command.CommandService;
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
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 */
@Controller
@RequestMapping(value = "/command")
public class CommandController extends BaseController {

    String menuUrl = "command/list.do"; // 菜单地址(权限用)
    @Resource(name = "commandService")
    private CommandService commandService;

    /**
     * 新增
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView save() throws Exception {
        logBefore(logger, "新增Command");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } // 校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("COMMAND_ID", this.get32UUID()); // 主键
        pd.put("CREATETIME", DateUtil.getTime());// 创建时间
        commandService.save(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    public void delete(HttpServletResponse response) {
        logBefore(logger, "删除Command");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } // 校验权限
        PageData pd = this.getPageData();
        try {
            commandService.delete(pd);
            writeObjectAsJson(response, "success");
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/edit")
    public ModelAndView edit(HttpServletRequest request) throws Exception {
        logBefore(logger, "修改Command");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        } // 校验权限
        ModelAndView mv = this.getModelAndView(request);
        PageData pd = new PageData();
        pd = this.getPageData();
        commandService.edit(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request) {
        logBefore(logger, "列表Command");
        ModelAndView mv = this.getModelAndView(request);
        mv.setViewName("weixin/command/command_list");
        mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
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
            String KEYWORD = pd.getString("KEYWORD");
            if (null != KEYWORD && !"".equals(KEYWORD)) {
                pd.put("KEYWORD", KEYWORD.trim());
            }
            page.setPd(pd);
            List<PageData> varList = commandService.list(page); // 列出Command列表
            map.put("varList", varList); // 返回结果
            map.put("pd", pd.toHash());
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
    public ModelAndView goAdd(HttpServletRequest request) {
        logBefore(logger, "去新增Command页面");
        ModelAndView mv = this.getModelAndView(request);
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            mv.setViewName("weixin/command/command_edit");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去修改页面
     */
    @RequestMapping(value = "/goEdit")
    public ModelAndView goEdit(HttpServletRequest request) {
        logBefore(logger, "去修改Command页面");
        ModelAndView mv = this.getModelAndView(request);
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd = commandService.findById(pd); // 根据ID读取
            mv.setViewName("weixin/command/command_edit");
            mv.addObject("msg", "edit");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteAll")
    @ResponseBody
    public Object deleteAll() {
        logBefore(logger, "批量删除Command");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "dell")) {
            return null;
        } // 校验权限
        PageData pd = new PageData();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            pd = this.getPageData();
            List<PageData> pdList = new ArrayList<PageData>();
            String DATA_IDS = pd.getString("DATA_IDS");
            if (null != DATA_IDS && !"".equals(DATA_IDS)) {
                String ArrayDATA_IDS[] = DATA_IDS.split(",");
                commandService.deleteAll(ArrayDATA_IDS);
                pd.put("msg", "ok");
            } else {
                pd.put("msg", "no");
            }
            pdList.add(pd);
            map.put("list", pdList);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        } finally {
            logAfter(logger);
        }
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 导出到excel
     *
     * @return
     */
    @RequestMapping(value = "/excel")
    public ModelAndView exportExcel() {
        logBefore(logger, "导出Command到excel");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        }
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<String> titles = new ArrayList<String>();
            titles.add("关键词"); // 1
            titles.add("应用路径"); // 2
            titles.add("创建时间"); // 3
            titles.add("状态"); // 4
            titles.add("备注"); // 5
            dataMap.put("titles", titles);
            List<PageData> varOList = commandService.listAll(pd);
            List<PageData> varList = new ArrayList<PageData>();
            for (int i = 0; i < varOList.size(); i++) {
                PageData vpd = new PageData();
                vpd.put("var1", varOList.get(i).getString("KEYWORD")); // 1
                vpd.put("var2", varOList.get(i).getString("COMMANDCODE")); // 2
                vpd.put("var3", varOList.get(i).getString("CREATETIME")); // 3
                vpd.put("var4", varOList.get(i).get("STATUS").toString()); // 4
                vpd.put("var5", varOList.get(i).getString("BZ")); // 5
                varList.add(vpd);
            }
            dataMap.put("varList", varList);
            ObjectExcelView erv = new ObjectExcelView();
            mv = new ModelAndView(erv, dataMap);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
    }
}
