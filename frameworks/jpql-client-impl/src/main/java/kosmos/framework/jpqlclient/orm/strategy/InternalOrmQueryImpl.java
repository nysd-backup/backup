/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.orm.strategy;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import kosmos.framework.jpqlclient.EntityManagerProvider;
import kosmos.framework.sqlclient.free.FreeParameter;
import kosmos.framework.sqlclient.free.FreeQueryParameter;
import kosmos.framework.sqlclient.free.FreeUpdateParameter;
import kosmos.framework.sqlclient.free.strategy.InternalQuery;
import kosmos.framework.sqlclient.orm.OrmParameter;
import kosmos.framework.sqlclient.orm.OrmQueryParameter;
import kosmos.framework.sqlclient.orm.OrmUpdateParameter;
import kosmos.framework.sqlclient.orm.strategy.InternalOrmQuery;
import kosmos.framework.sqlclient.orm.strategy.SQLStatementBuilder;
import kosmos.framework.sqlclient.orm.strategy.SQLStatementBuilder.Bindable;


/**
 * Creates the JPQL and execute 
 *
 * @author	yoshida-n
 * @version 2011/08/31 created.
 */
public class InternalOrmQueryImpl implements InternalOrmQuery {
	
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
	 * @see kosmos.framework.sqlclient.orm.strategy.InternalOrmQuery#update(kosmos.framework.sqlclient.orm.OrmParameter, java.util.Map)
	 */
	@Override
	public int update(OrmUpdateParameter<?> condition){

		String updateJpql = sb.createUpdate(condition.getEntityClass(),condition.getConditions(), condition.getCurrentValues());
		final FreeUpdateParameter parameter = new FreeUpdateParameter();
		parameter.setSql(updateJpql);
		parameter.setQueryId(condition.getEntityClass().getSimpleName() + ".update");
	
		setParameterAndHint(condition,parameter);

		for(Map.Entry<String, Object> s : condition.getCurrentValues().entrySet()){
			parameter.getParam().put(s.getKey(),s.getValue());			
		}		
		
		return internalNamedQuery.executeUpdate(parameter);
	}

	/**
	 * @see kosmos.framework.sqlclient.orm.strategy.InternalOrmQuery#delete(kosmos.framework.sqlclient.orm.OrmParameter)
	 */
	@Override
	public int delete(OrmUpdateParameter<?> condition){		
	
		String updateJpql = sb.createDelete(condition.getEntityClass(),condition.getConditions());
		final FreeUpdateParameter parameter = new FreeUpdateParameter();
		parameter.setSql(updateJpql);
		parameter.setQueryId(condition.getEntityClass().getSimpleName() + ".delete");
	
		setParameterAndHint(condition,parameter);

		return internalNamedQuery.executeUpdate(parameter);
	}

	/**
	 * @see kosmos.framework.sqlclient.orm.strategy.InternalOrmQuery#find(kosmos.framework.sqlclient.orm.OrmQueryParameter, java.lang.Object[])
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
	 * @see kosmos.framework.sqlclient.orm.strategy.InternalOrmQuery#getResultList(kosmos.framework.sqlclient.orm.OrmQueryParameter)
	 */
	@Override
	public <E> List<E> getResultList(OrmQueryParameter<E> condition){	

		String jpql = sb.createSelect(condition);
		
		final FreeQueryParameter parameter = new FreeQueryParameter();
		parameter.setResultType(condition.getEntityClass());
		parameter.setQueryId(condition.getEntityClass().getSimpleName() + ".select");
		parameter.setSql(jpql);
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
		
		sb.setConditionParameters(condition.getConditions(), new Bindable(){
			public void setParameter(String key , Object value){
				parameter.getParam().put(key, value);
			}
		});
		
	}
	
	/**
	 * @see kosmos.framework.sqlclient.orm.strategy.InternalOrmQuery#getFetchResult(kosmos.framework.sqlclient.orm.OrmQueryParameter)
	 */
	@Override
	public <E> List<E> getFetchResult(OrmQueryParameter<E> parameter) {
		throw new UnsupportedOperationException();
	}

}
