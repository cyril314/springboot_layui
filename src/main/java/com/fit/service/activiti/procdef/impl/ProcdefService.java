package com.fit.service.activiti.procdef.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fit.dao.DaoSupport;
import com.fit.entity.Page;
import com.fit.entity.PageData;
import com.fit.service.activiti.procdef.ProcdefManager;

/** 
 * 说明： 流程管理
 * 创建人：
 * 创建时间：2018-01-06
 * @version
 */
@Service("procdefService")
public class ProcdefService implements ProcdefManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ProcdefMapper.datalistPage", page);
	}
	
}

