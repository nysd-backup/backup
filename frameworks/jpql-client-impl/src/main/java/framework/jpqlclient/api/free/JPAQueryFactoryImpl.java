/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.api.free;

import framework.jpqlclient.internal.free.impl.InternalNativeJPAQueryImpl;
import framework.jpqlclient.internal.free.impl.LocalJPANativeQueryEngine;
import framework.jpqlclient.internal.free.impl.LocalJPANativeUpdateEngine;
import framework.sqlclient.api.free.AnonymousQuery;
import framework.sqlclient.api.free.FreeQuery;
import framework.sqlclient.api.free.FreeUpdate;

/**
 * JPA Queryのファクトリ.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class JPAQueryFactoryImpl extends AbstractQueryFactory {

	/**
	 * @see framework.jpqlclient.api.free.AbstractQueryFactory#createNativeQueryEngine(java.lang.Class)
	 */
	@Override
	protected FreeQuery createNativeQueryEngine(Class<?> queryClass) {
		javax.persistence.NamedNativeQuery nq = queryClass.getAnnotation(javax.persistence.NamedNativeQuery.class);
		LocalJPANativeQueryEngine engine = null;		
		//標準
		if(nq != null){
			engine = new LocalJPANativeQueryEngine(new InternalNativeJPAQueryImpl(nq.name(),nq.query(), em,queryClass.getSimpleName() ,nq.resultClass(),false,builder),emptyHandler);							
		//拡張-if文使用	
		}else{
			AnonymousQuery aq = queryClass.getAnnotation(AnonymousQuery.class);
			engine = new LocalJPANativeQueryEngine(new InternalNativeJPAQueryImpl(null,aq.query(), em, queryClass.getSimpleName(),aq.resultClass(),false,builder),emptyHandler);				
		}		
		return engine;
	}

	/**
	 * @see framework.jpqlclient.api.free.AbstractQueryFactory#createNativeUpdateEngine(java.lang.Class)
	 */
	@Override
	protected FreeUpdate createNativeUpdateEngine(Class<?> updateClass) {
		javax.persistence.NamedNativeQuery nq = updateClass.getAnnotation(javax.persistence.NamedNativeQuery.class);
		LocalJPANativeUpdateEngine engine = null;
		//標準
		if(nq != null){
			engine = new LocalJPANativeUpdateEngine(new InternalNativeJPAQueryImpl(nq.name(),nq.query(), em,updateClass.getSimpleName() ,nq.resultClass(),false,builder));							
			
		//拡張-if文使用	
		}else{
			AnonymousQuery aq = updateClass.getAnnotation(AnonymousQuery.class);
			engine = new LocalJPANativeUpdateEngine(new InternalNativeJPAQueryImpl(null,aq.query(), em, updateClass.getSimpleName(),aq.resultClass(),false,builder));							
		}
		
		return engine;
	}
}
