/**
 * Use is subject to license terms.
 */
package framework.service.core.advice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import framework.logics.log.LogWriter;
import framework.logics.log.LogWriterFactory;
import framework.service.core.utils.QueryUtils;

/**
 * ステートメント生成アドバイス.
 *
 * @author yoshida-n
 * @version	2011/05/02 created.
 */
public class StatementBuilderInterceptor implements Advice{

	private static final LogWriter LOG = LogWriterFactory.getLog(StatementBuilderInterceptor.class);

	/** ログ出力を無視するクエリIDのリスト */
	private List<String> ignoreList = new ArrayList<String>();
	
	/**
	 * @param ignoreList ログ無視SQLIDリスト
	 */
	public void setIgnoreList(String ignoreList){
		String[] ignore = ignoreList.split(",");
		this.ignoreList = Arrays.asList(ignore);
	}

	/**
	 * @see framework.service.core.advice.Advice#before(java.lang.Object, java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void before(Object target, String method, Object[] argments) {
		
		if(ignoreList.contains(argments[3])){
			return;
		}
		String sql = String.class.cast(argments[1]); 		
		List<Object> bindList = (List<Object>)argments[2];
		String converted = QueryUtils.applyValues(bindList, sql);
		LOG.info(String.format("sql for prepared statement %s%s","\r\n",converted));				
	}

	/**
	 * @see framework.service.core.advice.Advice#after(java.lang.Object, java.lang.String, java.lang.Object[], java.lang.Object)
	 */
	@Override
	public void after(Object target, String method, Object[] argments,Object result) {
	}

}
