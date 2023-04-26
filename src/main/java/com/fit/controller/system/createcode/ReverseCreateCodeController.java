package com.fit.controller.system.createcode;

import com.fit.config.shiro.Jurisdiction;
import com.fit.controller.base.BaseController;
import com.fit.entity.PageData;
import com.fit.entity.system.RecreateCode;
import com.fit.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名称： 反向生成
 * 创建人：
 * 修改时间：2016年4月15日
 */
@Controller
@RequestMapping(value = "/recreateCode")
public class ReverseCreateCodeController extends BaseController {

    String menuUrl = "recreateCode/list.do"; //菜单地址(权限用)

    /**
     * 列表
     */
    @RequestMapping(value = "/list")
    public ModelAndView list() throws Exception {
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
        }    //校验权限
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("system/createcode/recreatecode_list");
        mv.addObject("QX", Jurisdiction.getHC());    //按钮权限
        return mv;
    }

    /**
     * 列出所有表
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/listAllTable")
    @ResponseBody
    public Object listAllTable() {
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        } //校验权限
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String, Object> map = new HashMap<String, Object>();
        List<PageData> pdList = new ArrayList<PageData>();
        List<String> tblist = new ArrayList<String>();
        try {
            Object[] arrOb = DbFH.getTables(pd);
            tblist = (List<String>) arrOb[1];
            pd.put("msg", "ok");
        } catch (ClassNotFoundException e) {
            pd.put("msg", "no");
            e.printStackTrace();
        } catch (SQLException e) {
            pd.put("msg", "no");
        }
        pdList.add(pd);
        map.put("tblist", tblist);
        map.put("list", pdList);
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 去代码生成器页面(进入弹窗)
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goProductCode")
    public ModelAndView goProductCode() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String fieldType = "";
        List<Map<String, String>> columnList = DbFH.getFieldParameterLsit(DbFH.getFHCon(pd), pd.getString("table")); //读取字段信息
        List<RecreateCode> list = new ArrayList<>();
        for (int i = 0; i < columnList.size(); i++) {
            Map<String, String> fmap = columnList.get(i);
            RecreateCode rcc = new RecreateCode();
            rcc.setPROPERTYNAME(fmap.get("fieldNanme").toString().toUpperCase());
            fieldType = fmap.get("fieldType").toString().toLowerCase();                    //字段类型
            if (fieldType.contains("int")) {
                rcc.setTYPENAME("Integer");
            } else if (fieldType.contains("NUMBER")) {
                if (Integer.parseInt(fmap.get("fieldSccle")) > 0) {
                    rcc.setTYPENAME("Double");
                } else {
                    rcc.setTYPENAME("Integer");
                }
            } else if (fieldType.contains("double") || fieldType.contains("numeric")) {
                rcc.setTYPENAME("Double");
            } else if (fieldType.contains("date")) {
                rcc.setTYPENAME("Date");
            } else {
                rcc.setTYPENAME("String");
            }

            rcc.setBZ("备注" + (i + 1));//备注
            rcc.setEXTENT(fmap.get("fieldLength").toString());//长度
            rcc.setDECIMALPOINT(fmap.get("fieldSccle").toString());//小数点右边的位数
            rcc.setISFRONT("是");//是否前台录入
            rcc.setDEFAULTVALUE("无");//默认值
            list.add(rcc);
        }
        pd.put("FIELDLIST", JsonUtils.toJson(list));
        mv.addObject("msg", "edit");
        mv.addObject("pd", pd);
        mv.setViewName("system/createcode/productCode");
        return mv;
    }

}