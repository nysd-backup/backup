package framework.jpqlclient.internal.free;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;

import framework.sqlclient.internal.AbstractInternalQuery;

/**
 * JPA用クエリ.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class AbstractInternalJPAQuery extends AbstractInternalQuery{
	
	/** フラッシュモード */
	protected FlushModeType flush = null;
	
	/** ロックモード */
	protected LockModeType lock = null;
	
	/** エンティティマネージャ */
	protected EntityManager em = null;
	
	/** ヒント */
	private Map<String,Object> hints = null;
	
	/**
	 * @param sql SQL
	 * @param em エンティティマネージャ
	 * @param queryId クエリID
	 */
	public AbstractInternalJPAQuery(boolean useRowSql,String sql, EntityManager em , String queryId){
		super(useRowSql,sql,queryId);
		this.em = em;
		this.hints = new HashMap<String,Object>();		
	}

	
	/**
	 * @return 新規生成したクエリ
	 */
	protected abstract Query createQuery();
	
	/**
	 * @param arg0 キー
	 * @param arg1 値 
	 * @return self
	 */
	public AbstractInternalJPAQuery setHint(String arg0, Object arg1) {
		hints.put(arg0, arg1);
		return this;
	}
	
	/**
	 * @see framework.sqlclient.internal.AbstractInternalQuery#getResultList()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getResultList() {
		return mapping( createQuery()).getResultList();
	}

	/**
	 * @see framework.sqlclient.internal.AbstractInternalQuery#count()
	 */
	@Override
	public int count(){
		//TODO 
		throw new UnsupportedOperationException();
	}

	/**
	 * @see framework.sqlclient.internal.AbstractInternalQuery#getSingleResult()
	 */
	@Override
	public Object getSingleResult() {
		setMaxResults(1);
		return mapping(createQuery()).getSingleResult();
	}
	
	/**
	 * @see framework.sqlclient.internal.AbstractInternalQuery#executeUpdate()
	 */
	@Override
	public int executeUpdate() {
		return mapping(createQuery()).executeUpdate();
	}

	/**
	 * @return フラッシュモード
	 */
	public FlushModeType getFlushMode() {
		return flush;
	}

	/**
	 * @return ロックモード
	 */
	public LockModeType getLockMode() {
		return lock;
	}
	
	/**
	 * @param query クエリ
	 * @return クエリ
	 */
	private Query mapping(Query query){
				
		for(Map.Entry<String, Object> h : hints.entrySet()){		
			query.setHint(h.getKey(), h.getValue());
		}
		
		if( flush != null){
			query.setFlushMode(flush);
		}
		if( lock != null){
			query.setLockMode(lock);
		}
		if( maxSize > 0){
			query.setMaxResults(maxSize);
		}
		if( firstResult > 0){
			query.setFirstResult(firstResult);
		}
		return query;
	}

	/**
	 * @param arg0 フラッシュモード
	 * @return self
	 */
	public AbstractInternalJPAQuery setFlushMode(FlushModeType arg0) {
		flush = arg0;
		return this;
	}

	/**
	 * @param arg0 ロックモード
	 * @return self
	 */
	public AbstractInternalJPAQuery setLockMode(LockModeType arg0) {
		lock = arg0;
		return this;
	}

}
