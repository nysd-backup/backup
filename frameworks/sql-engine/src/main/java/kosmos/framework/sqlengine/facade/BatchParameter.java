/**
 * Copyright 2011 the original author
 */
package kosmos.framework.sqlengine.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A batch parameter.
 *
 * @author yoshida-n
 * @version	created.
 */
public class BatchParameter extends BaseSQLParameter{

	/** the parameters to bind */
	private List<Map<String,Object>> parameters = new ArrayList<Map<String,Object>>();

	/**
	 * @return the parameters
	 */
	public List<Map<String,Object>> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(List<Map<String,Object>> parameters) {
		this.parameters = parameters;
	}

}
