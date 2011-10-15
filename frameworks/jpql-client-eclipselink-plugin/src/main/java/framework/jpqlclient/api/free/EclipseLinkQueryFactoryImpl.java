/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.api.free;

import javax.persistence.QueryHint;

import framework.jpqlclient.internal.free.AbstractInternalJPANativeQueryImpl;
import framework.jpqlclient.internal.free.impl.InternalEclipseLinkNativeQueryImpl;
import framework.jpqlclient.internal.free.impl.LocalJPANativeQueryEngine;
import framework.jpqlclient.internal.free.impl.LocalJPANativeUpdateEngine;
import framework.sqlclient.api.free.AnonymousQuery;
import framework.sqlclient.api.free.FreeQuery;
import framework.sqlclient.api.free.FreeUpdate;
import framework.sqlengine.exception.ExceptionHandler;
import framework.sqlengine.exception.impl.ExceptionHandlerImpl;
import framework.sqlengine.executer.RecordHandlerFactory;
import framework.sqlengine.executer.ResultSetHandler;
import framework.sqlengine.executer.impl.RecordHandlerFactoryImpl;
import framework.sqlengine.executer.impl.ResultSetHandlerImpl;

/**
 * The factory to create the JPA query.
 *
 * @author yoshida-n
 * @version	created.
 */
public class EclipseLinkQueryFactoryImpl extends AbstractQueryFactory{
	
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
	 * @see framework.jpqlclient.api.free.AbstractQueryFactory#createNativeQueryEngine(java.lang.Class)
	 */
	@Override
	protected FreeQuery createNativeQueryEngine(Class<?> queryClass) {
		return new LocalJPANativeQueryEngine(createInternalQuery(queryClass),emptyHandler);	
	}

	/**
	 * @see framework.jpqlclient.api.free.AbstractQueryFactory#createNativeUpdateEngine(java.lang.Class)
	 */
	@Override
	protected FreeUpdate createNativeUpdateEngine(Class<?> updateClass) {
		return new LocalJPANativeUpdateEngine(createInternalQuery(updateClass));		
	}

	/**
	 * Creates the internal query.
	 * 
	 * @param targetClass the targetClass
	 * @return the internal query
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected AbstractInternalJPANativeQueryImpl createInternalQuery(Class<?> targetClass){
		javax.persistence.NamedNativeQuery nq = targetClass.getAnnotation(javax.persistence.NamedNativeQuery.class);
		if(nq != null){
			AbstractInternalJPANativeQueryImpl internal =
				new InternalEclipseLinkNativeQueryImpl(nq.name(),nq.query(), em,targetClass.getSimpleName()
						,nq.resultClass(),false,builder,handler,recordHandlerFactory,exceptionHandler);
			for(QueryHint h: nq.hints()){
				internal.setHint(h.name(), h.value());
			}
			return internal;
		}else{
			AnonymousQuery aq = targetClass.getAnnotation(AnonymousQuery.class);
			AbstractInternalJPANativeQueryImpl internal = 
				new InternalEclipseLinkNativeQueryImpl(null,aq.query(), em, targetClass.getSimpleName()
						,aq.resultClass(),false,builder,handler,recordHandlerFactory,exceptionHandler);
			setHint(targetClass, internal);
			return internal;						
		}
	}
}
