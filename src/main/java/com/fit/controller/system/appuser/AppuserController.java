package com.fit.controller.system.appuser;

import com.fit.config.shiro.Jurisdiction;
import com.fit.controller.base.BaseController;
import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.entity.system.Role;
import com.fit.service.system.appuser.AppuserManager;
import com.fit.service.system.role.RoleManager;
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
 * 类名称：会员管理
 */
@Controller
@RequestMapping(value = "/happuser")
public class AppuserController extends BaseController {

    String menuUrl = "happuser/listUsers.do"; //菜单地址(权限用)
    @Resource(name = "appuserService")
    private AppuserManager appuserService;
    @Resource(name = "roleService")
    private RoleManager roleService;

    /**
     * 显示用户列表
     */
    @RequestMapping(value = "/listUsers", method = RequestMethod.GET)
    public ModelAndView listUsers(HttpServletRequest request) {
        ModelAndView mv = this.getModelAndView(request);
        PageData pd = this.getPageData();
        try {
            pd.put("ROLE_ID", "2");
            List<Role> roleList = roleService.listAllRolesByPId(pd);            //列出会员组角色
            mv.setViewName("system/appuser/appuser_list");
            mv.addObject("roleList", roleList);
            mv.addObject("QX", Jurisdiction.getHC());    //按钮权限
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
            String keywords = pd.getString("keywords");                            //检索条件 关键词
            if (null != keywords && !"".equals(keywords)) {
                pd.put("keywords", keywords.trim());
            }
            page.setPd(pd);
            List<PageData> userList = appuserService.listPdPageUser(page);        //列出会员列表
            map.put("userList", userList); // 返回结果
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
     * 去新增用户页面
     */
    @RequestMapping(value = "/goAddU")
    public ModelAndView goAddU(HttpServletRequest request) throws Exception {
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView(request);
        PageData pd = this.getPageData();
        pd.put("ROLE_ID", "2");
        List<Role> roleList = roleService.listAllRolesByPId(pd);            //列出会员组角色
        mv.setViewName("system/appuser/appuser_edit");
        mv.addObject("msg", "saveU");
        mv.addObject("pd", pd.toHash());
        mv.addObject("roleList", roleList);
        return mv;
    }

    /**
     * 保存用户
     */
    @RequestMapping(value = "/saveU")
    public ModelAndView saveU(HttpServletRequest request) throws Exception {
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        logBefore(logger, Jurisdiction.getUsername() + "新增会员");
        ModelAndView mv = this.getModelAndView(request);
        PageData pd = this.getPageData();
        pd.put("USER_ID", this.get32UUID());    //ID
        pd.put("RIGHTS", "");
        pd.put("LAST_LOGIN", "");                //最后登录时间
        pd.put("IP", "");                        //IP
        pd.put("PASSWORD", MD5.md5(pd.getString("PASSWORD")));
        if (null == appuserService.findByUsername(pd)) {
            appuserService.saveU(pd);            //判断新增权限
            mv.addObject("msg", "success");
        } else {
            mv.addObject("msg", "failed");
        }
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 判断用户名是否存在
     */
    @RequestMapping(value = "/hasU")
    @ResponseBody
    public Object hasU() {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = "success";
        PageData pd = this.getPageData();
        try {
            if (appuserService.findByUsername(pd) != null) {
                errInfo = "error";
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);                //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }

    /**
     * 判断邮箱是否存在
     */
    @RequestMapping(value = "/hasE")
    @ResponseBody
    public Object hasE() {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = "success";
        PageData pd = this.getPageData();
        try {
            if (appuserService.findByEmail(pd) != null) {
                errInfo = "error";
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);                //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }

    /**
     * 判断编码是否存在
     */
    @RequestMapping(value = "/hasN")
    @ResponseBody
    public Object hasN() {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = "success";
        PageData pd = this.getPageData();
        try {
            if (appuserService.findByNumber(pd) != null) {
                errInfo = "error";
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);                //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }

    /**
     * 删除用户
     */
    @RequestMapping(value = "/deleteU")
    public void deleteU(HttpServletResponse response) throws Exception {
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } //校验权限
        logBefore(logger, Jurisdiction.getUsername() + "删除会员");
        PageData pd = this.getPageData();
        appuserService.deleteU(pd);
        writeObjectAsJson(response, "success");
    }

    /**
     * 修改用户
     */
    @RequestMapping(value = "/editU")
    public ModelAndView editU(HttpServletRequest request) throws Exception {
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        } //校验权限
        logBefore(logger, Jurisdiction.getUsername() + "修改会员");
        ModelAndView mv = this.getModelAndView(request);
        PageData pd = this.getPageData();
        if (pd.getString("PASSWORD") != null && !"".equals(pd.getString("PASSWORD"))) {
            pd.put("PASSWORD", MD5.md5(pd.getString("PASSWORD")));
        }
        appuserService.editU(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 去修改用户页面
     */
    @RequestMapping(value = "/goEditU")
    public ModelAndView goEditU(HttpServletRequest request) {
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView(request);
        PageData pd = this.getPageData();
        try {
            pd.put("ROLE_ID", "2");
            List<Role> roleList = roleService.listAllRolesByPId(pd);//列出会员组角色
            pd = appuserService.findByUiId(pd);                        //根据ID读取
            mv.setViewName("system/appuser/appuser_edit");
            mv.addObject("msg", "editU");
            mv.addObject("pd", pd.toHash());
            mv.addObject("roleList", roleList);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteAllU")
    @ResponseBody
    public Object deleteAllU() {
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
        } //校验权限
        logBefore(logger, Jurisdiction.getUsername() + "批量删除会员");
        PageData pd = new PageData();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            pd = this.getPageData();
            List<PageData> pdList = new ArrayList<PageData>();
            String USER_IDS = pd.getString("USER_IDS");
            if (null != USER_IDS && !"".equals(USER_IDS)) {
                String ArrayUSER_IDS[] = USER_IDS.split(",");
                appuserService.deleteAllU(ArrayUSER_IDS);
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
     * 导出会员信息到excel
     */
    @RequestMapping(value = "/excel")
    public ModelAndView exportExcel() {
        logBefore(logger, Jurisdiction.getUsername() + "导出会员资料");
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        try {
            if (Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
                String keywords = pd.getString("keywords");
                if (null != keywords && !"".equals(keywords)) {
                    pd.put("keywords", keywords.trim());
                }
                String lastLoginStart = pd.getString("lastLoginStart");
                String lastLoginEnd = pd.getString("lastLoginEnd");
                if (lastLoginStart != null && !"".equals(lastLoginStart)) {
                    pd.put("lastLoginStart", lastLoginStart + " 00:00:00");
                }
                if (lastLoginEnd != null && !"".equals(lastLoginEnd)) {
                    pd.put("lastLoginEnd", lastLoginEnd + " 00:00:00");
                }
                Map<String, Object> dataMap = new HashMap<String, Object>();
                List<String> titles = new ArrayList<String>();
                titles.add("用户名");        //1
                titles.add("编号");        //2
                titles.add("姓名");            //3
                titles.add("手机号");        //4
                titles.add("身份证号");        //5
                titles.add("等级");            //6
                titles.add("邮箱");            //7
                titles.add("最近登录");        //8
                titles.add("到期时间");        //9
                titles.add("上次登录IP");    //10
                dataMap.put("titles", titles);
                List<PageData> userList = appuserService.listAllUser(pd);
                List<PageData> varList = new ArrayList<PageData>();
                for (int i = 0; i < userList.size(); i++) {
                    PageData vpd = new PageData();
                    vpd.put("var1", userList.get(i).getString("USERNAME"));        //1
                    vpd.put("var2", userList.get(i).getString("NUMBER"));        //2
                    vpd.put("var3", userList.get(i).getString("NAME"));            //3
                    vpd.put("var4", userList.get(i).getString("PHONE"));        //4
                    vpd.put("var5", userList.get(i).getString("SFID"));            //5
                    vpd.put("var6", userList.get(i).getString("ROLE_NAME"));    //6
                    vpd.put("var7", userList.get(i).getString("EMAIL"));        //7
                    vpd.put("var8", userList.get(i).getString("LAST_LOGIN"));    //8
                    vpd.put("var9", userList.get(i).getString("END_TIME"));        //9
                    vpd.put("var10", userList.get(i).getString("IP"));            //10
                    varList.add(vpd);
                }
                dataMap.put("varList", varList);
                ObjectExcelView erv = new ObjectExcelView();
                mv = new ModelAndView(erv, dataMap);
            }
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
