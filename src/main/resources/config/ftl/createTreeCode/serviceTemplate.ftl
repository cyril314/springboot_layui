package com.aim.service.${packageName}.${objectNameLower}.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.aim.dao.DaoSupport;
import com.aim.entity.Page;
import com.aim.entity.${packageName}.${objectName};
import com.aim.util.PageData;
import com.aim.service.${packageName}.${objectNameLower}.${objectName}Manager;

/** 
 * 说明： ${TITLE}
 * 创建人：
 * 创建时间：${nowDate?string("yyyy-MM-dd")}
 * @version
 */
@Service("${objectNameLower}Service")
public class ${objectName}Service implements ${objectName}Manager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("${objectName}Mapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("${objectName}Mapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("${objectName}Mapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("${objectName}Mapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("${objectName}Mapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("${objectName}Mapper.findById", pd);
	}

	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<${objectName}> listByParentId(String parentId) throws Exception {
		return (List<${objectName}>) dao.findForList("${objectName}Mapper.listByParentId", parentId);
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<${objectName}> listTree(String parentId) throws Exception {
		List<${objectName}> valueList = this.listByParentId(parentId);
		for(${objectName} entity : valueList){
			entity.setTreeurl("${objectNameLower}/list.do?${objectNameUpper}_ID="+entity.get${objectNameUpper}_ID());
			entity.setSub${objectName}(this.listTree(entity.get${objectNameUpper}_ID()));
			entity.setTarget("treeFrame");
		}
		return valueList;
	}
		
}

