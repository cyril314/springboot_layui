package com.fit.service.activiti.fhmodel.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fit.dao.DaoSupport;
import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.service.activiti.fhmodel.FHModelManager;

/** 
 * 说明： 工作流模型管理
 * 创建人：
 * 创建时间：2017-12-26
 * @version
 */
@Service("fhmodelService")
public class FHModelService implements FHModelManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("FHModelMapper.edit", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("FHModelMapper.findById", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("FHModelMapper.datalistPage", page);
	}
	
}

