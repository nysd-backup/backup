/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.api.free;

import javax.persistence.QueryHint;

import kosmos.framework.sqlclient.api.free.AnonymousQuery;
import kosmos.framework.sqlclient.api.free.FreeParameter;
import kosmos.framework.sqlclient.api.free.FreeQuery;
import kosmos.framework.sqlclient.api.free.FreeQueryParameter;
import kosmos.framework.sqlclient.api.free.FreeUpdate;
import kosmos.framework.sqlclient.api.free.FreeUpdateParameter;
import kosmos.framework.sqlclient.internal.free.InternalQuery;
import kosmos.framework.sqlclient.internal.free.impl.LocalQueryEngine;
import kosmos.framework.sqlclient.internal.free.impl.LocalUpdateEngine;


/**
 * The factory to create the JPA query.
 *
 * @author yoshida-n
 * @version	created.
 */
public class EclipseLinkQueryFactoryImpl extends NamedQueryFactoryImpl{
	
	/** the internal query */
	private InternalQuery internalQuery;
	
	/**
	 * @param internalQuery the internalQuery to set
	 */
	public void setInternalQuery(InternalQuery internalQuery) {
		this.internalQuery = internalQuery;
	}
		
	/**
	 * @see kosmos.framework.jpqlclient.api.free.NamedQueryFactoryImpl#createNativeQueryEngine(java.lang.Class)
	 */
	@Override
	protected FreeQuery createNativeQueryEngine(Class<?> queryClass) {
		FreeQueryParameter parameter = (FreeQueryParameter)createParameter(queryClass,false);
		return new LocalQueryEngine(internalQuery,parameter);	
	}

	/**
	 * @see kosmos.framework.jpqlclient.api.free.NamedQueryFactoryImpl#createNativeUpdateEngine(java.lang.Class)
	 */
	@Override
	protected FreeUpdate createNativeUpdateEngine(Class<?> updateClass) {
		FreeUpdateParameter parameter = (FreeUpdateParameter)createParameter(updateClass,true);
		return new LocalUpdateEngine(internalQuery,parameter);		
	}

	/**
	 * Creates the internal query.
	 * 
	 * @param targetClass the targetClass
	 * @return the internal query
	 */
	protected FreeParameter createParameter(Class<?> targetClass,boolean update){
		javax.persistence.NamedNativeQuery nq = targetClass.getAnnotation(javax.persistence.NamedNativeQuery.class);
		FreeParameter parameter = null;
		QueryHint[] hint = null;
		if(nq != null){
			if(update){
				parameter = new FreeUpdateParameter(false, targetClass.getSimpleName(), nq.query());
			}else{
				parameter = new FreeQueryParameter(nq.resultClass(),false, targetClass.getSimpleName(), nq.query());				
			}
			parameter.setName(nq.name());
			hint = nq.hints();
		}else{
			AnonymousQuery aq = targetClass.getAnnotation(AnonymousQuery.class);
			if(update){
				parameter = new FreeUpdateParameter(false, targetClass.getSimpleName(), aq.query());
			}else{
				parameter = new FreeQueryParameter(aq.resultClass(),false, targetClass.getSimpleName(), aq.query());				
			}
			hint = aq.hints();
		}
		for(QueryHint h: hint){
			parameter.getHints().put(h.name(), h.value());
		}
		return parameter;
	}
}
