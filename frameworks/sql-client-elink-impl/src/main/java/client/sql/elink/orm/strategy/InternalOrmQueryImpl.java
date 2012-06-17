/**
 * Copyright 2011 the original author
 */
package client.sql.elink.orm.strategy;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import client.sql.elink.EntityManagerProvider;
import client.sql.free.FreeQueryParameter;
import client.sql.free.FreeSelectParameter;
import client.sql.free.FreeUpsertParameter;
import client.sql.free.strategy.InternalQuery;
import client.sql.orm.OrmQueryParameter;
import client.sql.orm.OrmSelectParameter;
import client.sql.orm.OrmUpdateParameter;
import client.sql.orm.strategy.InternalOrmQuery;
import client.sql.orm.strategy.SQLStatementBuilder;
import client.sql.orm.strategy.SQLStatementBuilder.Bindable;





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
	 * @see client.sql.orm.strategy.InternalOrmQuery#update(client.sql.orm.OrmQueryParameter, java.util.Map)
	 */
	@Override
	public int update(OrmUpdateParameter<?> condition){

		String updateJpql = sb.createUpdate(condition.getEntityClass(),condition.getConditions(), condition.getCurrentValues());
		final FreeUpsertParameter parameter = new FreeUpsertParameter();
		parameter.setSql(updateJpql);
		parameter.setQueryId(condition.getEntityClass().getSimpleName() + ".update");
	
		setParameterAndHint(condition,parameter);

		for(Map.Entry<String, Object> s : condition.getCurrentValues().entrySet()){
			parameter.getParam().put(s.getKey(),s.getValue());			
		}		
		
		return internalNamedQuery.executeUpdate(parameter);
	}

	/**
	 * @see client.sql.orm.strategy.InternalOrmQuery#delete(client.sql.orm.OrmQueryParameter)
	 */
	@Override
	public int delete(OrmUpdateParameter<?> condition){		
	
		String updateJpql = sb.createDelete(condition.getEntityClass(),condition.getConditions());
		final FreeUpsertParameter parameter = new FreeUpsertParameter();
		parameter.setSql(updateJpql);
		parameter.setQueryId(condition.getEntityClass().getSimpleName() + ".delete");
	
		setParameterAndHint(condition,parameter);

		return internalNamedQuery.executeUpdate(parameter);
	}

	/**
	 * @see client.sql.orm.strategy.InternalOrmQuery#find(client.sql.orm.OrmSelectParameter, java.lang.Object[])
	 */
	@Override
	public <E> E find(OrmSelectParameter<E> query,Object... pks) {
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
	 * @see client.sql.orm.strategy.InternalOrmQuery#getResultList(client.sql.orm.OrmSelectParameter)
	 */
	@Override
	public <E> List<E> getResultList(OrmSelectParameter<E> condition){	

		String jpql = sb.createSelect(condition);
		
		final FreeSelectParameter parameter = new FreeSelectParameter();
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
	private void setParameterAndHint(OrmQueryParameter<?> condition,final FreeQueryParameter parameter ){
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
	 * @see client.sql.orm.strategy.InternalOrmQuery#getFetchResult(client.sql.orm.OrmSelectParameter)
	 */
	@Override
	public <E> List<E> getFetchResult(OrmSelectParameter<E> parameter) {
		throw new UnsupportedOperationException();
	}

}
