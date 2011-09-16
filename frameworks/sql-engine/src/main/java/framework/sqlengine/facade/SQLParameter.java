/**
 * Use is subject to license terms.
 */
package framework.sqlengine.facade;

import java.util.HashMap;
import java.util.Map;

/**
 * SQLエンジンのパラメータ基底.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class SQLParameter {

	/** SQL-ID */
	private String sqlId = null;
	
	/** SQL */
	private String sql = null;
	
	/** コメント */
	private StringBuilder comment = new StringBuilder();
	
	/** パラメータ */
	private Map<String,Object> parameter = new HashMap<String,Object>();
	
	/** if文用パラメータ */
	private Map<String,Object> branchParameter = new HashMap<String,Object>();
	
	/** そのままのSQLを使用するか否か */
	private boolean useRowSql = false;

	/**
	 * @param comment コメント
	 */
	public void addComment(String comment){
		this.comment.append(comment); 
	}
	
	/**
	 * @return コメント
	 */
	public String getComment(){
		return this.comment.toString();
	}
	
	/**
	 * @param param
	 */
	public void setAllParameter(Map<String,Object> param){
		this.parameter = param;
	}
	
	/**
	 * @param param
	 */
	public void setAllBranchParameter(Map<String,Object> param){
		this.branchParameter = param;
	}
	
	/**
	 * @param parameter the parameter to set
	 */
	public void putParameter(String name,Object param) {
		parameter.put(name, param);
	}

	
	/**
	 * @param parameter the parameter to set
	 */
	public void putBranchParameter(String name ,Object param) {
		branchParameter.put(name, param);
	}
	

	/**
	 * @return the parameter
	 */
	public Map<String,Object> getBranchParameter() {
		return branchParameter;
	}

	/**
	 * @return the parameter
	 */
	public Map<String,Object> getParameter() {
		return parameter;
	}

	/**
	 * @param sql the sql to set
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * @return the sql
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * @param sqlId the sqlId to set
	 */
	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}

	/**
	 * @return the sqlId
	 */
	public String getSqlId() {
		return sqlId;
	}
	
	/**
	 * @param ignoreEvaluate the ignoreEvaluate to set
	 */
	public void setUseRowSql(boolean useRowSql) {
		this.useRowSql = useRowSql;
	}

	/**
	 * @return the ignoreEvaluate
	 */
	public boolean isUseRowSql() {
		return useRowSql;
	}
	
}
