/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.facade;

import java.util.HashMap;
import java.util.Map;

/**
 * The base of the parameter
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class SQLParameter extends BaseSQLParameter{
	
	/** the parameter */
	private Map<String,Object> parameter = new HashMap<String,Object>();
	
	/**
	 * @param param the param to set 
	 */
	public void setAllParameter(Map<String,Object> param){
		this.parameter = param;
	}
	
	/**
	 * @param name パラメータ名
	 * @param param 値
	 */
	public void putParameter(String name,Object param) {
		parameter.put(name, param);
	}
	
	/**
	 * @return the parameter
	 */
	public Map<String,Object> getParameter() {
		return parameter;
	}

}
