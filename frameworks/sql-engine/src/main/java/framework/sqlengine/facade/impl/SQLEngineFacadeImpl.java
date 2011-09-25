/**
 * Use is subject to license terms.
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
 * SQLエンジンのファサード.
 *
 * @author yoshida-n
 * @version created.
 */
public class SQLEngineFacadeImpl implements SQLEngineFacade{

	/** 例外ハンドラ */
	private ExceptionHandler exceptionHandler = new ExceptionHandlerImpl();
	
	/** ResultSet全件ループ */
	private ResultSetHandler resultSetHandler = new ResultSetHandlerImpl();

	/** SQL文ビルダー */
	private SQLBuilder sqlBuilder = new SQLBuilderProxyImpl();
	
	/** ステートメント作成エンジン */
	private StatementProvider provider = new StatementProviderImpl();
	
	/** Bean用のリザルトセットパーサ */
	private RecordHandlerFactory recordHandlerFactory = new RecordHandlerFactoryImpl();
	
	/** コメントアペンダー */
	private CommentAppender commentAppender = null;
	
	/** 検索処理 */
	private Selecter selecter = new SelecterImpl();
	
	/** 更新処理 */
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
			
			//ResultFetch用オブジェクトの返却
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
		//最大件数はresultSetを回しながら取得するため取得件数のrange設定は行わない。
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
		
		//SQL生成
		List<Object> bindList = new ArrayList<Object>();			
		String executingSql = createSql(param,bindList);
		
		//コメント追加
		if( commentAppender != null){
			executingSql = commentAppender.setExternalString(param, executingSql);
		}
		
		//ステートメント生成
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
	 * クエリを組み立てる.
	 * @param <T>　型
	 * @param param パラメータ
	 * @param bindList バインド値
	 * @return クエリ
	 */
	private <T> String createQuery(QueryParameter<T> param, List<Object> bindList){
		//SQL生成
		String executingSql = createSql(param,bindList);
		executingSql = sqlBuilder.setRange(executingSql, param.getFirstResult() , param.getMaxSize(),bindList);
		if( commentAppender != null){
			executingSql = commentAppender.setExternalString(param, executingSql);
		}
		return executingSql;	
	}
	
	/**
	 * SQLを組み立てる.
	 * @param <T>　型
	 * @param param パラメータ
	 * @param bindList バインド値
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
	 * データを検索してメモリに格納する
	 * @param <T> 型
	 * @param stmt ステートメント
	 * @param param パラメータ
	 * @param con コネクション
	 * @return 検索結果
	 */
	private <T> QueryResult<T> getResultList(boolean totalEnabled,QueryParameter<T> param,Connection con,String executingSql,List<Object> bindList){
		PreparedStatement stmt = null;		
		ResultSet rs = null;
		try{									
			stmt = provider.createStatement(con, executingSql, bindList,param.getSqlId());
			rs = selecter.select(stmt);

			//指定件数データをメモリに格納する
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
	 * クローズ.
	 * 
	 * @param rs リザルトセット
	 * @param stmt ステートメント
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
	 * ステートメントクローズ.
	 * 
	 * @param stmt ステートメント
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
