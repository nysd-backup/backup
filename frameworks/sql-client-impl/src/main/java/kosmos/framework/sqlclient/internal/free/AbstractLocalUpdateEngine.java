/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.internal.free;

import kosmos.framework.sqlclient.api.free.FreeUpdate;
import kosmos.framework.sqlclient.api.free.FreeUpdateParameter;

/**
 * The updating engine.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractLocalUpdateEngine implements FreeUpdate{

	/** the query */
	protected FreeUpdateParameter parameter;
	
	/** the internal query */
	protected final InternalQuery internalQuery;
	
	/**
	 * @param internalQuery the internalQuery to set
	 * @param parameter the parameter to set
	 */
	public AbstractLocalUpdateEngine(InternalQuery internalQuery, FreeUpdateParameter parameter){
		this.parameter = parameter;		
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

	/**
	 * @see kosmos.framework.sqlclient.api.free.FreeUpdate#setCondition(kosmos.framework.sqlclient.api.free.FreeUpdateParameter)
	 */
	@Override
	public void setCondition(FreeUpdateParameter parameter) {
		this.parameter = parameter;
	}

}
