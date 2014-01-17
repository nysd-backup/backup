/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.free.query;

import java.beans.PropertyDescriptor;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.coder.gear.query.free.QueryCallback;
import org.coder.gear.query.free.result.CloseableIterator;
import org.coder.gear.query.free.result.Record;
import org.coder.gear.query.free.result.TotalList;
import org.coder.gear.query.gateway.PersistenceGateway;



/**
 * Concrete native reading query.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class NativeQuery{
	
	private boolean autoSearchAgain;
	
	/** the delegate */
	private Conditions condition = new Conditions();

	/** the internal query */
	private PersistenceGateway persistenceGateway;

	/**
	 * Constructor.
	 */
	public NativeQuery(){
		result(Record.class);
	}
	
	/**
	 * @param persistenceGateway the persistenceGateway to set
	 */
	public void setPersistenceGateway(PersistenceGateway persistenceGateway ){
		this.persistenceGateway = persistenceGateway;
	}

	/**
	 * @param autoSearchAgain to set
	 * @return selfs
	 */
	public NativeQuery enableAutoSearch(boolean autoSearchAgain){
		this.autoSearchAgain = autoSearchAgain;
		return this;
	}
	
	/**
	 * Set the parameters 
	 * @param arguments the arguments
	 * @return self
	 */
	public NativeQuery set(Object bean){
		PropertyUtilsBean utilsBean = new PropertyUtilsBean();
		PropertyDescriptor[] properties = utilsBean.getPropertyDescriptors(bean.getClass());
		for(PropertyDescriptor p : properties){
			String name = p.getName();
			if("class".equals(name)){
				continue;
			}
			if(utilsBean.isReadable(bean, name)){
				try {
					condition.getParam().put(name, p.getReadMethod().invoke(bean));
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
			}
		}
		return this;
	}
	
	/**
	 * Sets the result type.
	 * @param resultType the result type
	 * @return self
	 */
	public NativeQuery result(Class<?> resultType){
		condition.setResultType(resultType);
		return this;
	}
	
	/**
	 * @param queryId the queryId
	 */
	public NativeQuery id(String queryId){
		condition.setQueryId(queryId);
		return this;
	}
	
	/**
	 * Set the parameter 
	 * @param name the name
	 * @param value the value
	 * @return self
	 */
	public NativeQuery set(String name, Object value){
		condition.getParam().put(name, value);
		return this;
	}

	/**
	 * Sets the first result.
	 * @param arg0 the start position that starts with 1
	 * @return self
	 */
	public NativeQuery offset(int arg0) {
		condition.setFirstResult(arg0);
		return this;
	}

	/**
	 * Sets the max results.
	 * @param arg0 the size to get
	 * @return self
	 */
	public NativeQuery limit(int arg0) {
		condition.setMaxResults(arg0);
		return this;
	}

	/**
	 * Sets the query hints
	 * @param arg0 the key
	 * @param arg1 the value
	 * @return self
	 */
	public NativeQuery hints(String arg0, Object arg1) {
		condition.getHints().put(arg0,arg1);
		return this;
	}
	
	/**
	 * Gets the result with fetching to cursor.
	 * @param callback the callback
	 * @return the hit count
	 */
	@SuppressWarnings("unchecked")
	public long getFetchResult(QueryCallback<?> callback){
		long count = 0;
		try(CloseableIterator<Object> iterator = getFetchResult();
			QueryCallback<?> call = callback){			
			call.preRead();
			while(iterator.hasNext()){	
				((QueryCallback<Object>)callback).handleRow(iterator.next(), count);
				count++;
			}			
			call.postRead(count);
		}catch(Exception e){
			throw new IllegalStateException(e);
		}
		return count;
	}
	
	/**
	 * Gets the result with fetching to cursor.
	 * @return the result holding ResultSet
	 */
	public <T> CloseableIterator<T> getFetchResult(){
		return persistenceGateway.getFetchResult(condition);
	}

	
	/**
	 * @see org.coder.gear.query.free.query.AbstractReadQuery#getTotalResult()
	 */
	public <T> TotalList<T> getTotalResult() {
		TotalList<T> result = persistenceGateway.getTotalResult(condition);
		if(autoSearchAgain){
			PagingHandler handler = new PagingHandler();
			while( handler.shouldRetry(condition, result)) {
				offset(handler.getNewStart());
				result = persistenceGateway.getTotalResult(condition);
			}
		}
		return result;
	}
	
	
	/**
	 * Gets the query result.
	 * @return the result
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getResultList() {
		return (List<T>)persistenceGateway.getResultList(condition);
	}
	
	/**
	 * Gets the query result.
	 * @return the first result
	 */
	public <T> T getSingleResult() {
		limit(1);
		List<T> result = getResultList();
		return (result == null || result.isEmpty() )? null : result.get(0);
	}
	
	/**
	 * Updates the data.
	 * @return the updated count
	 */
	public int update(){
		return persistenceGateway.executeUpdate(condition);
	}
	

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
	
}
