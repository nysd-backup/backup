package kosmos.framework.sqlclient.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * The internal query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public abstract class AbstractInternalQuery{
	
	/** the pattern */
	protected static final Pattern BIND_VAR_PATTERN = Pattern.compile("([\\s,(=]+):([a-z][a-zA-Z0-9_]*)");
	
	/** the parameter for <code>PreparedStatement</code> */
	protected Map<String,Object> param = null;
	
	/** the parameter for analyze the template */
	protected Map<String,Object> branchParam = null;
	
	/** the max size */
	protected int maxSize = 0;
	
	/** the start position */
	protected int firstResult = 0;
	
	/** the queryId */
	protected final String queryId;
	
	/** the SQL */
	protected final String sql;
	
	/** the executing SQL */
	protected String firingSql = null;

	/** if true dont analyze the template*/
	protected final boolean useRowSql;
	
	/** the JPA hint */
	protected Map<String,Object> hints = null;
	
	
	/**
	 * @param useRowSql　the useRowSql to set
	 * @param sql the SQL to set
	 * @param queryId the queryId to set
	 */
	public AbstractInternalQuery(boolean useRowSql,String sql, String queryId){		
		this.queryId = queryId;
		this.useRowSql = useRowSql;
		this.param = new HashMap<String,Object>();
		this.branchParam = new HashMap<String,Object>();		
		this.sql = sql;		
		this.hints = new HashMap<String,Object>();		
	}
	
	/**
	 * @param arg0 the hint of the key
	 * @param arg1 the hint value
	 * @return self
	 */
	public void setHint(String arg0, Object arg1) {
		hints.put(arg0, arg1);
	}
	
	/**
	 * @param arg0 the key 
	 * @param arg1 the value
	 */
	public void setBranchParameter(String arg0 , Object arg1){
		this.branchParam.put(arg0,arg1);
	}

	/**
	 * @return the firstResult
	 */
	public int getFirstResult() {
		return this.firstResult;
	}

	/**
	 * @return the maxResults
	 */
	public int getMaxResults() {
		return maxSize;
	}

	/**
	 * @param arg0 the start position
	 * @return self
	 */
	public void setFirstResult(int arg0) {
		this.firstResult = arg0;
	}

	/**
	 * @param arg0 the max results
	 * @return self
	 */
	public void setMaxResults(int arg0) {
		maxSize = arg0;
	}

	/**
	 * @param arg0 the key
	 * @param arg1 the value
	 * @return self
	 */
	public void setParameter(String arg0, Object arg1) {
		param.put(arg0, arg1);
	}
	
	/**
	 * Searches the result.
	 * 
	 * @return the result
	 */
	@SuppressWarnings("rawtypes")
	public abstract List getResultList();
	
	/**
	 * Gets the hit count.
	 * 
	 * @return the count
	 */
	public abstract int count();
	
	/**
	 * Finds the first result hit.
	 * @return the result
	 */
	public abstract Object getSingleResult();
	

	/**
	 * Updates the data.
	 * 
	 * @return the updated count
	 */
	public abstract int executeUpdate();
}
