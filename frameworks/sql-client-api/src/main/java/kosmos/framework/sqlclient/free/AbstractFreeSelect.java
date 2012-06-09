/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.free;

import java.util.List;

import kosmos.framework.sqlclient.free.strategy.InternalQuery;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * The free writable query.
 * The base of all the query.
 *
 * @author yoshida-n
 * @version　 created.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractFreeSelect {
	
	/** the delegate */
	private FreeSelectParameter condition = null;

	/** the internal query */
	private InternalQuery internalQuery = null;
	
	/**
	 * @return the condition
	 */
	public FreeSelectParameter getParameter(){
		return this.condition;
	}
	
	/**
	 * @return the internal query
	 */
	protected InternalQuery getInternalQuery(){
		return this.internalQuery;
	}
	
	/**
	 *　Constructor 
	 */
	public AbstractFreeSelect(){
		condition = new FreeSelectParameter();
	}
	
	/**
	 * @param internalQuery
	 */
	public void setInternalQuery(InternalQuery internalQuery ){
		this.internalQuery = internalQuery;
	}
	
	/**
	 * Sets the branch parameter
	 * @param arg0 the key
	 * @param arg1 the param
	 * @return self
	 */
	public <T extends AbstractFreeSelect> T setBranchParameter(String arg0, Object arg1) {
		condition.getBranchParam().put(arg0, arg1);
		return (T)this;
	}

	/**
	 * Gets the query result.
	 * @return the result
	 */
	public <T> List<T> getResultList() {
		return (List<T>)internalQuery.getResultList(condition);
	}
	
	/**
	 * Gets the query result.
	 * @return the first result
	 */
	public <T> T getSingleResult() {
		return (T)internalQuery.getSingleResult(condition);
	}
	
	/**
	 * Gets the hit count
	 * @return the hit count
	 */
	public long count() {
		return internalQuery.count(condition);
	}

	/**
	 * Sets the first result.
	 * @param arg0 the start position that starts with 1
	 * @return self
	 */
	public <T extends AbstractFreeSelect> T setFirstResult(int arg0) {
		condition.setFirstResult(arg0);
		return (T)this;
	}

	/**
	 * Sets the max results.
	 * @param arg0 the size to get
	 * @return self
	 */
	public <T extends AbstractFreeSelect> T setMaxResults(int arg0) {
		condition.setMaxSize(arg0);
		return (T)this;
	}

	/**
	 * Sets the parameter
	 * @param arg0 the key
	 * @param arg1 the param
	 * @return self
	 */
	public <T extends AbstractFreeSelect> T setParameter(String arg0, Object arg1) {
		condition.getParam().put(arg0, arg1);
		return (T)this;
	}

	/**
	 * Sets the query hints
	 * @param arg0 the key
	 * @param arg1 the value
	 * @return self
	 */
	public <T extends AbstractFreeSelect> T setHint(String arg0, Object arg1) {
		condition.getHints().put(arg0,arg1);
		return (T)this;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}

}

