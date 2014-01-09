/**
 * 
 */
package org.coder.sample.s2jdbc.repository;

import javax.annotation.Resource;

import org.coder.sample.s2jdbc.domain.Parent;
import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.extension.jdbc.where.SimpleWhere;

/**
 * @author yoshida-n
 *
 */
public class ParentRepository{

	@Resource
	private JdbcManager jdbcManager;
	
	/**
	 * @param entity
	 */
	public void persist(Parent entity){		
		jdbcManager.deleteBatch(entity.child).execute();
		jdbcManager.delete(entity).execute();		
		jdbcManager.insert(entity).execute();		
		jdbcManager.insertBatch(entity.child).execute();
	}	
	
	/**
	 * @param id 主キー
	 * @return 親エンティティ
	 */
	public Parent find(String id){
		return jdbcManager.from(Parent.class).innerJoin("child").where(new SimpleWhere().eq("id",id)).getSingleResult();
	}	

}
