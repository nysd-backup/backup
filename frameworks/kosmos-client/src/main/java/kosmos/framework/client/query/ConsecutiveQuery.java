/**
 * Copyright 2011 the original author
 */
package kosmos.framework.client.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import kosmos.framework.core.query.impl.AbstractLimitedOrmQuery;
import kosmos.framework.core.services.ConsecutiveQueryService;
import kosmos.framework.core.services.QueryRequest;
import kosmos.framework.sqlclient.api.free.AbstractNativeQuery;
import kosmos.framework.sqlclient.api.free.NativeQuery;
import kosmos.framework.sqlclient.api.orm.OrmQuery;

/**
 * A consecutive query.
 *
 * @author yoshida-n
 * @version	created.
 */
public class ConsecutiveQuery {

	/** the service */
	private final ConsecutiveQueryService service;
	
	/**
	 * @param service the service
	 */
	public ConsecutiveQuery(ConsecutiveQueryService service){
		this.service = service;
	}
	
	/**
	 * Gets the chained results lists.
	 * 
	 * @param queries the queries
	 * @return the resultLists
	 */
	public List<List<Object>> getChainedResultLists(AbstractNativeQuery... queries){
		
		List<QueryRequest> rs = new ArrayList<QueryRequest>(queries.length);
		
		for(AbstractNativeQuery q : queries){
			NativeQuery delegate =  q.getDelegate();
			if(delegate instanceof ClientNativeQueryEngine){
				rs.add(((ClientNativeQueryEngine)delegate).getRequest());
			}else{
				throw new UnsupportedOperationException("delegate must be a ClientNativeQueryEngine");
			}
		}
		return service.getChainedResultLists(rs.toArray(new QueryRequest[0]));
	}
	
	/**
	 * Gets the results lists.
	 * 
	 * @param queries the queries
	 * @return the resultLists
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<List<Object>> getResultLists(Object... queries){
		
		List<Serializable> rs = new ArrayList<Serializable>(queries.length);
		
		for(Object q : queries){
			if( q instanceof AbstractLimitedOrmQuery ){
				OrmQuery<Object> delegate = ((AbstractLimitedOrmQuery<Object>)q).getDelegate();
				if(delegate instanceof ClientOrmQueryEngine){
					rs.add(((ClientOrmQueryEngine)delegate).getRequest());
				}else{
					throw new UnsupportedOperationException("delegate must be a ClientOrmQueryEngine");
				}
			}else if (q instanceof AbstractNativeQuery){
				NativeQuery delegate =  ((AbstractNativeQuery)q).getDelegate();
				if(delegate instanceof ClientNativeQueryEngine){
					rs.add(((ClientNativeQueryEngine)delegate).getRequest());
				}else{
					throw new UnsupportedOperationException("delegate must be a ClientNativeQueryEngine");
				}
			}else {
				throw new UnsupportedOperationException("query must be a AbstractLimitedOrmQuery or AbstractNativeQuery");
			}
		}
		return service.getResultLists(rs.toArray(new Serializable[0]));
	}
	
}
