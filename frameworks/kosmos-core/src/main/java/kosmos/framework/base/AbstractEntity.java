/**
 * Copyright 2011 the original author
 */
package kosmos.framework.base;

import java.util.LinkedHashMap;
import java.util.Map;

import kosmos.framework.sqlclient.orm.FastEntity;

/**
 * The base of an entity.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractEntity extends AbstractBean implements FastEntity{

	private static final long serialVersionUID = 1L;

	/**
	 * @return Map
	 */
	protected final Map<String,Object> createMap(){
		return new LinkedHashMap<String,Object>();
	}
}
