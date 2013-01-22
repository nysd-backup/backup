/**
 * Copyright 2011 the original author
 */
package alpha.framework.registry;

import alpha.framework.advice.InternalPerfInterceptor;
import alpha.framework.advice.InternalQueryBuilderInterceptor;
import alpha.framework.advice.ProxyFactory;
import alpha.jdbc.strategy.QueryLoader;
import alpha.jdbc.strategy.impl.QueryLoaderProxyImpl;
import alpha.query.criteria.CriteriaQueryFactory;
import alpha.query.criteria.builder.JPQLQueryBuilderFactory;
import alpha.query.criteria.builder.QueryBuilderFactory;
import alpha.query.elink.free.gateway.EclipseLinkJpqlGateway;
import alpha.query.elink.free.gateway.EclipseLinkNativeGateway;
import alpha.query.free.QueryFactory;
import alpha.query.free.gateway.PersistenceGateway;

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
		QueryLoader builder = createQueryLoader();		
		PersistenceGateway namedQuery = createJpqlGateway(builder);
		PersistenceGateway nativeQuery = createNativeGateway(builder);
		return createQueryFactory(namedQuery,nativeQuery);	
	}
	
	/**
	 * @see alpha.framework.registry.QueryFactoryFinder#createCriteriaQueryFactory()
	 */
	@Override
	public CriteriaQueryFactory createCriteriaQueryFactory() {
		QueryLoader loader = createQueryLoader();
		PersistenceGateway gateway = createJpqlGateway(loader);
		QueryBuilderFactory builderFactory = new JPQLQueryBuilderFactory();
		return createCriteriaQueryFactory(builderFactory,gateway);
	}
	
	/**
	 * Creates the CriteriaQueryFactory
	 * @param ormQuery the ormQuery
	 * @return CriteriaQueryFactory
	 */
	protected CriteriaQueryFactory createCriteriaQueryFactory(QueryBuilderFactory builderFactory,PersistenceGateway gateway){
		CriteriaQueryFactory factory = new CriteriaQueryFactory();
		factory.setPersistenceGateway(gateway);
		factory.setQueryBuilderFactory(builderFactory);
		return factory;
	}
	
	/**
	 * Creates the Internal Named Query.
	 * 
	 * @param builder the builder
	 * @return the query
	 */
	protected PersistenceGateway createJpqlGateway(QueryLoader builder){
		EclipseLinkJpqlGateway named = new EclipseLinkJpqlGateway();
		named.setQueryLoader(builder);				
		EJBComponentFinder finder = ServiceLocator.getComponentFinder();
		InternalPerfInterceptor interceptor = finder.getInternaPerflInterceptor();
		if(interceptor.isEnabled()){
			return  ProxyFactory.create(PersistenceGateway.class, named, interceptor,"*");	
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
	protected PersistenceGateway createNativeGateway(QueryLoader builder){
		EclipseLinkNativeGateway ntv = new EclipseLinkNativeGateway();
		ntv.setQueryLoader(builder);
		EJBComponentFinder finder = ServiceLocator.getComponentFinder();
		InternalPerfInterceptor interceptor = finder.getInternaPerflInterceptor();
		if(interceptor.isEnabled()){
			return ProxyFactory.create(PersistenceGateway.class, ntv, interceptor,"*");
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
	protected QueryFactory createQueryFactory(PersistenceGateway named , PersistenceGateway ntv){
		QueryFactory nf = new QueryFactory();
		nf.setNativeGateway(ntv);
		nf.setJpqlGateway(named);
		return nf;
	}

	/**
	 * Creates the query builder.
	 * @return the QueryLoader
	 */
	protected QueryLoader createQueryLoader(){
		QueryLoader proxy =  new QueryLoaderProxyImpl();
		InternalQueryBuilderInterceptor interceptor = new InternalQueryBuilderInterceptor();
		if(interceptor.isEnabled()){
			QueryLoader builder = ProxyFactory.create(QueryLoader.class, proxy, interceptor,"evaluate");		
			return builder;
		}else {
			return proxy;
		}
	}
	
}
