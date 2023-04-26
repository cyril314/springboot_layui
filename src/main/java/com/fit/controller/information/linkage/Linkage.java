package com.fit.controller.information.linkage;

import com.fit.controller.base.BaseController;
import com.fit.entity.system.Dictionaries;
import com.fit.service.system.dictionaries.DictionariesManager;
import com.fit.util.AppUtil;
import com.fit.entity.PageData;
import com.fit.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 说明：4级联动
 */
@Controller
@RequestMapping(value = "/linkage")
public class Linkage extends BaseController {

    String menuUrl = "linkage/view.do"; //菜单地址(权限用)

    @Resource(name = "dictionariesService")
    private DictionariesManager dictionariesService;

    /**
     * 去新增页面
     */
    @RequestMapping(value = "/view")
    public ModelAndView goAdd() throws Exception {
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("information/linkage/view");
        return mv;
    }

    /**
     * 获取连级数据
     */
    @RequestMapping(value = "/getLevels")
    @ResponseBody
    public Object getLevels() {
        Map<String, Object> map = new HashMap<String, Object>();
        String errInfo = "success";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String DICTIONARIES_ID = pd.getString("DICTIONARIES_ID");
            DICTIONARIES_ID = StringUtil.isEmpty(DICTIONARIES_ID) ? "0" : DICTIONARIES_ID;
            List<Dictionaries> varList = dictionariesService.listSubDictByParentId(DICTIONARIES_ID); //用传过来的ID获取此ID下的子列表数据
            List<PageData> pdList = new ArrayList<PageData>();
            for (Dictionaries d : varList) {
                PageData pdf = new PageData();
                pdf.put("DICTIONARIES_ID", d.getDICTIONARIES_ID());
                pdf.put("BIANMA", d.getBIANMA());
                pdf.put("NAME", d.getNAME());
                pdList.add(pdf);
            }
            map.put("list", pdList);
        } catch (Exception e) {
            errInfo = "error";
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);                //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }

}
