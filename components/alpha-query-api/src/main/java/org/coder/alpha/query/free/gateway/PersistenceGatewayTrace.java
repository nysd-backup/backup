/**
 * Copyright 2011 the original author
 */
package org.coder.alpha.query.free.gateway;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.coder.alpha.query.free.HitData;
import org.coder.alpha.query.free.ModifyingConditions;
import org.coder.alpha.query.free.ReadingConditions;


/**
 * Trace query result
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class PersistenceGatewayTrace implements PersistenceGateway{
	
	/** the log */
	private static final Log LOG = LogFactory.getLog("QUERY." + PersistenceGatewayTrace.class);
	
	/**
	 * @return true:enabled
	 */
	public static final boolean isEnabled(){
		return LOG.isInfoEnabled();
	}
	
	/** the delegate */
	private PersistenceGateway delegate;
	
	/**
	 * @param delegate the delegate to set
	 */
	public void setDelegate(PersistenceGateway delegate){
		this.delegate = delegate;
	}

	/**
	 * @see org.coder.alpha.query.free.gateway.PersistenceGateway#executeUpdate(org.coder.alpha.query.free.ModifyingConditions)
	 */
	@Override
	public int executeUpdate(ModifyingConditions param) {
		int result = delegate.executeUpdate(param);
		if(LOG.isInfoEnabled()){
			LOG.info(String.format("%s:updated=%d", param.getQueryId(),result));
		}
		return result;
	}

	/**
	 * @see org.coder.alpha.query.free.gateway.PersistenceGateway#executeBatch(java.util.List)
	 */
	@Override
	public int[] executeBatch(List<ModifyingConditions> param) {
		return delegate.executeBatch(param);
	}

	/**
	 * @see org.coder.alpha.query.free.gateway.PersistenceGateway#getTotalResult(org.coder.alpha.query.free.ReadingConditions)
	 */
	@Override
	public <T> HitData<T> getTotalResult(ReadingConditions param) {
		HitData<T> result = delegate.getTotalResult(param);
		if(LOG.isInfoEnabled()){
			LOG.info(String.format("%s:hitdata=%d, datasize=%d, limited=%s",
					param.getQueryId(),
					result.getHitCount(),result.size(),
					String.valueOf(result.isLimited())));
		}
		return result;
	}

	/**
	 * @see org.coder.alpha.query.free.gateway.PersistenceGateway#getFetchResult(org.coder.alpha.query.free.ReadingConditions)
	 */
	@Override
	public <T> List<T> getFetchResult(ReadingConditions param) {
		return delegate.getFetchResult(param);
	}

	/**
	 * @see org.coder.alpha.query.free.gateway.PersistenceGateway#count(org.coder.alpha.query.free.ReadingConditions)
	 */
	@Override
	public long count(ReadingConditions param) {
		return delegate.count(param);
	}

	/**
	 * @see org.coder.alpha.query.free.gateway.PersistenceGateway#getResultList(org.coder.alpha.query.free.ReadingConditions)
	 */
	@Override
	public <T> List<T> getResultList(ReadingConditions param) {
		List<T> result = delegate.getResultList(param);
		if(LOG.isInfoEnabled()){
			LOG.info(String.format("%s:datasize=%d",
					param.getQueryId(),result.size()));
		}
		return result;
	}
	
	

}