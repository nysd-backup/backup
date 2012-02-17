/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;

import java.util.HashMap;

/**
 * DynamicNativeQuery.
 *
 * @author yoshida-n
 * @version	created.
 */
@AnonymousQuery(query="",resultClass=HashMap.class)
public class DynamicNativeQuery extends AbstractNativeQuery{

	/**
	 * @param sql the sql to set
	 */
	public void setSql(String sql){
		getCurrentParams().setSql(sql);
		getCurrentParams().setUseRowSql(true);
	}
}
