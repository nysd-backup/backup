package org.coder.gear.query.free.loader;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;




/**
 * The template engine using the Velocity.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class VelocityTemplateEngine implements TemplateEngine, ConstantAccessible{

	/** the encoding */
	protected static final String CHARSET = "UTF-8";

	/** the line separator */
	private static final String SEPARATOR = "\n";
	
	/** the comparator */
	private static final LengthComparator COMPARATOR = new LengthComparator();
	
	/** the pattern for velocity-statement */
	private static final Pattern controlStatementPattern = Pattern.compile("--%\\s*(\\w+)");
	
	/**
	 * @see org.coder.gear.query.free.loader.TemplateEngine#load(java.io.InputStream)
	 */
	@Override
	public String load(InputStream source){
		Scanner scanner = null;
		try {
			scanner = new Scanner(source,CHARSET);
			scanner.useDelimiter(SEPARATOR);

			StringBuffer templateSQL = new StringBuffer();
			while (scanner.hasNext()) {
				String line = scanner.next();
				templateSQL.append(line).append(SEPARATOR);
			}
			return convert(templateSQL.toString());
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}
	
	/**
	 * Convert the SQL 
	 * 
	 * @param template the template
	 * @return the SQL
	 */
	protected String convert(String template){
		
		String vtl = template;
		// '#'を"\#"としてエスケープ
		vtl = vtl.replaceAll("#", "\\\\#");
		// 制御構文"--%"を"#"に変換
		vtl = controlStatementPattern.matcher(vtl).replaceAll("#$1");
		// if変数置換
		String[] lines = vtl.split("[\r\n]");
		StringBuilder buff = new StringBuilder();
		for (String line : lines) {
			Set<String> duplicationCheck = new HashSet<String>();
			if (line.contains("#if") || line.contains("#elseif")) {
				// fixed [バグ #30] シングルクオートを認識しない。
				String newLine = line.replaceAll("'", "\"");

				int begin = newLine.indexOf('(');
				int end = newLine.lastIndexOf(')');
				String match = newLine.substring(begin + 1, end).trim();
				String[] tempToken = match.split("[!=+&-*/ ]+");
				List<String> array = Arrays.asList(tempToken);
				Collections.sort(array, COMPARATOR);
				String replaceString = String.copyValueOf(match.toCharArray());

				for (String token : array) {
					if (!(token.matches("\\w+") && !token.equalsIgnoreCase("true") && !token.equalsIgnoreCase("false") && !token.startsWith("\"") && !duplicationCheck.contains(token))) {
						continue;
					}
					// 定数置換
					if (isValidKey(token)) {
						Object o = getConstTarget(token);				
						if( o instanceof String){	
							replaceString = replaceString.replace(token, "\"" + o.toString() + "\"");
						}else{
							replaceString = replaceString.replace(token,o.toString());
						}			
					} else {
						replaceString = replaceString.replace(token, "$" + token);
					}
					duplicationCheck.add(token);				
				}
				buff.append(newLine.replace(match, replaceString)).append("\n");
			} else if (line.length() > 0) {
				buff.append(line).append("\n");
			}
		}
		vtl = buff.toString().trim();
		return vtl;
	}
	
	/**
	 * @see org.coder.gear.query.free.loader.TemplateEngine#evaluate(java.lang.String, java.util.Map)
	 */
	@Override
	public String evaluate(String rowString, Map<String, Object> parameter) {
		// 変換開始
		Map<String, Object> evaluatingParam = createEvaluatingParam(parameter);
		VelocityContext context = new VelocityContext(evaluatingParam);
		StringWriter writer = new StringWriter();
		try {
			Velocity.evaluate(context, writer, "", rowString);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		writer.flush();
		// エスケープしても"\#'となるのでそれでも"#'に戻す
		String sql = writer.toString().replaceAll("\\\\#", "#");
		try {
			writer.close();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		return sql;
	}
	
	/**
	 * Creates the evaluating parameter.
	 * Convert the parameter if needed.
	 * 
	 * @param parameter the parameter
	 * @return the converted the parameter
	 */
	protected Map<String,Object> createEvaluatingParam(Map<String,Object> parameter){		
		return parameter;
	}
	
	/** */
	private static class LengthComparator implements Comparator<String> {
		
		/**
		 * @param arg0 the token
		 * @param arg1 the token
		 * @return the result
		 */
		public int compare(String arg0, String arg1) {
			if (arg0.length() > arg1.length()) {
				return -1;
			} else {
				return 1;
			}
		}

	}
	
}
