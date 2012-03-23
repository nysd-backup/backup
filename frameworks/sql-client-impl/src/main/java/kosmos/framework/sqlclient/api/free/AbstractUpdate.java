/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;

import kosmos.framework.sqlclient.api.free.FreeUpdate;
import kosmos.framework.sqlclient.api.free.FreeUpdateParameter;
import kosmos.framework.sqlclient.internal.free.InternalQuery;

/**
 * The updating engine.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractUpdate implements FreeUpdate{

	/** the query */
	protected FreeUpdateParameter parameter;
	
	/** the internal query */
	protected InternalQuery internalQuery;
	
	/**
	 * コンストラクタ
	 */
	public AbstractUpdate(){
		parameter = new FreeUpdateParameter();
	}
	
	/**
	 * @param internalQuery
	 */
	public void setInternalQuery(InternalQuery internalQuery){
		this.internalQuery = internalQuery;
	}
	
	
	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeUpdate#setParameter(java.lang.String, java.lang.Object)
	 */
	@Override
	public <T extends FreeUpdate> T setParameter(String arg0 , Object arg1){
		parameter.getParam().put(arg0, arg1);
		return (T)this;
	}
	
	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeUpdate#getCurrentParams()
	 */
	@Override
	public FreeUpdateParameter getCurrentParams() {
		return parameter;
	}

}
