/**
 * Copyright 2011 the original author
 */
package alpha.framework.registry;

import alpha.framework.advice.InternalPerfInterceptor;
import alpha.framework.advice.InternalQueryBuilderInterceptor;
import alpha.framework.advice.ProxyFactory;
import alpha.jdbc.strategy.QueryBuilder;
import alpha.jdbc.strategy.impl.QueryBuilderProxyImpl;
import alpha.query.criteria.CriteriaQueryFactory;
import alpha.query.criteria.strategy.DataMapper;
import alpha.query.criteria.strategy.impl.JPQLDataMapperImpl;
import alpha.query.elink.free.strategy.InternalNamedQueryImpl;
import alpha.query.elink.free.strategy.InternalNativeQueryImpl;
import alpha.query.free.QueryFactory;
import alpha.query.free.strategy.InternalQuery;

/**
 * Provides the QueryFactory.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DefaultQueryFactoryFinder implements QueryFactoryFinder{

	/**
	 * @see alpha.framework.registry.QueryFactoryFinder#createQueryFactory()
	 */
	@Override
	public QueryFactory createQueryFactory() {
		QueryBuilder builder = createQueryBuilder();		
		InternalQuery namedQuery = createInternalNamedQuery(builder);
		InternalQuery nativeQuery = createInternalNativeQuery(builder);
		return createQueryFactory(namedQuery,nativeQuery);	
	}
	
	/**
	 * @see alpha.framework.registry.QueryFactoryFinder#createCriteriaQueryFactory()
	 */
	@Override
	public CriteriaQueryFactory createCriteriaQueryFactory() {
		QueryBuilder builder = createQueryBuilder();
		InternalQuery namedQuery = crateInternalOrmNamedQuery(builder);
		DataMapper ormQuery = createInternalOrmQuery(namedQuery);
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
	protected DataMapper createInternalOrmQuery(InternalQuery namedQuery){
		JPQLDataMapperImpl query = new JPQLDataMapperImpl();	
		query.setInternalQuery(namedQuery);		
		return query;
	}
	
	/**
	 * Creates the CriteriaQueryFactory
	 * @param ormQuery the ormQuery
	 * @return CriteriaQueryFactory
	 */
	protected CriteriaQueryFactory createCriteriaQueryFactory(DataMapper ormQuery){
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
		EJBComponentFinder finder = ServiceLocator.getComponentFinder();
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
		EJBComponentFinder finder = ServiceLocator.getComponentFinder();
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
