package com.fit.controller.weixin.textmsg;

import com.fit.config.shiro.Jurisdiction;
import com.fit.controller.base.BaseController;
import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.service.weixin.command.CommandService;
import com.fit.service.weixin.imgmsg.ImgmsgService;
import com.fit.service.weixin.textmsg.TextmsgService;
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
 * 类名称：TextmsgController 创建人：FH 创建时间：2015-05-05
 */
@Controller
@RequestMapping(value = "/textmsg")
public class TextmsgController extends BaseController {

    String menuUrl = "textmsg/list.do"; // 菜单地址(权限用)
    @Resource(name = "textmsgService")
    private TextmsgService textmsgService;
    @Resource(name = "commandService")
    private CommandService commandService;
    @Resource(name = "imgmsgService")
    private ImgmsgService imgmsgService;

    /**
     * 新增
     */
    @RequestMapping(value = "/save")
    public ModelAndView save() throws Exception {
        logBefore(logger, "新增Textmsg");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        }
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("TEXTMSG_ID", this.get32UUID()); // 主键
        pd.put("CREATETIME", DateUtil.getTime()); // 创建时间
        textmsgService.save(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    public void delete(HttpServletResponse response) {
        logBefore(logger, "删除Textmsg");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        }
        try {
            PageData pd = this.getPageData();
            textmsgService.delete(pd);
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
        logBefore(logger, "修改Textmsg");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        }
        ModelAndView mv = this.getModelAndView(request);
        PageData pd = this.getPageData();
        textmsgService.edit(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request) {
        logBefore(logger, "列表Textmsg");
        ModelAndView mv = this.getModelAndView(request);
        try {
            PageData pd = this.getPageData();
            mv.setViewName("weixin/textmsg/textmsg_list");
            mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
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
            List<PageData> varList = textmsgService.list(page); // 列出用户列表
            map.put("varList", varList); // 返回结果
            map.put("pd", pd.toHash());
            map.put("count",page.getTotalResult());
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
        logBefore(logger, "去新增Textmsg页面");
        ModelAndView mv = this.getModelAndView(request);
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            mv.setViewName("weixin/textmsg/textmsg_edit");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去关注回复页面
     */
    @RequestMapping(value = "/goSubscribe")
    public ModelAndView goSubscribe(HttpServletRequest request) {
        logBefore(logger, "去关注回复页面");
        ModelAndView mv = this.getModelAndView(request);
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("KEYWORD", "关注");
            PageData msgpd = textmsgService.findByKw(pd);
            if (null != msgpd) {
                mv.addObject("msg", "文本消息");
                mv.addObject("content", msgpd.getString("CONTENT"));
            } else {
                msgpd = imgmsgService.findByKw(pd);
                if (null != msgpd) {
                    mv.addObject("msg", "图文消息");
                    mv.addObject("content", "标题：" + msgpd.getString("TITLE1"));
                } else {
                    msgpd = commandService.findByKw(pd);
                    if (null != msgpd) {
                        mv.addObject("msg", "命令");
                        mv.addObject("content", "执行命令：" + msgpd.getString("COMMANDCODE"));
                    } else {
                        mv.addObject("msg", "无回复");
                    }
                }
            }
            mv.setViewName("weixin/subscribe");
            mv.addObject("pd", msgpd);
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
        logBefore(logger, "去修改Textmsg页面");
        ModelAndView mv = this.getModelAndView(request);
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd = textmsgService.findById(pd); // 根据ID读取
            mv.setViewName("weixin/textmsg/textmsg_edit");
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
        logBefore(logger, "批量删除Textmsg");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return null;
        }
        PageData pd = new PageData();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            pd = this.getPageData();
            List<PageData> pdList = new ArrayList<PageData>();
            String DATA_IDS = pd.getString("DATA_IDS");
            if (null != DATA_IDS && !"".equals(DATA_IDS)) {
                String ArrayDATA_IDS[] = DATA_IDS.split(",");
                textmsgService.deleteAll(ArrayDATA_IDS);
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
     * 判断关键词是否存在
     *
     * @return
     */
    @RequestMapping(value = "/hasK")
    @ResponseBody
    public Object hasK() {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = "success";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            pd.put("STATUS", "3");
            if (textmsgService.findByKw(pd) != null || commandService.findByKw(pd) != null || imgmsgService.findByKw(pd) != null) {
                errInfo = "error";
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo); // 返回结果
        return AppUtil.returnObject(new PageData(), map);
    }

    /**
     * 导出到excel
     *
     * @return
     */
    @RequestMapping(value = "/excel")
    public ModelAndView exportExcel() {
        logBefore(logger, "导出Textmsg到excel");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        }
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<String> titles = new ArrayList<String>();
            titles.add("关键词");// 1
            titles.add("内容"); // 2
            titles.add("创建时间");// 3
            titles.add("状态"); // 4
            titles.add("备注"); // 5
            dataMap.put("titles", titles);
            List<PageData> varOList = textmsgService.listAll(pd);
            List<PageData> varList = new ArrayList<PageData>();
            for (int i = 0; i < varOList.size(); i++) {
                PageData vpd = new PageData();
                vpd.put("var1", varOList.get(i).getString("KEYWORD")); // 1
                vpd.put("var2", varOList.get(i).getString("CONTENT")); // 2
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
