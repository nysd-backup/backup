/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.facade.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import framework.sqlengine.builder.CommentAppender;
import framework.sqlengine.builder.SQLBuilder;
import framework.sqlengine.builder.StatementProvider;
import framework.sqlengine.builder.impl.SQLBuilderProxyImpl;
import framework.sqlengine.builder.impl.StatementProviderImpl;
import framework.sqlengine.exception.ExceptionHandler;
import framework.sqlengine.exception.impl.ExceptionHandlerImpl;
import framework.sqlengine.executer.LazyList;
import framework.sqlengine.executer.RecordHandler;
import framework.sqlengine.executer.RecordHandlerFactory;
import framework.sqlengine.executer.ResultSetHandler;
import framework.sqlengine.executer.Selecter;
import framework.sqlengine.executer.Updater;
import framework.sqlengine.executer.impl.RecordHandlerFactoryImpl;
import framework.sqlengine.executer.impl.ResultSetHandlerImpl;
import framework.sqlengine.executer.impl.SelecterImpl;
import framework.sqlengine.executer.impl.UpdaterImpl;
import framework.sqlengine.facade.QueryParameter;
import framework.sqlengine.facade.QueryResult;
import framework.sqlengine.facade.SQLEngineFacade;
import framework.sqlengine.facade.SQLParameter;
import framework.sqlengine.facade.UpdateParameter;

/**
 * SQL繧ｨ繝ｳ繧ｸ繝ｳ縺ｮ繝輔ぃ繧ｵ繝ｼ繝・
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class SQLEngineFacadeImpl implements SQLEngineFacade{

	/** 萓句､悶ワ繝ｳ繝峨Λ */
	private ExceptionHandler exceptionHandler = new ExceptionHandlerImpl();
	
	/** ResultSet蜈ｨ莉ｶ繝ｫ繝ｼ繝・*/
	private ResultSetHandler resultSetHandler = new ResultSetHandlerImpl();

	/** SQL譁・ン繝ｫ繝繝ｼ */
	private SQLBuilder sqlBuilder = new SQLBuilderProxyImpl();
	
	/** 繧ｹ繝・・繝医Γ繝ｳ繝井ｽ懈・繧ｨ繝ｳ繧ｸ繝ｳ */
	private StatementProvider provider = new StatementProviderImpl();
	
	/** Bean逕ｨ縺ｮ繝ｪ繧ｶ繝ｫ繝医そ繝・ヨ繝代・繧ｵ */
	private RecordHandlerFactory recordHandlerFactory = new RecordHandlerFactoryImpl();
	
	/** 繧ｳ繝｡繝ｳ繝医い繝壹Φ繝繝ｼ */
	private CommentAppender commentAppender = null;
	
	/** 讀懃ｴ｢蜃ｦ逅・*/
	private Selecter selecter = new SelecterImpl();
	
	/** 譖ｴ譁ｰ蜃ｦ逅・*/
	private Updater updater = new UpdaterImpl();
	
	/**
	 * @param recordHandlerFactory the recordHandlerFactory to set
	 */
	public void setRecordHandlerFactory(RecordHandlerFactory recordHandlerFactory){
		this.recordHandlerFactory = recordHandlerFactory;
	}

	/**
	 * @param provider the provider to set
	 */
	public void setProvider(StatementProvider provider){
		this.provider = provider;
	}
	
	/**
	 * @param resultSetHandler the resultSetHandler to set
	 */
	public void setResultSetHandler(ResultSetHandler resultSetHandler){
		this.resultSetHandler = resultSetHandler;
	}
	
	/**
	 * @param sqlBuilder the sqlBuilder to set
	 */
	public void setSqlBuilder(SQLBuilder sqlBuilder){
		this.sqlBuilder = sqlBuilder;
	}

	/**
	 * @param selecter the selecter to set
	 */
	public void setSelecter(Selecter selecter) {
		this.selecter = selecter;
	}
	
	/**
	 * @param updater the updater to set
	 */
	public void setUpdater(Updater updater) {
		this.updater = updater;
	}
	
	/**
	 * @see framework.sqlengine.facade.SQLEngineFacade#executeCount(framework.sqlengine.facade.QueryParameter, java.sql.Connection)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public int executeCount(QueryParameter<?> param,Connection con) {
		
		List<Object> bindList = new ArrayList<Object>();	
		String query = sqlBuilder.setCount(createQuery(param,bindList));
		PreparedStatement stmt = null;		
		ResultSet rs = null;

		try{
			stmt = provider.createStatement(con, query, bindList,param.getSqlId());	
			rs= selecter.select(stmt);
			QueryResult<HashMap> decimal = resultSetHandler.getResultList(rs, HashMap.class, 1, false, param.getSqlId(), null);
			Iterator itr = decimal.getResultList().get(0).values().iterator();
			itr.hasNext();
			Object value = itr.next();
			if( Number.class.isAssignableFrom(value.getClass())){
				return ((Number)value).intValue();
			}
			throw new IllegalStateException("Illegal type : type = " + value.getClass());
		}catch(Exception sqle){
			throw exceptionHandler.rethrow(sqle);
		}catch(Error e){
			throw e;
		}finally{
			close(stmt);
		}
	}

	/**
	 * @see framework.sqlengine.facade.SQLEngineFacade#executeQuery(framework.sqlengine.facade.QueryParameter, java.sql.Connection)
	 */
	@Override
	public <T> QueryResult<T> executeQuery(QueryParameter<T> param , Connection con){	
		List<Object> bindList = new ArrayList<Object>();	
		String query = createQuery(param,bindList);
		return getResultList(false,param,con,query,bindList);
	}

	/**
	 * @see framework.sqlengine.facade.SQLEngineFacade#executeFetch(framework.sqlengine.facade.QueryParameter, java.sql.Connection)
	 */
	@Override
	public <T> QueryResult<T> executeFetch(QueryParameter<T> param,	Connection con) {

		List<Object> bindList = new ArrayList<Object>();	
		String query = createQuery(param,bindList);

		ResultSet rs = null;
		PreparedStatement stmt = null;
		try{							
			stmt = provider.createStatement(con, query, bindList,param.getSqlId());	
			rs = selecter.select(stmt);				
			
			//ResultFetch逕ｨ繧ｪ繝悶ず繧ｧ繧ｯ繝医・霑泌唆
			RecordHandler<T> handler = recordHandlerFactory.create(param.getResultType(), rs);								
			List<T> resultList = new LazyList<T>(stmt,rs, param.getMaxSize(),handler);
			return new QueryResult<T>(false,resultList,-1);
			
		}catch(Exception re){
			close(rs,stmt);
			throw exceptionHandler.rethrow(re);
		}catch(Error e){
			close(rs,stmt);
			throw e;
		}
	}

	/**
	 * @see framework.sqlengine.facade.SQLEngineFacade#executeTotalQuery(framework.sqlengine.facade.QueryParameter, java.sql.Connection)
	 */
	@Override
	public <T> QueryResult<T> executeTotalQuery(QueryParameter<T> param,Connection con) {
		List<Object> bindList = new ArrayList<Object>();	
		
		String query = createSql(param,bindList);
		//譛螟ｧ莉ｶ謨ｰ縺ｯresultSet繧貞屓縺励↑縺後ｉ蜿門ｾ励☆繧九◆繧∝叙蠕嶺ｻｶ謨ｰ縺ｮrange險ｭ螳壹・陦後ｏ縺ｪ縺・・
		query = sqlBuilder.setRange(query, param.getFirstResult() , 0 ,bindList);
		if( commentAppender != null){
			query = commentAppender.setExternalString(param, query);
		}
		
		return getResultList(true,param,con,query,bindList);
	}

	/**
	 * @see framework.sqlengine.facade.SQLEngineFacade#executeUpdate(framework.sqlengine.facade.UpdateParameter, java.sql.Connection)
	 */
	@Override
	public int executeUpdate(UpdateParameter param, Connection con) {
		
		//SQL逕滓・
		List<Object> bindList = new ArrayList<Object>();			
		String executingSql = createSql(param,bindList);
		
		//繧ｳ繝｡繝ｳ繝郁ｿｽ蜉
		if( commentAppender != null){
			executingSql = commentAppender.setExternalString(param, executingSql);
		}
		
		//繧ｹ繝・・繝医Γ繝ｳ繝育函謌・
		PreparedStatement stmt = null;
		
		try{
			stmt = provider.createStatement(con, executingSql, bindList,param.getSqlId());	
			return updater.update(stmt);
		}catch(Exception sqle){
			throw exceptionHandler.rethrow(sqle);
		}catch(Error e){
			throw e;
		}finally{
			close(stmt);
		}
	}
	
	/**
	 * 繧ｯ繧ｨ繝ｪ繧堤ｵ・∩遶九※繧・
	 * @param <T>縲蝙・
	 * @param param 繝代Λ繝｡繝ｼ繧ｿ
	 * @param bindList 繝舌う繝ｳ繝牙､
	 * @return 繧ｯ繧ｨ繝ｪ
	 */
	private <T> String createQuery(QueryParameter<T> param, List<Object> bindList){
		//SQL逕滓・
		String executingSql = createSql(param,bindList);
		executingSql = sqlBuilder.setRange(executingSql, param.getFirstResult() , param.getMaxSize(),bindList);
		if( commentAppender != null){
			executingSql = commentAppender.setExternalString(param, executingSql);
		}
		return executingSql;	
	}
	
	/**
	 * SQL繧堤ｵ・∩遶九※繧・
	 * @param <T>縲蝙・
	 * @param param 繝代Λ繝｡繝ｼ繧ｿ
	 * @param bindList 繝舌う繝ｳ繝牙､
	 * @return SQL
	 */
	private String createSql(SQLParameter param, List<Object> bindList){
		String executingSql = param.getSql();
		
		if(!param.isUseRowSql()){
			executingSql = sqlBuilder.build(param.getSqlId(), executingSql);		
			executingSql = sqlBuilder.evaluate(executingSql, param.getBranchParameter(),param.getSqlId());
		}				
		executingSql = sqlBuilder.replaceToPreparedSql(executingSql, param.getParameter(), bindList,param.getSqlId());				
		return executingSql;
	}

	/**
	 * 繝・・繧ｿ繧呈､懃ｴ｢縺励※繝｡繝｢繝ｪ縺ｫ譬ｼ邏阪☆繧・
	 * @param <T> 蝙・
	 * @param stmt 繧ｹ繝・・繝医Γ繝ｳ繝・
	 * @param param 繝代Λ繝｡繝ｼ繧ｿ
	 * @param con 繧ｳ繝阪け繧ｷ繝ｧ繝ｳ
	 * @return 讀懃ｴ｢邨先棡
	 */
	private <T> QueryResult<T> getResultList(boolean totalEnabled,QueryParameter<T> param,Connection con,String executingSql,List<Object> bindList){
		PreparedStatement stmt = null;		
		ResultSet rs = null;
		try{									
			stmt = provider.createStatement(con, executingSql, bindList,param.getSqlId());
			rs = selecter.select(stmt);

			//謖・ｮ壻ｻｶ謨ｰ繝・・繧ｿ繧偵Γ繝｢繝ｪ縺ｫ譬ｼ邏阪☆繧・
			return resultSetHandler.getResultList(rs, param.getResultType(), param.getMaxSize(), totalEnabled, param.getSqlId(), param.getFilter());
			
		}catch(Exception sqle){
			throw exceptionHandler.rethrow(sqle);
		}catch(Error e){
			throw e;
		}finally{
			close(rs,stmt);
		}
	}
	
	
	/**
	 * 繧ｯ繝ｭ繝ｼ繧ｺ.
	 * 
	 * @param rs 繝ｪ繧ｶ繝ｫ繝医そ繝・ヨ
	 * @param stmt 繧ｹ繝・・繝医Γ繝ｳ繝・
	 */
	private void close(ResultSet rs , Statement stmt){
		try{
			if(rs != null){
				rs.close();
			}
		}catch(SQLException sqlee){			
		}finally{
			close(stmt);
		}
	}
	
	/**
	 * 繧ｹ繝・・繝医Γ繝ｳ繝医け繝ｭ繝ｼ繧ｺ.
	 * 
	 * @param stmt 繧ｹ繝・・繝医Γ繝ｳ繝・
	 */
	private void close(Statement stmt){
		try{
			if( stmt != null){
				stmt.close();
			}
		}catch(SQLException sqlee){				
		}
	}


}
