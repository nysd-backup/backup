/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.framework.registry;

import org.coder.alpha.jdbc.strategy.QueryLoader;
import org.coder.alpha.jdbc.strategy.impl.QueryLoaderTrace;
import org.coder.alpha.query.criteria.CriteriaQueryFactory;
import org.coder.alpha.query.criteria.statement.JPQLBuilderFactory;
import org.coder.alpha.query.criteria.statement.StatementBuilderFactory;
import org.coder.alpha.query.elink.free.gateway.EclipseLinkJpqlGateway;
import org.coder.alpha.query.elink.free.gateway.EclipseLinkNativeGateway;
import org.coder.alpha.query.free.QueryFactory;
import org.coder.alpha.query.free.gateway.PersistenceGateway;
import org.coder.alpha.query.free.gateway.PersistenceGatewayTrace;


/**
 * Provides the QueryFactory.
 *
 * @author yoshida-n
 * @version	created.
 */
public class DefaultQueryFactoryFinder implements QueryFactoryFinder{

	/**
	 * @see org.coder.alpha.framework.registry.QueryFactoryFinder#createQueryFactory()
	 */
	@Override
	public QueryFactory createQueryFactory() {
		QueryLoader builder = createQueryLoader();		
		PersistenceGateway namedQuery = createJpqlGateway(builder);
		PersistenceGateway nativeQuery = createNativeGateway(builder);
		return createQueryFactory(namedQuery,nativeQuery);	
	}
	
	/**
	 * @see org.coder.alpha.framework.registry.QueryFactoryFinder#createCriteriaQueryFactory()
	 */
	@Override
	public CriteriaQueryFactory createCriteriaQueryFactory() {
		QueryLoader loader = createQueryLoader();
		PersistenceGateway gateway = createJpqlGateway(loader);
		StatementBuilderFactory builderFactory = new JPQLBuilderFactory();
		return createCriteriaQueryFactory(builderFactory,gateway);
	}
	
	/**
	 * Creates the CriteriaQueryFactory
	 * @param ormQuery the ormQuery
	 * @return CriteriaQueryFactory
	 */
	protected CriteriaQueryFactory createCriteriaQueryFactory(StatementBuilderFactory builderFactory,PersistenceGateway gateway){
		CriteriaQueryFactory factory = new CriteriaQueryFactory();
		factory.setPersistenceGateway(gateway);
		factory.setBuilderFactory(builderFactory);
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
		if(PersistenceGatewayTrace.isEnabled()){
			PersistenceGatewayTrace trace = new PersistenceGatewayTrace();
			trace.setDelegate(named);
			return trace;
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
		if(PersistenceGatewayTrace.isEnabled()){
			PersistenceGatewayTrace trace = new PersistenceGatewayTrace();
			trace.setDelegate(ntv);
			return trace;
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
		return new QueryLoaderTrace();
	}
	
}
