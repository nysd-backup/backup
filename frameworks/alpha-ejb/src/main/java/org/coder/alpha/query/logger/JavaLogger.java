package org.coder.alpha.query.logger;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.eclipse.persistence.logging.EclipseLinkLogRecord;
import org.eclipse.persistence.logging.JavaLog;
import org.eclipse.persistence.logging.SessionLog;
import org.eclipse.persistence.logging.SessionLogEntry;

/**
 * Logger.
 * glassfishがデフォルトでJavaLoggerを使用するのでglassfishではこれを使うことを推奨する。
 * glassfishではeclipselink-should-display-data=trueにしておくこと。
 * 
 * @author yoshida-n
 * @version 2012/10/16 新規作成
 */
public class JavaLogger extends JavaLog {

	/** パターン */
	private static final Pattern PATTERN = Pattern.compile("(.+)\tbind =>.+\\[(.+)\\]");

	/**
	 * @see org.eclipse.persistence.logging.JavaLog#internalLog(org.eclipse.persistence.logging.SessionLogEntry, java.util.logging.Level, java.util.logging.Logger)
	 */
	@Override
	 protected void internalLog(SessionLogEntry entry, Level javaLevel, Logger logger) {
       
		//通常のログ
		super.internalLog(entry, javaLevel, logger);
		 
		//SQlの詳細なログ
		String nameSpace = entry.getNameSpace();
		if (SessionLog.SQL.equals(nameSpace)) {
			String sql = StringUtils.replace(StringUtils.replace(entry.getMessage(), "\n", " "), "\r", " ");
			String completeQuery = getCompleteQuery(sql);
			if(completeQuery != null){
				entry.setMessage(completeQuery);
				EclipseLinkLogRecord lr = new EclipseLinkLogRecord(javaLevel, formatMessage(entry)); 
		        lr.setLoggerName(getNameSpaceString(entry.getNameSpace()));
		        if (shouldPrintSession()) {
		            lr.setSessionString(getSessionString(entry.getSession()));
		        }
		        if (shouldPrintConnection()) {
		            lr.setConnection(entry.getConnection());
		        }
		        lr.setShouldPrintDate(shouldPrintDate());
		        lr.setShouldPrintThread(shouldPrintThread());
				logger.log(lr);
			}			
		}
	}

	/**
	 * SQLのバインドパラメータを置換する.
	 * 
	 * @param sql SQL
	 * @return 置換されたSQL
	 */
	private String getCompleteQuery(String sql) {
		Matcher mt = PATTERN.matcher(sql);
		if (mt.find()) {
			String query = mt.group(1);
			String arguments = mt.group(2);
			String[] args = arguments.split(",");
			int i = 0;
			while (query.contains("?")) {
				if (i >= args.length) {
					return null;
				}
				query = StringUtils.replaceOnce(query, "?", args[i]);
				i++;
			}
			return StringUtils.replace(query, "\t", " ");
		}
		return null;
	}

}
