/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlclient.api.free;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class FreeUpdateParameter extends FreeParameter{
	
	/** the batch parameter */
	private List<Map<String,Object>> batchParam = new ArrayList<Map<String,Object>>();


	public FreeUpdateParameter(boolean useRowSql, String queryId, String sql) {
		super(useRowSql, queryId, sql);
	}
	
	/**
	 * Adds the batchParam
	 */
	public void addBatch(){
		batchParam.add(new LinkedHashMap<String,Object>(getParam()));
		getParam().clear();
	}
	
	/**
	 * @return the batch param
	 */
	public List<Map<String,Object>> getBatchParam(){
		return batchParam;
	}

}
