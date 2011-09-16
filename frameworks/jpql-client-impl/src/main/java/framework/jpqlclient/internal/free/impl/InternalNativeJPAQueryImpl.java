/**
 * Use is subject to license terms.
 */
package framework.jpqlclient.internal.free.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import framework.jpqlclient.internal.free.AbstractInternalJPAQuery;
import framework.sqlengine.builder.SQLBuilder;

/**
 * JPA用NativeQuery.
 *
 * @author yoshida-n
 * @version	created.
 */
public class InternalNativeJPAQueryImpl extends AbstractInternalJPAQuery {

	/** 結果格納クラス */
	private final Class<?> resultType;
	
	/** 名称 */
	private String name = null;
	
	/** 解析なし有無 */
	private boolean useRowSql;
	
	/** クエリビルダー */
	private final SQLBuilder builder;
	
	/**
	 * @param sql SQL
	 * @param em エンティティマネージャ
	 * @param queryId クエリID
	 * @param resultType 結果格納クラス
	 */
	public InternalNativeJPAQueryImpl(String name ,String sql,EntityManager em, String queryId, Class<?> resultType,boolean useRowSql,SQLBuilder builder) {
		super(useRowSql,sql, em, queryId);		
		this.name = name;
		this.resultType = resultType;
		this.builder = builder;
	}

	/**
	 * @see framework.jpqlclient.internal.free.AbstractInternalJPAQuery#createQuery()
	 */
	@Override
	protected Query createQuery() {
		
		Query query = null;
		List<Object> bindList = new ArrayList<Object>();
		//名前付き
		if( name != null){
			if( resultType != null){
				query = em.createNamedQuery(firingSql, resultType);
			}else{
				query = em.createNamedQuery(firingSql);
			}		
		//名前なし	
		}else {			
			String str = sql;	
			if(!useRowSql){
				str = builder.build(queryId, str);
				str = builder.evaluate(str, branchParam,queryId);
			}
			str = builder.replaceToPreparedSql(str, param,bindList, queryId);
			firingSql = str;			
			if( resultType != null && !resultType.equals(void.class)){
				query = em.createNativeQuery(firingSql, resultType);
			}else{
				query = em.createNativeQuery(firingSql);
			}		
		}
		for(int i=0; i < bindList.size(); i++){			
			query.setParameter(i+1,bindList.get(i));			
		}		
		return query;		
		
	}

}
