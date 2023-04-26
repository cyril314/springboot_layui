package com.fit.util;

import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 项目名称：
 */
public class Const {

    public static final String SESSION_SECURITY_CODE = "Session_Sec_Code";//验证码
    public static final String SESSION_USER = "Session_User";            //session用的用户
    public static final String SESSION_ROLE_RIGHTS = "Session_Role_Rights";
    public static final String SHIROSET = "SHIROSET";                               //菜单权限标识
    public static final String SESSION_menuList = "menuList";                //当前菜单
    public static final String SESSION_allmenuList = "allmenuList";            //全部菜单
    public static final String SESSION_QX = "QX";                            //主职角色权限
    public static final String SESSION_QX2 = "QX2";                            //副职角色权限
    public static final String SESSION_userpds = "userpds";
    public static final String SESSION_USERROL = "USERROL";                    //用户对象
    public static final String SESSION_USERNAME = "USERNAME";                //用户名
    public static final String SESSION_U_NAME = "U_NAME";                    //用户姓名
    public static final String SESSION_RNUMBERS = "RNUMBERS";                //角色编码数组
    public static final String DEPARTMENT_IDS = "DEPARTMENT_IDS";            //当前用户拥有的最高部门权限集合
    public static final String DEPARTMENT_ID = "DEPARTMENT_ID";                //当前用户拥有的最高部门权限
    public static final String TRUE = "T";
    public static final String FALSE = "F";
    public static final String SKIN = "SKIN";                                //用户皮肤
    public static final String LOGIN = "/login";                    //登录地址
    public static final String SYSNAME = "config/SYSNAME.txt";        //系统名称路径
    public static final String PAGE = "config/PAGE.txt";                //分页条数配置路径
    public static final String EMAIL = "config/EMAIL.txt";            //邮箱服务器配置路径
    public static final String SMS1 = "config/SMS1.txt";                //短信账户配置路径1
    public static final String SMS2 = "config/SMS2.txt";                //短信账户配置路径2
    public static final String FWATERM = "config/FWATERM.txt";        //文字水印配置路径
    public static final String IWATERM = "config/IWATERM.txt";        //图片水印配置路径
    public static final String WEIXIN = "config/WEIXIN.txt";        //微信配置路径
    public static final String WEBSOCKET = "config/WEBSOCKET.txt";    //WEBSOCKET配置路径
    public static final String LOGINEDIT = "config/LOGIN.txt";        //登录页面配置
    public static final String FILEPATHIMG = "uploadFiles/uploadImgs/";        //图片上传路径
    public static final String FILEPATHFILE = "uploadFiles/file/";            //文件上传路径
    public static final String FILEPATHFILEOA = "uploadFiles/uploadFile/";    //文件上传路径(oa管理)
    public static final String FILEACTIVITI = "uploadFiles/activitiFile/";    //工作流生成XML和PNG目录
    public static final String FILEPATHTWODIMENSIONCODE = "uploadFiles/twoDimensionCode/"; //二维码存放路径
    public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)|(app)|(weixin)|(static)|(main)|(websocket)|(uploadImgs)).*";    //不对匹配该值的访问路径拦截（正则）
    public static ApplicationContext WEB_APP_CONTEXT = null; //该值会在web容器启动时由WebAppContextListener初始化

    /**
     * APP Constants
     */
    //app注册接口_请求协议参数)
    public static final String[] APP_REGISTERED_PARAM_ARRAY = new String[]{"countries", "uname", "passwd", "title", "full_name", "company_name", "countries_code", "area_code", "telephone", "mobile"};
    public static final String[] APP_REGISTERED_VALUE_ARRAY = new String[]{"国籍", "邮箱帐号", "密码", "称谓", "名称", "公司名称", "国家编号", "区号", "电话", "手机号"};
    //系统用户注册接口_请求协议参数)
    public static final String[] SYSUSER_REGISTERED_PARAM_ARRAY = new String[]{"USERNAME", "PASSWORD", "NAME", "EMAIL", "rcode"};
    public static final String[] SYSUSER_REGISTERED_VALUE_ARRAY = new String[]{"用户名", "密码", "姓名", "邮箱", "验证码"};
    //app根据用户名获取会员信息接口_请求协议中的参数
    public static final String[] APP_GETAPPUSER_PARAM_ARRAY = new String[]{"USERNAME"};
    public static final String[] APP_GETAPPUSER_VALUE_ARRAY = new String[]{"用户名"};

    /**
     * 系统默认的分页大小
     *
     * @author fujl 2017-5-18 ++
     */
    public static final Integer PAGESIZE_DEFAULT = 10;
    public static final String REGEX_NUMBER = "^([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])|(-?[0])|([0]\\.\\d*)$";

    /**
     * 存储调用接口所有token数据
     */
    public static final ConcurrentHashMap<String, Object> TOKENMAP = new ConcurrentHashMap<String, Object>();
    public static final long EFFECTIVETIME = 3600000;// 默认token有效时间（毫秒）

    //时间格式
    public static final String FORMAT_YYYYMMDDHHMMSS_STRING = "yyyy-MM-dd hh:mm:ss";
    public static final String FORMAT_YYYYMMDD_STRING = "yyyy-MM-dd";

    //增加下载设备
    public static final List<String> APPEUIARRAY = Arrays.asList("ENOSEDATA");

    //臭气类型（二氧化硫等等）10为臭气
    public static final String ENOSEDATA_CODE_10 = "10";

    //ENOSEDATA_HTTP_URL电子鼻数据下载对接http协议
    public static final String ENOSEDATA_HTTP_URL = "https://loraflow.io/v1/application/data?appeui=1f8ea4173606487c&token=1v7zub023bd4372d6f04b96bb47ba&order=desc";
    //ENOSEDATA_WSS_URL电子鼻数据下载对接websocket协议
    public static final String ENOSEDATA_WSS_URL = "wss://loraflow.io/v1/application/ws?appeui=1f8ea4173606487c&token=1v7zub023bd4372d6f04b96bb47ba&order=desc";
    //key为enoseserialno，value是wss地址
    public static final ConcurrentHashMap<String, Object> ENOSEDATANMAP = new ConcurrentHashMap<String, Object>();

    public static final String ENOSEDATA_STATUS_100 = "100";//启用

    public static final Double LOCATION_SCOPE = 1000.0;//方圆距离

    //上传数据条数
    public static final int AVG_DAYCOUNT = 24;

    //短信模板Code配置
    public static final String SMS_TEMPLATE_TYPE_1 = "1";//1代表注册
    public static final String SMS_TEMPLATE_REGISTER = "39576";//注册
    public static final String SMS_TEMPLATE_TYPE_2 = "2";//2代表修改
    public static final String SMS_TEMPLATE_FORGET = "39570";//修改

    public static final Integer SMS_EXPIRETIME = 10;//短信实现时间（10分钟）
}
