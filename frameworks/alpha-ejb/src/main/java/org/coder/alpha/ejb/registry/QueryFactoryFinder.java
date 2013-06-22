/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.ejb.registry;

import org.coder.alpha.query.criteria.CriteriaQueryFactory;
import org.coder.alpha.query.criteria.statement.JPQLBuilderFactory;
import org.coder.alpha.query.criteria.statement.StatementBuilderFactory;
import org.coder.alpha.query.free.QueryFactory;
import org.coder.alpha.query.free.loader.QueryLoader;
import org.coder.alpha.query.free.loader.QueryLoaderTrace;
import org.coder.alpha.query.gateway.JpqlGateway;
import org.coder.alpha.query.gateway.NativeGateway;
import org.coder.alpha.query.gateway.PersistenceGateway;
import org.coder.alpha.query.gateway.PersistenceGatewayTrace;

/**
 * Provides the QueryFactory.
 *
 * @author yoshida-n
 * @version	created.
 */
public class QueryFactoryFinder {

	/** 
	 * Creates QueryFactory.
	 *  
	 * @return QueryFactory
	 */
	public QueryFactory createQueryFactory() {
		QueryLoader builder = createQueryLoader();		
		PersistenceGateway nativeQuery = createNativeGateway(builder);
		return createQueryFactory(nativeQuery);	
	}
	
	
	/**
	 * Creates CriteriaQueryFactory.
	 * 
	 * @return CriteriaQueryFactory
	 */
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
		JpqlGateway named = new JpqlGateway();
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
		NativeGateway ntv = new NativeGateway();
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
	protected QueryFactory createQueryFactory(PersistenceGateway ntv){
		QueryFactory nf = new QueryFactory();
		nf.setNativeGateway(ntv);
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
