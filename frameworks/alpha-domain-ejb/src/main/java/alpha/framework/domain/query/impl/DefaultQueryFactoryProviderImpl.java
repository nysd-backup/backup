/**
 * Copyright 2011 the original author
 */
package alpha.framework.domain.query.impl;

import alpha.framework.domain.activation.EJBComponentFinder;
import alpha.framework.domain.activation.ServiceLocator;
import alpha.framework.domain.advice.InternalPerfInterceptor;
import alpha.framework.domain.advice.InternalQueryBuilderInterceptor;
import alpha.framework.domain.advice.ProxyFactory;
import alpha.framework.domain.query.QueryFactoryProvider;
import alpha.jdbc.strategy.QueryBuilder;
import alpha.jdbc.strategy.impl.QueryBuilderProxyImpl;
import alpha.sqlclient.elink.free.strategy.InternalNamedQueryImpl;
import alpha.sqlclient.elink.free.strategy.InternalNativeQueryImpl;
import alpha.sqlclient.elink.orm.strategy.JPQLStatementBuilderImpl;
import alpha.sqlclient.free.QueryFactory;
import alpha.sqlclient.free.strategy.InternalQuery;
import alpha.sqlclient.orm.CriteriaQueryFactory;
import alpha.sqlclient.orm.strategy.InternalOrmQuery;
import alpha.sqlclient.orm.strategy.impl.InternalOrmQueryImpl;

/**
 * Provides the QueryFactory.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DefaultQueryFactoryProviderImpl implements QueryFactoryProvider{

	/**
	 * @see alpha.framework.domain.query.QueryFactoryProvider#createQueryFactory()
	 */
	@Override
	public QueryFactory createQueryFactory() {
		QueryBuilder builder = createQueryBuilder();		
		InternalQuery namedQuery = createInternalNamedQuery(builder);
		InternalQuery nativeQuery = createInternalNativeQuery(builder);
		return createQueryFactory(namedQuery,nativeQuery);	
	}
	
	/**
	 * @see alpha.framework.domain.query.QueryFactoryProvider#createCriteriaQueryFactory()
	 */
	@Override
	public CriteriaQueryFactory createCriteriaQueryFactory() {
		QueryBuilder builder = createQueryBuilder();
		InternalQuery namedQuery = crateInternalOrmNamedQuery(builder);
		InternalOrmQuery ormQuery = createInternalOrmQuery(namedQuery);
		return createCriteriaQueryFactory(ormQuery);
	}
	
	/**
	 * Creates the internal query.
	 * @param builder the builder
	 * @return the query;
	 */
	protected InternalQuery crateInternalOrmNamedQuery(QueryBuilder builder){
		InternalNamedQueryImpl named = new InternalNamedQueryImpl();
		named.setQueryBuilder(builder);
		return named;
	}
	
	/**
	 * Creates the internal named Query.
	 * @param namedQuery the namedQuery
	 * @return the query;
	 */
	protected InternalOrmQuery createInternalOrmQuery(InternalQuery namedQuery){
		InternalOrmQueryImpl query = new InternalOrmQueryImpl();	
		query.setInternalQuery(namedQuery);		
		query.setSqlStatementBuilder(new JPQLStatementBuilderImpl());
		return query;
	}
	
	/**
	 * Creates the CriteriaQueryFactory
	 * @param ormQuery the ormQuery
	 * @return CriteriaQueryFactory
	 */
	protected CriteriaQueryFactory createCriteriaQueryFactory(InternalOrmQuery ormQuery){
		CriteriaQueryFactory impl = new CriteriaQueryFactory();
		impl.setInternalOrmQuery(ormQuery);
		return impl;
	}
	
	/**
	 * Creates the Internal Named Query.
	 * 
	 * @param builder the builder
	 * @return the query
	 */
	protected InternalQuery createInternalNamedQuery(QueryBuilder builder){
		InternalNamedQueryImpl named = new InternalNamedQueryImpl();
		named.setQueryBuilder(builder);				
		EJBComponentFinder finder = ServiceLocator.unwrap();
		InternalPerfInterceptor interceptor = finder.getInternaPerflInterceptor();
		if(interceptor.isEnabled()){
			return  ProxyFactory.create(InternalQuery.class, named, interceptor,"*");	
		}else{
			return named;
		}
	}
	
	/**
	 * Creates the Internal Native Query.
	 * 
	 * @param builder the builder
	 * @return the query
	 */
	protected InternalQuery createInternalNativeQuery(QueryBuilder builder){
		InternalNativeQueryImpl ntv = new InternalNativeQueryImpl();
		ntv.setQueryBuilder(builder);
		EJBComponentFinder finder = ServiceLocator.unwrap();
		InternalPerfInterceptor interceptor = finder.getInternaPerflInterceptor();
		if(interceptor.isEnabled()){
			return ProxyFactory.create(InternalQuery.class, ntv, interceptor,"*");
		}else{
			return ntv;
		}
	}
	
	/**
	 * Creates the QueryFactory.
	 * @param named the named query
	 * @param ntv the native query
	 * @return the query factory
	 */
	protected QueryFactory createQueryFactory(InternalQuery named , InternalQuery ntv){
		QueryFactory nf = new QueryFactory();
		nf.setInternalNativeQuery(ntv);
		nf.setInternalNamedQuery(named);
		return nf;
	}

	/**
	 * Creates the query builder.
	 * @return the QueryBuilder
	 */
	protected QueryBuilder createQueryBuilder(){
		QueryBuilder proxy =  new QueryBuilderProxyImpl();
		InternalQueryBuilderInterceptor interceptor = new InternalQueryBuilderInterceptor();
		if(interceptor.isEnabled()){
			QueryBuilder builder = ProxyFactory.create(QueryBuilder.class, proxy, interceptor,"evaluate");		
			return builder;
		}else {
			return proxy;
		}
	}
	
}
