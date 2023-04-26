package com.fit.controller.system;

import com.fit.config.shiro.Jurisdiction;
import com.fit.controller.base.BaseController;
import com.fit.entity.PageData;
import com.fit.entity.system.Menu;
import com.fit.entity.system.Role;
import com.fit.entity.system.User;
import com.fit.service.fhoa.datajur.DatajurManager;
import com.fit.service.system.appuser.AppuserManager;
import com.fit.service.system.buttonrights.ButtonrightsManager;
import com.fit.service.system.fhbutton.FhbuttonManager;
import com.fit.service.system.menu.MenuManager;
import com.fit.service.system.role.RoleManager;
import com.fit.service.system.user.UserManager;
import com.fit.util.*;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author AIM
 * @Des 总入口
 * @DATE 2018/12/30
 */
@Controller
public class IndexController extends BaseController {

    @Resource(name = "userService")
    private UserManager userService;
    @Resource(name = "menuService")
    private MenuManager menuService;
    @Resource(name = "roleService")
    private RoleManager roleService;
    @Resource(name = "buttonrightsService")
    private ButtonrightsManager buttonrightsService;
    @Resource(name = "fhbuttonService")
    private FhbuttonManager fhbuttonService;
    @Resource(name = "appuserService")
    private AppuserManager appuserService;
    @Resource(name = "datajurService")
    private DatajurManager datajurService;

    /**
     * 更新登录用户的IP
     */
    private void getRemortIP(String USERNAME) throws Exception {
        PageData pd = new PageData();
        HttpServletRequest request = this.getRequest();
        String ip = "";
        if (request.getHeader("x-forwarded-for") == null) {
            ip = request.getRemoteAddr();
        } else {
            ip = request.getHeader("x-forwarded-for");
        }
        pd.put("USERNAME", USERNAME);
        pd.put("IP", ip);
        userService.saveIP(pd);
    }

    /**
     * 访问系统首页
     *
     * @param menu ：切换菜单参数
     */
    @RequestMapping(value = "/main/{menu}")
    public ModelAndView login_index(HttpServletRequest request, @PathVariable("menu") String menu) {
        ModelAndView mv = this.getModelAndView(request);
        PageData pd = this.getPageData();
        try {
            Session session = Jurisdiction.getSession();
            User user = (User) session.getAttribute(Const.SESSION_USER); // 读取session中的用户信息(单独用户信息)
            if (user != null) {
                User userr = (User) session.getAttribute(Const.SESSION_USERROL); // 读取session中的用户信息(含角色信息)
                if (null == userr) {
                    user = userService.getUserAndRoleById(user.getUSER_ID()); // 通过用户ID读取用户信息和角色信息
                    session.setAttribute(Const.SESSION_USERROL, user); // 存入session
                } else {
                    user = userr;
                }
                String USERNAME = user.getUSERNAME();
                Role role = user.getRole(); // 获取用户角色
                String roleRights = role != null ? role.getRIGHTS() : ""; // 角色权限(菜单权限)
                session.setAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS, roleRights); // 将角色权限存入session
                session.setAttribute(Const.SESSION_USERNAME, USERNAME); // 放入用户名到session
                this.setAttributeToAllDEPARTMENT_ID(session, USERNAME); // 把用户的组织机构权限放到session里面
                List<Menu> allmenuList = new ArrayList<Menu>();
                allmenuList = this.getAttributeMenu(session, USERNAME, roleRights); // 菜单缓存
                List<Menu> menuList = new ArrayList<Menu>();
                menuList = this.changeMenuF(allmenuList, session, USERNAME, menu); // 切换菜单
                if (null == session.getAttribute(USERNAME + Const.SESSION_QX)) {
                    session.setAttribute(USERNAME + Const.SESSION_QX, this.getUQX(USERNAME));// 按钮权限放到session中
                }
                this.getRemortIP(USERNAME); // 更新登录IP
                mv.setViewName("/index");
                mv.addObject("user", user);
                mv.addObject("menuList", menuList);
            } else {
                mv.setViewName("/login");// session失效后跳转登录页面
            }
        } catch (Exception e) {
            mv.setViewName("/login");
            logger.error(e.getMessage(), e);
        }
        pd.put("SYSNAME", FileUtil.readTxtFile(Const.SYSNAME)); // 读取系统名称
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 切换菜单处理
     *
     * @param allmenuList 所有目录集合
     * @param session     用户session
     * @param USERNAME    用户名称
     * @param changeMenu  访问的目录
     */
    public List<Menu> changeMenuF(List<Menu> allmenuList, Session session, String USERNAME, String changeMenu) {
        List<Menu> menuList = new ArrayList<Menu>();
        if (null == session.getAttribute(USERNAME + Const.SESSION_menuList) || ("yes".equals(changeMenu))) {
            List<Menu> menuList1 = new ArrayList<Menu>();
            List<Menu> menuList2 = new ArrayList<Menu>();
            for (int i = 0; i < allmenuList.size(); i++) {// 拆分菜单
                Menu menu = allmenuList.get(i);
                if ("1".equals(menu.getMENU_TYPE())) {
                    menuList1.add(menu);
                } else {
                    menuList2.add(menu);
                }
            }
            session.removeAttribute(USERNAME + Const.SESSION_menuList);
            if ("2".equals(session.getAttribute("changeMenu"))) {
                session.setAttribute(USERNAME + Const.SESSION_menuList, menuList1);
                session.removeAttribute("changeMenu");
                session.setAttribute("changeMenu", "1");
                menuList = menuList1;
            } else {
                session.setAttribute(USERNAME + Const.SESSION_menuList, menuList2);
                session.removeAttribute("changeMenu");
                session.setAttribute("changeMenu", "2");
                menuList = menuList2;
            }
        } else {
            menuList = (List<Menu>) session.getAttribute(USERNAME + Const.SESSION_menuList);
        }
        return menuList;
    }

    /**
     * 获取用户权限
     *
     * @param USERNAME
     */
    public Map<String, String> getUQX(String USERNAME) {
        PageData pd = new PageData();
        Map<String, String> map = new HashMap<String, String>();
        try {
            pd.put(Const.SESSION_USERNAME, USERNAME);
            pd.put("ROLE_ID", userService.findByUsername(pd).get("ROLE_ID").toString());// 获取角色ID
            pd = roleService.findObjectById(pd); // 获取角色信息
            map.put("adds", pd.getString("ADD_QX")); // 增
            map.put("dels", pd.getString("DEL_QX")); // 删
            map.put("edits", pd.getString("EDIT_QX")); // 改
            map.put("chas", pd.getString("CHA_QX")); // 查
            List<PageData> buttonQXnamelist = new ArrayList<PageData>();
            if ("admin".equals(USERNAME)) {
                buttonQXnamelist = fhbuttonService.listAll(pd); // admin用户拥有所有按钮权限
            } else {
                buttonQXnamelist = buttonrightsService.listAllBrAndQxname(pd); // 此角色拥有的按钮权限标识列表
            }
            for (int i = 0; i < buttonQXnamelist.size(); i++) {
                map.put(buttonQXnamelist.get(i).getString("QX_NAME"), "1"); // 按钮权限
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return map;
    }

    /**
     * 菜单缓存
     *
     * @param session
     * @param USERNAME
     * @param roleRights
     */
    public List<Menu> getAttributeMenu(Session session, String USERNAME, String roleRights) throws Exception {
        List<Menu> allmenuList = new ArrayList<Menu>();
        if (null == session.getAttribute(USERNAME + Const.SESSION_allmenuList)) {
            allmenuList = menuService.listAllMenuQx("0"); // 获取所有菜单
            if (StringUtil.isNotEmpty(roleRights)) {
                allmenuList = this.readMenu(allmenuList, roleRights); // 根据角色权限获取本权限的菜单列表
            }
            session.setAttribute(USERNAME + Const.SESSION_allmenuList, allmenuList);// 菜单权限放入session中
        } else {
            allmenuList = (List<Menu>) session.getAttribute(USERNAME + Const.SESSION_allmenuList);
        }
        return allmenuList;
    }

    /**
     * 根据角色权限获取本权限的菜单列表(递归处理)
     *
     * @param menuList   ：传入的总菜单
     * @param roleRights ：加密的权限字符串
     */
    public List<Menu> readMenu(List<Menu> menuList, String roleRights) {
        for (int i = 0; i < menuList.size(); i++) {
            menuList.get(i).setHasMenu(RightsHelper.testRights(roleRights, menuList.get(i).getMENU_ID()));
            if (menuList.get(i).isHasMenu()) { // 判断是否有此菜单权限
                this.readMenu(menuList.get(i).getSubMenu(), roleRights);// 是：继续排查其子菜单
            }
        }
        return menuList;
    }

    /**
     * 把用户的组织机构权限放到session里面
     *
     * @param session
     * @param USERNAME
     */
    public void setAttributeToAllDEPARTMENT_ID(Session session, String USERNAME) throws Exception {
        String DEPARTMENT_IDS = "0", DEPARTMENT_ID = "0";
        if (!"admin".equals(USERNAME)) {
            PageData pd = datajurService.getDEPARTMENT_IDS(USERNAME);
            DEPARTMENT_IDS = null == pd ? "无权" : pd.getString("DEPARTMENT_IDS");
            DEPARTMENT_ID = null == pd ? "无权" : pd.getString("DEPARTMENT_ID");
        }
        session.setAttribute(Const.DEPARTMENT_IDS, DEPARTMENT_IDS); // 把用户的组织机构权限集合放到session里面
        session.setAttribute(Const.DEPARTMENT_ID, DEPARTMENT_ID); // 把用户的最高组织机构权限放到session里面
    }

    /**
     * 进入首页后的默认页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login_default")
    public ModelAndView defaultPage(HttpServletRequest request) throws Exception {
        ModelAndView mv = this.getModelAndView(request);
        PageData pd = new PageData();
        pd.put("userCount", Integer.parseInt(userService.getUserCount("").get("userCount").toString()) - 1); // 系统用户数
        pd.put("appUserCount", Integer.parseInt(appuserService.getAppUserCount("").get("appUserCount").toString())); // 会员数
        mv.addObject("pd", pd);
        mv.setViewName("/indexMain");
        return mv;
    }

    /**
     * @Author AIM
     * @Des 错误页面
     * @DATE 2018/12/30
     */
    @RequestMapping(value = "/404")
    public ModelAndView error404(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = this.getModelAndView(request);
        PageData pd = this.getPageData();
        mv.setViewName("/404");
        mv.addObject("pd", pd);
        return mv;
    }
}
