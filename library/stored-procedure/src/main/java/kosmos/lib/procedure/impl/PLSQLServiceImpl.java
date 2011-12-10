/**
 * Copyright 2011 the original author
 */
package kosmos.lib.procedure.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import kosmos.lib.procedure.CallRequest;
import kosmos.lib.procedure.CallRequest.Bind;
import kosmos.lib.procedure.CallRequest.InOutType;
import kosmos.lib.procedure.CallRequest.PlSqlDataType;
import kosmos.lib.procedure.CallResult;
import kosmos.lib.procedure.CallableService;
import kosmos.lib.procedure.ExceptionHandler;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

/**
 * PLSQL Service.
 *
 * @author yoshida-n
 * @version	created.
 */
public class PLSQLServiceImpl implements CallableService {
	
	private int maxArraySize = 32767;

	private int queryTimeout = 0;
	
	private ExceptionHandler handler = new ExceptionHandlerImpl();
	
	/**
	 * @param maxArraySize the maxArraySize to set
	 */
	public void setMaxArraySize(int maxArraySize){
		this.maxArraySize = maxArraySize;
	}
	
	/**
	 * @param timeout the timeout to set
	 */
	public void setQueryTimeout(int timeout){
		this.queryTimeout = timeout;
	}
	
	/**
	 * @param exceptionHandler the exceptionHandler to set
	 */
	public void setExceptionHandler(ExceptionHandler exceptionHandler){
		this.handler = exceptionHandler;
	}
	
	/**
	 * @see kosmos.lib.procedure.CallableService#execute(kosmos.lib.procedure.CallRequest, java.sql.Connection)
	 */
	@Override
	public CallResult execute(CallRequest parameter,Connection connection) {

		List<Bind> params = parameter.getParameter();
		OracleCallableStatement statement = null;
		CallResult result = null;
		try {

			String execSql = createCallableString(connection.getMetaData().getUserName() + "." + parameter.getCallString(), params);
			statement = (OracleCallableStatement) connection.prepareCall(String.format("begin %s; end;",execSql));
			configure(statement,parameter);
			setParameter(params, statement);
			statement.execute();
			result =  getResult(statement, params);
			
		} catch (SQLException e) {			
			handler.handleException(e);
		} catch (RuntimeException e) {
			throw e;
		} catch (Error e) {
			throw e;
		} finally {			
			if (statement != null) {
				try{
					statement.close();
				}catch(SQLException sqle){
					
				}
			}
		}
		return result;
	}
	
	/**
	 * Configures the statement.
	 * 
	 * @param statement the statement
	 * @param parameter the parameter
	 * @throws SQLException the exception
	 */
	protected void configure(OracleCallableStatement statement , CallRequest parameter) throws SQLException{
		if(parameter.getQueryTimeout() > 0){
			statement.setQueryTimeout(parameter.getQueryTimeout());
		}else if(this.queryTimeout > 0 ){
			statement.setQueryTimeout(queryTimeout);			
		}
	}

	/**
	 * Creates the SQL.
	 * 
	 * @param nativeString the SQL string
	 * @param paramIndex the paramIndex
	 * @return the value
	 */
	private String createCallableString(String nativeString, List<Bind> paramIndex) {

		// 実行プロシージャ作成
		StringBuffer execProcedure = new StringBuffer(nativeString);
		execProcedure.append("(");
		for (int i = 0; i < paramIndex.size(); i++) {
			if (i > 0) {
				execProcedure.append(", ");
			}
			execProcedure.append("?");
		}
		execProcedure.append(")");

		return execProcedure.toString();
	}

	/**
	 * Set the parameter.
	 * 
	 * @param params the parameter index
	 * @param statement the statement
	 * @throws SQLException the SQL exception
	 */
	private void setParameter(List<Bind> params, OracleCallableStatement statement) throws SQLException {

		int oraclePlSqlDataType = OracleTypes.OTHER;
		int size = params.size();
		for (int i = 0; i < size; i++) {

			Bind bind = params.get(i);

			// IN・INOUTパラメータの設定
			if (InOutType.InOut == bind.inout || InOutType.In == bind.inout) {

				// データタイプが配列かそうでないかにより値セット方法変更
				if (PlSqlDataType.NumberArray == bind.type || PlSqlDataType.VarcharArray == bind.type) {
					
					// 配列
					int arrayLength = ((Object[]) (bind.value)).length;
					if (PlSqlDataType.NumberArray == bind.type) {
						oraclePlSqlDataType = OracleTypes.NUMBER;
					} else if (PlSqlDataType.VarcharArray == bind.type) {
						oraclePlSqlDataType = OracleTypes.VARCHAR;
					} else {
						oraclePlSqlDataType = OracleTypes.OTHER;
					}

					// パラメータ値セット
					statement.setPlsqlIndexTable(i + 1, bind.value, arrayLength, arrayLength, oraclePlSqlDataType, maxArraySize);

				} else {
					// 配列以外
					statement.setObject(i + 1, bind.value);
				}
			}

			// OUT・INOUTパラメータの設定
			if (InOutType.InOut == bind.inout || InOutType.Out == bind.inout) {

				// データタイプが配列かそうでないかにより値セット方法変更
				if (PlSqlDataType.NumberArray == bind.type || PlSqlDataType.VarcharArray == bind.type) {

					// 配列
					if (PlSqlDataType.NumberArray == bind.type) {
						oraclePlSqlDataType = OracleTypes.NUMBER;
					} else if (PlSqlDataType.VarcharArray == bind.type) {
						oraclePlSqlDataType = OracleTypes.VARCHAR;
					} else {
						oraclePlSqlDataType = OracleTypes.OTHER;
					}

					statement.registerIndexTableOutParameter(i + 1, maxArraySize, oraclePlSqlDataType, maxArraySize);

				} else {
					// オラクルデータタイプの取得
					if (PlSqlDataType.Number == bind.type) {
						oraclePlSqlDataType = OracleTypes.NUMBER;
					} else if (PlSqlDataType.Char == bind.type) {
						oraclePlSqlDataType = OracleTypes.CHAR;
					} else if (PlSqlDataType.Varchar == bind.type) {
						oraclePlSqlDataType = OracleTypes.VARCHAR;
					} else if (PlSqlDataType.Date == bind.type) {
						oraclePlSqlDataType = OracleTypes.DATE;
					} else {
						oraclePlSqlDataType = OracleTypes.OTHER;
					}

					// 型指定
					statement.registerOutParameter(i + 1, oraclePlSqlDataType);
				}
			}
		}
	}

	/**
	 * Gets the PLSQL result.
	 * 
	 * @param statement the statement
	 * @param params the parameter
	 * @return the result
	 * @throws SQLException the exception
	 */
	private CallResult getResult(OracleCallableStatement statement, List<Bind> params) throws SQLException {
		// パラメータ値反映
		CallResult res = new CallResult();
		for (int i = 0; i < params.size(); i++) {
			// パラメータ取得
			Bind bind = params.get(i);
			if (InOutType.Out == bind.inout || InOutType.InOut == bind.inout) {

				Bind resBind = bind;

				if (PlSqlDataType.NumberArray == bind.type) {
					// 数値配列
					resBind.value = statement.getPlsqlIndexTable(i + 1);
				} else if (PlSqlDataType.VarcharArray == bind.type) {
					// 可変長文字列配列
					resBind.value = statement.getPlsqlIndexTable(i + 1);
					// 日付型
				} else if (PlSqlDataType.Date == bind.type) {
					resBind.value = statement.getTimestamp(i + 1);
				} else {
					// それ以外
					resBind.value = statement.getObject(i + 1);
				}
				res.put(resBind.name, resBind);

			}

		}

		return res;

	}
}
