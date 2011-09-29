/**
 * Copyright 2011 the original author
 */
package framework.jpqlclient.internal.free.impl;

import java.util.Map;
import java.util.regex.Matcher;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import framework.jpqlclient.internal.free.AbstractInternalJPAQuery;
import framework.sqlengine.builder.ConstAccessor;
import framework.sqlengine.builder.SQLBuilder;

/**
 * The internal named query.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class InternalNamedQueryImpl extends AbstractInternalJPAQuery {

	/** 
	 * the name of the query. 
	 * only <code>javax.persistence.NamedQuery</code> is required to use name. 
	 */
	private final String name;
	
	/** the <code>SQLBuilder</code> */
	private final SQLBuilder builder;
	
	/** the <code>ConstAccessor</code> */
	private final ConstAccessor accessor;
	
	/**
	 * @param sql the SQL
	 * @param em the EntityManager
	 * @param queryId the queryId
	 * @param useRowSql true:dont analyze template 
	 */
	public InternalNamedQueryImpl(String name ,String sql, EntityManager em,String queryId,boolean useRowSql,SQLBuilder builder,ConstAccessor accessor) {
		super(useRowSql,sql, em, queryId);		
		this.name = name;
		this.builder = builder;
		this.accessor = accessor;
	}

	/**
	 * @see framework.jpqlclient.internal.free.AbstractInternalJPAQuery#createQuery()
	 */
	@Override
	protected Query createQuery() {
		String str = sql;		
		Query query = null;
		//SQLエンジンを使用してクエリを読み込む
		if( name == null){	
			
			//解析未使用
			if( useRowSql ){	
				firingSql = str;
				query = em.createQuery(firingSql);			
				for(Map.Entry<String, Object> p : param.entrySet()){
					query.setParameter(p.getKey(), p.getValue());				
				}
			}else{				
				str = builder.build(queryId, str);
				str = builder.evaluate(str, branchParam,queryId);	
				firingSql = str;
				query = setParameters(em.createQuery(firingSql));	
			}
		//名前付きクエリ
		}else {
			firingSql = str;
			query = setParameters( em.createNamedQuery(name));
		}
		return query;
	}
	
	/**
	 * Set the parameters to the specified query.
	 * 
	 * @param query the query
	 * @return the query
	 */
	private Query setParameters(Query query){
		
		// バインド変数を検索
		Matcher match = BIND_VAR_PATTERN.matcher(firingSql);

		// バインド変数にマッチした部分を取得してパラメータ追加
		while (match.find()) {
			// マッチしたバインド変数名を取得(前後の空白、1文字目のコロンを除く)
			String variableName = match.group(2);
			Object[] value = accessor.getConstTarget(variableName);
			
			//定数
			if( value.length > 0 ){
				query.setParameter(variableName, value[0]);
			//定数以外
			}else{
				if(param.containsKey(variableName)){				
					query.setParameter(variableName, param.get(variableName));						
				}
			}
		}
		return query;
	}
}
