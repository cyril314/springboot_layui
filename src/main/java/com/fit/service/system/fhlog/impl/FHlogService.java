package com.fit.service.system.fhlog.impl;

import com.fit.dao.DaoSupport;
import com.fit.entity.Page;
import com.fit.service.system.fhlog.FHlogManager;
import com.fit.entity.PageData;
import com.fit.util.DateUtil;
import com.fit.util.UuidUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 说明： 操作日志记录
 * 创建人：
 * 创建时间：2016-05-10
 */
@Service("fhlogService")
public class FHlogService implements FHlogManager {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * 新增
     */
    public void save(String USERNAME, String CONTENT) throws Exception {
        PageData pd = new PageData();
        pd.put("USERNAME", USERNAME);                    //用户名
        pd.put("CONTENT", CONTENT);                        //事件
        pd.put("FHLOG_ID", UuidUtil.get32UUID());        //主键
        pd.put("CZTIME", DateUtil.getTime());    //操作时间
        dao.save("FHlogMapper.save", pd);
    }

    /**
     * 删除
     *
     * @param pd
     * @throws Exception
     */
    public void delete(PageData pd) throws Exception {
        dao.delete("FHlogMapper.delete", pd);
    }

    /**
     * 列表
     *
     * @param page
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<PageData> list(Page page) throws Exception {
        return (List<PageData>) dao.findForList("FHlogMapper.datalistPage", page);
    }

    /**
     * 列表(全部)
     *
     * @param pd
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<PageData> listAll(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("FHlogMapper.listAll", pd);
    }

    /**
     * 通过id获取数据
     *
     * @param pd
     * @throws Exception
     */
    public PageData findById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("FHlogMapper.findById", pd);
    }

    /**
     * 批量删除
     *
     * @param ArrayDATA_IDS
     * @throws Exception
     */
    public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
        dao.delete("FHlogMapper.deleteAll", ArrayDATA_IDS);
    }

}

