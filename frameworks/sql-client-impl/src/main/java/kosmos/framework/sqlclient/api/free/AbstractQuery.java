/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;

import kosmos.framework.sqlclient.api.Query;
import kosmos.framework.sqlclient.api.free.FreeQuery;
import kosmos.framework.sqlclient.api.free.FreeQueryParameter;
import kosmos.framework.sqlclient.internal.free.InternalQuery;

/**
 * The local query engine.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractQuery implements FreeQuery{

	/** the delegate */
	protected FreeQueryParameter condition = null;

	/** the internal query */
	protected InternalQuery internalQuery = null;
	
	/**
	 * コンストラクタ
	 */
	public AbstractQuery(){
		condition = new FreeQueryParameter();
	}
	
	/**
	 * @param internalQuery
	 */
	public void setInternalQuery(InternalQuery internalQuery ){
		this.internalQuery = internalQuery;
	}
	/**
	 * @see kosmos.framework.sqlclient.api.Query#getSingleResult()
	 */
	@Override
	public <T> T getSingleResult() {
		return (T)internalQuery.getSingleResult(condition);
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#setFirstResult(int)
	 */
	@Override
	public <T extends Query> T setFirstResult(int arg0) {
		condition.setFirstResult(arg0);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.Query#setMaxResults(int)
	 */
	@Override
	public <T extends Query> T setMaxResults(int arg0) {
		condition.setMaxSize(arg0);
		return (T)this;
	}

	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeQuery#setParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeQuery> T setParameter(String arg0 , Object arg1){
		condition.getParam().put(arg0, arg1);
		return (T)this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeQuery#setBranchParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeQuery> T setBranchParameter(String arg0, Object arg1) {
		condition.getBranchParam().put(arg0, arg1);
		return (T)this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeQuery#getCurrentParams()
	 */
	@Override
	public FreeQueryParameter getCurrentParams() {
		return condition;
	}

}
