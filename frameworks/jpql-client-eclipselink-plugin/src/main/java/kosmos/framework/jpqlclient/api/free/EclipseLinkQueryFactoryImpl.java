/**
 * Copyright 2011 the original author
 */
package kosmos.framework.jpqlclient.api.free;

import javax.persistence.QueryHint;

import kosmos.framework.jpqlclient.internal.free.AbstractInternalJpaNativeQuery;
import kosmos.framework.jpqlclient.internal.free.impl.InternalEclipseLinkNativeQueryImpl;
import kosmos.framework.sqlclient.api.free.AnonymousQuery;
import kosmos.framework.sqlclient.api.free.FreeQuery;
import kosmos.framework.sqlclient.api.free.FreeUpdate;
import kosmos.framework.sqlclient.internal.free.InternalQuery;
import kosmos.framework.sqlclient.internal.free.impl.LocalQueryEngineImpl;
import kosmos.framework.sqlclient.internal.free.impl.LocalUpdateEngineImpl;
import kosmos.framework.sqlengine.exception.ExceptionHandler;
import kosmos.framework.sqlengine.exception.impl.ExceptionHandlerImpl;
import kosmos.framework.sqlengine.executer.RecordHandlerFactory;
import kosmos.framework.sqlengine.executer.ResultSetHandler;
import kosmos.framework.sqlengine.executer.impl.RecordHandlerFactoryImpl;
import kosmos.framework.sqlengine.executer.impl.ResultSetHandlerImpl;


/**
 * The factory to create the JPA query.
 *
 * @author yoshida-n
 * @version	created.
 */
public class EclipseLinkQueryFactoryImpl extends NamedQueryFactoryImpl{
	
	/** the handler */
	private ResultSetHandler handler = new ResultSetHandlerImpl();
	
	/** the recordHandlerFactory */
	private RecordHandlerFactory recordHandlerFactory = new RecordHandlerFactoryImpl();
	
	/** the ExceptionHandler */
	private ExceptionHandler exceptionHandler = new ExceptionHandlerImpl();
	
	/**
	 * @param exceptionHandler the exceptionHandler to set
	 */
	public void setExceptionHandler(ExceptionHandler exceptionHandler){
		this.exceptionHandler = exceptionHandler;
	}
	
	/**
	 * @param handler the handler to set
	 */
	public void setResultSetHandler(ResultSetHandler handler){
		this.handler = handler;
	}
	
	/**
	 * @param handler the recordHandlerFactory to set
	 */
	public void setRecordHandlerFactory(RecordHandlerFactory recordHandlerFactory){
		this.recordHandlerFactory = recordHandlerFactory;
	}
	
	/**
	 * @see kosmos.framework.jpqlclient.api.free.NamedQueryFactoryImpl#createNativeQueryEngine(java.lang.Class)
	 */
	@Override
	protected FreeQuery createNativeQueryEngine(Class<?> queryClass) {
		return new LocalQueryEngineImpl(createInternalQuery(queryClass));	
	}

	/**
	 * @see kosmos.framework.jpqlclient.api.free.NamedQueryFactoryImpl#createNativeUpdateEngine(java.lang.Class)
	 */
	@Override
	protected FreeUpdate createNativeUpdateEngine(Class<?> updateClass) {
		return new LocalUpdateEngineImpl(createInternalQuery(updateClass));		
	}

	/**
	 * Creates the internal query.
	 * 
	 * @param targetClass the targetClass
	 * @return the internal query
	 */
	protected InternalQuery createInternalQuery(Class<?> targetClass){
		javax.persistence.NamedNativeQuery nq = targetClass.getAnnotation(javax.persistence.NamedNativeQuery.class);
		if(nq != null){
			AbstractInternalJpaNativeQuery internal =
				new InternalEclipseLinkNativeQueryImpl(nq.name(),nq.query(), em,targetClass.getSimpleName()
						,nq.resultClass(),false,builder,handler,recordHandlerFactory,exceptionHandler);
			for(QueryHint h: nq.hints()){
				internal.setHint(h.name(), h.value());
			}
			return internal;
		}else{
			AnonymousQuery aq = targetClass.getAnnotation(AnonymousQuery.class);
			AbstractInternalJpaNativeQuery internal = 
				new InternalEclipseLinkNativeQueryImpl(null,aq.query(), em, targetClass.getSimpleName()
						,aq.resultClass(),false,builder,handler,recordHandlerFactory,exceptionHandler);
			for(QueryHint h: aq.hints()){
				internal.setHint(h.name(), h.value());
			}
			return internal;						
		}
	}
}
