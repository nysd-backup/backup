/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.internal.orm.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import kosmos.framework.jpqlclient.api.EntityManagerProvider;
import kosmos.framework.sqlclient.api.free.FreeParameter;
import kosmos.framework.sqlclient.api.free.FreeQueryParameter;
import kosmos.framework.sqlclient.api.free.FreeUpdateParameter;
import kosmos.framework.sqlclient.api.orm.OrmParameter;
import kosmos.framework.sqlclient.api.orm.OrmQueryParameter;
import kosmos.framework.sqlclient.api.orm.OrmUpdateParameter;
import kosmos.framework.sqlclient.internal.free.InternalQuery;
import kosmos.framework.sqlclient.internal.orm.InternalOrmQuery;
import kosmos.framework.sqlclient.internal.orm.SQLStatementBuilder;
import kosmos.framework.sqlclient.internal.orm.SQLStatementBuilder.Bindable;


/**
 * Creates the JPQL and execute 
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class InternalOrmJpaQueryImpl implements InternalOrmQuery {
	
	/** the SQLStatementBuilder */
	private SQLStatementBuilder sb = new JPQLStatementBuilderImpl();

	/** the EntityManager */
	private EntityManager em = null;
	
	/** the internal query */
	private InternalQuery internalNamedQuery;

	/**
	 * @param provider the provider to set
	 */
	public void setEntityManagerProvider(EntityManagerProvider provider){
		em = provider.getEntityManager();		
	}	
	
	/**
	 * @param internalNamedQuery the internalNamedQuery to set
	 */
	public void setInternalNamedQuery(InternalQuery internalNamedQuery){
		this.internalNamedQuery = internalNamedQuery;
	}
	
	/**
	 * @param sb the sb to set
	 */
	public void setSqlStatementBuilder(SQLStatementBuilder sb){
		this.sb = sb;
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.orm.InternalOrmQuery#insert(kosmos.framework.sqlclient.api.orm.OrmParameter, java.util.Map)
	 */
	@Override
	public int insert(OrmUpdateParameter<?> entity) {
		throw new UnsupportedOperationException("Dont' use this method. Use 'EntityManager#persist' instead");
	}
	
	/**
	 * @see kosmos.framework.sqlclient.internal.orm.InternalOrmQuery#update(kosmos.framework.sqlclient.api.orm.OrmParameter, java.util.Map)
	 */
	@Override
	public int update(OrmUpdateParameter<?> condition){

		String updateJpql = sb.createUpdate(condition.getEntityClass(),condition.getFilterString(),condition.getConditions(), condition.getCurrentValues());
		final FreeUpdateParameter parameter = new FreeUpdateParameter(false, condition.getEntityClass().getSimpleName() + ".update", updateJpql);
	
		setParameterAndHint(condition,parameter);

		for(Map.Entry<String, Object> s : condition.getCurrentValues().entrySet()){
			parameter.getParam().put(s.getKey(),s.getValue());			
		}		
		
		return internalNamedQuery.executeUpdate(parameter);
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.orm.InternalOrmQuery#delete(kosmos.framework.sqlclient.api.orm.OrmParameter)
	 */
	@Override
	public  int delete(OrmUpdateParameter<?> condition){		
	
		String updateJpql = sb.createDelete(condition.getEntityClass(),condition.getFilterString(),condition.getConditions());
		final FreeUpdateParameter parameter = new FreeUpdateParameter(false, condition.getEntityClass().getSimpleName() + ".delete", updateJpql);
		setParameterAndHint(condition,parameter);

		return internalNamedQuery.executeUpdate(parameter);
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.orm.InternalOrmQuery#find(kosmos.framework.sqlclient.api.orm.OrmQueryParameter, java.lang.Object[])
	 */
	@Override
	public <E> E find(OrmQueryParameter<E> query,Object... pks) {
		Object v = pks;
		if( pks.length == 1){
			v = pks[0];
		}
		E result = null;
		if(query.getLockModeType() != null){
			result = (E)em.find(query.getEntityClass(),v,query.getLockModeType(),query.getHints());
		}else{
			result = (E)em.find(query.getEntityClass(),v,query.getHints()); 
		}
		return result;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.internal.orm.InternalOrmQuery#getResultList(kosmos.framework.sqlclient.api.orm.OrmQueryParameter)
	 */
	@Override
	public <E> List<E> getResultList(OrmQueryParameter<E> condition){	

		String jpql = sb.createSelect(condition);
		
		final FreeQueryParameter parameter = new FreeQueryParameter(condition.getEntityClass(), false, condition.getEntityClass().getSimpleName() + ".select", jpql);
		setParameterAndHint(condition,parameter);
		
		parameter.setFirstResult(condition.getFirstResult());
		parameter.setMaxSize(condition.getMaxSize());		
		parameter.setLockMode(condition.getLockModeType());
		
		return internalNamedQuery.getResultList(parameter);
	}

	/**
	 * Set the parameter and hint.
	 * 
	 * @param condition
	 * @param parameter
	 */
	private void setParameterAndHint(OrmParameter<?> condition,final FreeParameter parameter ){
		for(Map.Entry<String, Object> e : condition.getHints().entrySet()){
			parameter.getHints().put(e.getKey(), e.getValue());
		}
		
		sb.setConditionParameters(condition.getFilterString(),condition.getEasyParams(),condition.getConditions(), new Bindable(){
			public void setParameter(String key , Object value){
				parameter.getParam().put(key, value);
			}
		});
		
	}
	
	/**
	 * @see kosmos.framework.sqlclient.internal.orm.InternalOrmQuery#batchUpdate(kosmos.framework.sqlclient.api.orm.OrmUpdateParameter)
	 */
	@Override
	public int[] batchUpdate(OrmUpdateParameter<?> parameter) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see kosmos.framework.sqlclient.internal.orm.InternalOrmQuery#batchInsert(kosmos.framework.sqlclient.api.orm.OrmUpdateParameter)
	 */
	@Override
	public int[] batchInsert(OrmUpdateParameter<?> parameter) {
		throw new UnsupportedOperationException();
	}

}
