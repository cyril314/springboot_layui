package com.fit.controller.system;

import com.fit.config.shiro.Jurisdiction;
import com.fit.controller.base.BaseController;
import com.fit.entity.PageData;
import com.fit.entity.system.User;
import com.fit.service.system.fhlog.FHlogManager;
import com.fit.service.system.user.UserManager;
import com.fit.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author AIM
 * @Des 登录控制器
 * @DATE 2018/12/30
 */
@Controller
public class LoginController extends BaseController {

    @Resource(name = "userService")
    private UserManager userService;
    @Resource(name = "fhlogService")
    private FHlogManager FHLOG;
    @Value("${debug}")
    private boolean isDebug = false;

    /**
     * 访问登录页
     */
    @GetMapping(value = "/login")
    public ModelAndView toLogin(HttpServletRequest request) throws Exception {
        ModelAndView mv = this.getModelAndView(request);
        PageData pd = this.getPageData();
        pd.put("SYSNAME", FileUtil.readTxtFile(Const.SYSNAME)); // 读取系统名称
        mv.setViewName("/login");
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 请求登录，验证用户
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public Object login() throws Exception {
        boolean toVerify = false;
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = this.getPageData();
        String errInfo = "";
        Integer errCode = 1000;
        String KEYDATA[] = pd.getString("KEYDATA").replaceAll("qq313596790fh", "").replaceAll("QQ978336446fh", "").split(",fh,");
        if (null != KEYDATA && KEYDATA.length == 3) {
            Session session = Jurisdiction.getSession();
            // 判断登录验证码
            if (!isDebug) {
                String code = KEYDATA[2];
                if (null == code || "".equals(code)) {//判断效验码
                    errInfo = "nullcode";    //效验码为空
                }
                String sessionCode = (String) session.getAttribute(Const.SESSION_SECURITY_CODE); // 获取session中的验证码
                if (StringUtil.isNotEmpty(sessionCode) && !sessionCode.equalsIgnoreCase(code)) {
                    errInfo = "验证码输入有误"; // 验证码输入有误
                } else {
                    toVerify = true;
                }
            } else {
                toVerify = true;
            }
            if (toVerify) {
                String USERNAME = KEYDATA[0]; // 登录过来的用户名
                String PASSWORD = KEYDATA[1]; // 登录过来的密码
                pd.put("USERNAME", USERNAME);
                String passwd = new SimpleHash("SHA-1", USERNAME, PASSWORD).toString(); // 密码加密
                pd.put("PASSWORD", passwd);
                pd = userService.getUserByNameAndPwd(pd); // 根据用户名和密码去读取用户信息
                if (pd != null) {
                    pd.put("LAST_LOGIN", DateUtil.getTime());
                    userService.updateLastLogin(pd);
                    User user = new User();
                    user.setUSER_ID(pd.getString("USER_ID"));
                    user.setUSERNAME(pd.getString("USERNAME"));
                    user.setPASSWORD(pd.getString("PASSWORD"));
                    user.setNAME(pd.getString("NAME"));
                    user.setRIGHTS(pd.getString("RIGHTS"));
                    user.setROLE_ID(pd.getString("ROLE_ID"));
                    user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
                    user.setIP(pd.getString("IP"));
                    user.setSTATUS(pd.getString("STATUS"));
                    session.setAttribute(Const.SESSION_USER, user); // 把用户信息放session中
                    session.removeAttribute(Const.SESSION_SECURITY_CODE); // 清除登录验证码的session
                    // shiro加入身份验证
                    Subject subject = SecurityUtils.getSubject();
                    UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD);
                    try {
                        subject.login(token);
                    } catch (AuthenticationException e) {
                        errInfo = "身份验证失败！";
                    }
                } else {
                    errInfo = "用户名或密码有误"; // 用户名或密码有误
                    FHLOG.save(USERNAME, "登录系统密码或用户名错误");
                }
                if (StringUtil.isEmpty(errInfo)) {
                    errInfo = "验证成功"; // 验证成功
                    errCode = 0;
                    FHLOG.save(USERNAME, "登录系统");
                }
            }
        } else {
            errInfo = "缺少参数"; // 缺少参数
        }
        map.put("msg", errInfo);
        map.put("code", errCode);
        return AppUtil.returnObject(new PageData(), map);
    }

    /**
     * 用户注销
     */
    @RequestMapping(value = "/logout")
    public ModelAndView logout() {
        ModelAndView mv = this.getModelAndView();
        String USERNAME = Jurisdiction.getUsername(); // 当前登录的用户名
        if (StringUtil.isNotEmpty(USERNAME)) {
            logBefore(logger, USERNAME + "退出系统");
            Session session = Jurisdiction.getSession(); // 以下清除session缓存
            session.removeAttribute(Const.SESSION_USER);
            session.removeAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS);
            session.removeAttribute(USERNAME + Const.SESSION_allmenuList);
            session.removeAttribute(USERNAME + Const.SESSION_menuList);
            session.removeAttribute(USERNAME + Const.SESSION_QX);
            session.removeAttribute(Const.SESSION_userpds);
            session.removeAttribute(Const.SESSION_USERNAME);
            session.removeAttribute(Const.SESSION_USERROL);
            session.removeAttribute("changeMenu");
            session.removeAttribute("DEPARTMENT_IDS");
            session.removeAttribute("DEPARTMENT_ID");
        }
        // shiro销毁登录
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        PageData pd = this.getPageData();
        pd.put("msg", pd.getString("msg"));
        pd.put("SYSNAME", FileUtil.readTxtFile(Const.SYSNAME)); // 读取系统名称
        mv.setViewName("/login");
        mv.addObject("pd", pd);
        return mv;
    }
}
