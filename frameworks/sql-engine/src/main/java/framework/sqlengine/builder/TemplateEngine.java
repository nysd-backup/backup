/**
 * Copyright 2011 the original author
 */
package framework.sqlengine.builder;

import java.io.InputStream;
import java.util.Map;

/**
 * TemplateEngineを使用してSQLを評価する.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public interface TemplateEngine {

	/**
	 * @param rowString ロード前SQL
	 * @return SQL
	 */
	public String load(InputStream rowString);
	
	/**
	 * @param rowString ロード後SQL
	 * @param parameter if文用パラメータ
	 * @return if文解析後SQL
	 */
	public String evaluate(String rowString,Map<String,Object> parameter);
}
