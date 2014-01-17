/**
 * 
 */
package org.coder.gear.query;

import javax.persistence.EntityManager;

import org.coder.gear.query.criteria.query.DeleteQuery;
import org.coder.gear.query.criteria.query.ListQuery;
import org.coder.gear.query.criteria.query.SingleQuery;
import org.coder.gear.query.criteria.query.UpdateQuery;
import org.coder.gear.query.criteria.statement.JPQLBuilder;
import org.coder.gear.query.criteria.statement.StatementBuilder;
import org.coder.gear.query.free.loader.QueryLoaderTrace;
import org.coder.gear.query.free.query.NativeQuery;
import org.coder.gear.query.gateway.JpqlGateway;
import org.coder.gear.query.gateway.NativeGateway;
import org.coder.gear.query.gateway.PersistenceGateway;
import org.coder.gear.query.gateway.PersistenceGatewayTrace;

/**
 * @author yoshida-n
 *
 */
public class QueryFactory {
	
	/** em */
	private EntityManager em;

	/**
	 * @param em to set
	 */
	public QueryFactory(EntityManager em){
		this.em = em;
	}
	
	/**
	 * @return query
	 */
	public <T> ListQuery<T> listQuery(){
		ListQuery<T> query = new ListQuery<T>();		
		query.setPersistenceGateway(createJpqlGateway());
		query.setStatementBuilder(createBuilder());
		return query;
	}
	
	/**
	 * @return query
	 */
	public <T> SingleQuery<T> singleQuery(){
		SingleQuery<T> query = new SingleQuery<T>();		
		query.setPersistenceGateway(createJpqlGateway());
		query.setStatementBuilder(createBuilder());
		return query;
	}
	
	/**
	 * @return query
	 */
	public  DeleteQuery deleteQuery(){
		DeleteQuery query = new DeleteQuery();		
		query.setPersistenceGateway(createJpqlGateway());
		query.setStatementBuilder(createBuilder());
		return query;
	}
	
	/**
	 * @return query
	 */
	public UpdateQuery updateQuery(){
		UpdateQuery query = new UpdateQuery();		
		query.setPersistenceGateway(createJpqlGateway());
		query.setStatementBuilder(createBuilder());
		return query;
	}
	
	/**
	 * @param id to set
	 * @return query
	 */
	public NativeQuery nativeQuery(String id){
		NativeQuery query = new NativeQuery().id(id);		
		query.setPersistenceGateway(createJpqlGateway());		
		return query;
	}
	
	/**
	 * @return builder
	 */
	protected StatementBuilder createBuilder(){
		return new JPQLBuilder();
	}
	
	/**
	 * @return gateway
	 */
	protected PersistenceGateway createJpqlGateway(){		
		JpqlGateway named = new JpqlGateway();
		named.setEntityManager(em);
		named.setQueryLoader(new QueryLoaderTrace());				
		if(PersistenceGatewayTrace.isEnabled()){
			PersistenceGatewayTrace trace = new PersistenceGatewayTrace();
			trace.setDelegate(named);
			return trace;
		}else{
			return named;
		}
		
	}
	
	/**
	 * @return gateway
	 */
	protected PersistenceGateway createNativeGateway(){
		NativeGateway ntv = new NativeGateway();
		ntv.setQueryLoader(new QueryLoaderTrace());	
		ntv.setEntityManager(em);
		if(PersistenceGatewayTrace.isEnabled()){
			PersistenceGatewayTrace trace = new PersistenceGatewayTrace();
			trace.setDelegate(ntv);
			return trace;
		}else{
			return ntv;
		}		
	}
}
