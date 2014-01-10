/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.gateway;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.coder.gear.query.free.query.Conditions;
import org.coder.gear.query.free.result.CloseableIterator;
import org.coder.gear.query.free.result.TotalList;


/**
 * Trace query result
 *
 * @author yoshida-n
 * @version	1.0
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
	 * @see org.coder.gear.query.gateway.PersistenceGateway#executeUpdate(org.coder.alpha.query.free.ModifyingConditions)
	 */
	@Override
	public int executeUpdate(Conditions param) {
		int result = delegate.executeUpdate(param);
		if(LOG.isInfoEnabled()){
			LOG.info(String.format("%s:updated=%d", param.getQueryId(),result));
		}
		return result;
	}

	/**
	 * @see org.coder.gear.query.gateway.PersistenceGateway#getTotalResult(org.coder.gear.query.free.query.ReadingConditions)
	 */
	@Override
	public <T> TotalList<T> getTotalResult(Conditions param) {
		TotalList<T> result = delegate.getTotalResult(param);
		if(LOG.isInfoEnabled()){
			LOG.info(String.format("%s:hitdata=%d, datasize=%d, limited=%s",
					param.getQueryId(),
					result.getHitCount(),result.size(),
					String.valueOf(result.isExceededLimit())));
		}
		return result;
	}

	/**
	 * @see org.coder.gear.query.gateway.PersistenceGateway#getFetchResult(org.coder.gear.query.free.query.Conditions)
	 */
	@Override
	public <T> CloseableIterator<T> getFetchResult(Conditions param) {
		return delegate.getFetchResult(param);
	}

	/**
	 * @see org.coder.gear.query.gateway.PersistenceGateway#getResultList(org.coder.gear.query.free.query.ReadingConditions)
	 */
	@Override
	public <T> List<T> getResultList(Conditions param) {
		List<T> result = delegate.getResultList(param);
		if(LOG.isInfoEnabled()){
			LOG.info(String.format("%s:datasize=%d",
					param.getQueryId(),result.size()));
		}
		return result;
	}
	
	

}