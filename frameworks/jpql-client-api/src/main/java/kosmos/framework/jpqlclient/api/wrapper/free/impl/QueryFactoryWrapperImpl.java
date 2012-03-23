/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.api.wrapper.free.impl;

import javax.persistence.QueryHint;

import kosmos.framework.jpqlclient.api.wrapper.free.AbstractNamedQuery;
import kosmos.framework.jpqlclient.api.wrapper.free.AbstractNamedUpdate;
import kosmos.framework.sqlclient.api.free.FreeQuery;
import kosmos.framework.sqlclient.api.free.FreeUpdate;
import kosmos.framework.sqlclient.api.free.QueryFactory;
import kosmos.framework.sqlclient.api.wrapper.free.AbstractFreeQuery;
import kosmos.framework.sqlclient.api.wrapper.free.AbstractFreeUpdate;
import kosmos.framework.sqlclient.api.wrapper.free.AnonymousQuery;
import kosmos.framework.sqlclient.api.wrapper.free.QueryFactoryWrapper;
import kosmos.framework.utility.ClassUtils;


/**
 * Query Factory.
 *
 * @author yoshida-n
 * @version	created.
 */
public class QueryFactoryWrapperImpl implements QueryFactoryWrapper{
	
	/** Native Query Factory */
	private QueryFactory internalFactory;
	
	/** Named Query Factory */
	private QueryFactory internalNamedFactory;
	
	/**
	 * @param internalFactory the internalFactory to set
	 */
	public void setInternalFactory(QueryFactory internalFactory){
		this.internalFactory = internalFactory;
	}
	
	/**
	 * @param internalNamedFactory the internalNamedFactory to set
	 */
	public void setInternalNamedFactory(QueryFactory internalNamedFactory){
		this.internalNamedFactory = internalNamedFactory;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.free.QueryFactoryWrapper#createQuery(java.lang.Class)
	 */
	@Override
	public <K extends FreeQuery,T extends AbstractFreeQuery<K>> T createQuery(Class<T> query) {
		
		K delegate = null;
		T instance = ClassUtils.newInstance(query);
		if(instance instanceof AbstractNamedQuery){
			delegate = internalNamedFactory.createQuery();
		}else{
			delegate = internalFactory.createQuery();
		}		
		AnonymousQuery aq = query.getAnnotation(AnonymousQuery.class);
		delegate.setSqlId(query.getSimpleName());
		if(aq != null){
			delegate.setResultType(aq.resultClass());
			delegate.setSql(aq.query());
			for(QueryHint hints : aq.hints()){
				delegate.setHint(hints.name(), hints.value());
			}
		}
		instance.setDelegate(delegate);
		return instance;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.wrapper.free.QueryFactoryWrapper#createUpdate(java.lang.Class)
	 */
	@Override
	public <K extends FreeUpdate,T extends AbstractFreeUpdate<K>> T createUpdate(Class<T> update) {
		
		K delegate = null;
		T instance = ClassUtils.newInstance(update);
		if(instance instanceof AbstractNamedUpdate){
			delegate = internalNamedFactory.createUpdate();
		}else{
			delegate = internalFactory.createUpdate();
		}
		
		AnonymousQuery aq = update.getAnnotation(AnonymousQuery.class);
		delegate.setSqlId(update.getSimpleName());
		if(aq != null){
			delegate.setSql(aq.query());
			for(QueryHint hints : aq.hints()){
				delegate.setHint(hints.name(), hints.value());
			}
		}
		
		instance.setDelegate(delegate);
		return instance;
	}

}
