/**
 * Copyright 2011 the original author
 */
package showcase.basic;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.LockModeType;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import client.sql.orm.OrmSelect;
import client.sql.orm.OrmUpdate;
import client.sql.orm.PersistenceManager;

import service.framework.base.AbstractCoreService;
import showcase.entity.GeneratedChildEntity;
import showcase.entity.GeneratedEntity;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@Transactional
@Service
public class ShowcaseService extends AbstractCoreService{
	
	private static final int LIMIT = 1000;
	
	@Autowired
	private PersistenceManager persister;
	
	/**
	 * @param attrList
	 * @return
	 */
	public List<GeneratedEntity> search(List<String> attrList) {
		
		//検索
		OrmSelect<GeneratedEntity> query = createOrmSelect(GeneratedEntity.class);
		query.contains(GeneratedEntity.ATTR, attrList).setMaxResults(LIMIT+1);
		List<GeneratedEntity> result = query.getResultList();
		
		//検索結果0件ならシステムエラー
		assertExists(result);
		
		//検索結果超過ならエラー
		throwIfOverLimit(createMessage("OVER_LIMIT"),result, LIMIT);
		
		return result;
	}
	
	/**
	 * @param attrList
	 * @return
	 */
	public void update(List<GeneratedEntity> updateTarget) {
		
		for(GeneratedEntity e : updateTarget){
			GeneratedEntity result = createOrmSelect(GeneratedEntity.class).setLockMode(LockModeType.PESSIMISTIC_READ).find(e.getPk());			
			long generatedId = -1;
			e.setPk(String.valueOf(generatedId));
			persister.persist(e, null);
			if(result != null){
				//最新フラグを落とす
				GeneratedEntity updating = result.clone();
				updating.setAttr2(0L);
				persister.merge(updating, result, null);
				
				//子テーブルの履歴番号を落とす
				OrmUpdate<GeneratedChildEntity> update = createOrmUpdate(GeneratedChildEntity.class);
				update.eq(GeneratedChildEntity.PK,e.getPk()).eq(GeneratedChildEntity.ATTR2, 1L).update();
			}			
		}
	}
	
	/**
	 * @param attrList
	 * @return
	 */
	public void batchUpdate(List<GeneratedEntity> updateTarget) {
		
		List<GeneratedEntity> updatable = new ArrayList<GeneratedEntity>();
		List<GeneratedEntity> insertable = new ArrayList<GeneratedEntity>();
		
		for(GeneratedEntity e : updateTarget){
			GeneratedEntity result = createOrmSelect(GeneratedEntity.class).find(e.getPk());			
			if(result == null){
				insertable.add(e);
			}else{
				updatable.add(e);
			}			
		}
		persister.batchPersist(insertable, null);
		persister.batchPersist(updatable, null);
	}
	
	
}
