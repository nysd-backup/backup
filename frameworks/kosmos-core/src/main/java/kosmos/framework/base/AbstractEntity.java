/**
 * Copyright 2011 the original author
 */
package kosmos.framework.base;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import kosmos.framework.core.exception.PoorImplementationException;
import kosmos.framework.sqlclient.api.FastEntity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * The base of an entity.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractEntity implements Serializable , Cloneable,  FastEntity{

	private static final long serialVersionUID = 1L;

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone(){
		try{
			return super.clone();
		}catch(CloneNotSupportedException cnse){
			throw new PoorImplementationException("Cloneable interface is required", cnse);
		}
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object){
		return EqualsBuilder.reflectionEquals(this,object);
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
	/**
	 * @return Map
	 */
	protected final Map<String,Object> createMap(){
		return new LinkedHashMap<String,Object>();
	}
}
