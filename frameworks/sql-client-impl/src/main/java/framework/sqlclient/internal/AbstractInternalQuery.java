package framework.sqlclient.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 内部クエリ.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractInternalQuery{
	
	/** バインド変数の正規表現パターン */
	protected static final Pattern BIND_VAR_PATTERN = Pattern.compile("([\\s,(=]+):([a-z][a-zA-Z0-9_]*)");
	
	/** パラメータ */
	protected Map<String,Object> param = null;
	
	/** if文用パラメータ */
	protected Map<String,Object> branchParam = null;
	
	/** 検索可能数 */
	protected int maxSize = 0;
	
	/** 先頭レコード位置 */
	protected int firstResult = 0;
	
	/** クエリID */
	protected final String queryId;
	
	/** SQL */
	protected final String sql;
	
	/** 実行SQL */
	protected String firingSql = null;

	/** if文解析有無 */
	protected final boolean useRowSql;
	
	/**
	 * @param useRowSql　if文評価するか否か
	 * @param sql SQL
	 * @param queryId クエリID
	 */
	public AbstractInternalQuery(boolean useRowSql,String sql, String queryId){		
		this.queryId = queryId;
		this.useRowSql = useRowSql;
		this.param = new HashMap<String,Object>();
		this.branchParam = new HashMap<String,Object>();		
		this.sql = sql;		
	}
	
	/**
	 * @param arg0 キー
	 * @param arg1 値　
	 */
	public void setBranchParameter(String arg0 , Object arg1){
		this.branchParam.put(arg0,arg1);
	}

	/**
	 * @return 検索開始位置
	 */
	public int getFirstResult() {
		return this.firstResult;
	}

	/**
	 * @return 最大件数
	 */
	public int getMaxResults() {
		return maxSize;
	}

	/**
	 * @param arg0 検索開始位置
	 * @return self
	 */
	public AbstractInternalQuery setFirstResult(int arg0) {
		this.firstResult = arg0;
		return this;
	}

	/**
	 * @param arg0 最大件数
	 * @return self
	 */
	public AbstractInternalQuery setMaxResults(int arg0) {
		maxSize = arg0;
		return this;
	}

	/**
	 * @param arg0 キー
	 * @param arg1 値
	 * @return self
	 */
	public AbstractInternalQuery setParameter(String arg0, Object arg1) {
		param.put(arg0, arg1);
		return this;
	}
	
	/**
	 * @return 検索
	 */
	@SuppressWarnings("rawtypes")
	public abstract List getResultList();
	
	/**
	 * @return 件数
	 */
	public abstract int count();
	
	/**
	 * @return 先頭行
	 */
	public abstract Object getSingleResult();
	

	/**
	 * @return 更新件数
	 */
	public abstract int executeUpdate();
}
