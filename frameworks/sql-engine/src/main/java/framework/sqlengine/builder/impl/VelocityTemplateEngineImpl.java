package framework.sqlengine.builder.impl;

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

import framework.sqlengine.builder.ConstAccessor;
import framework.sqlengine.builder.TemplateEngine;
import framework.sqlengine.exception.SQLEngineException;

/**
 * Velocityを使用してif斁E��解析すめE
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class VelocityTemplateEngineImpl implements TemplateEngine{

	/** SQLファイル斁E��コーチE*/
	protected static final String CHARSET = "UTF-8";

	/** 改行文孁E*/
	private static final String SEPARATOR = "\n";
	
	/** ソートエンジン */
	private static final LengthComparator COMPARATOR = new LengthComparator();
	
	/** 制御構文にマッチする正規表現パターン */
	private static final Pattern controlStatementPattern = Pattern.compile("--%\\s*(\\w+)");

	/** 1行コメンチEヒント句を除ぁEにマッチする正規表現パターン */
	private static final Pattern singleLineCommentPattern = Pattern.compile("--([^+].*[\n\r]*)");
	
	/** 褁E��行コメンチEヒント句を除ぁEにマッチする正規表現パターン */
	private static final Pattern multiLineCommentPattern = Pattern.compile("/\\*\\**[^+]([^/*][^*]*\\*+)*/", Pattern.MULTILINE);
	
	/** 定数アクセサ. */
	private ConstAccessor accessor = new ConstAccessorImpl();
	
	/**
	 * @param accessor 定数アクセサ
	 */
	public void setConstAccessor(ConstAccessor accessor){
		this.accessor = accessor;
	}
	
	/**
	 * @see framework.sqlengine.builder.TemplateEngine#load(java.io.InputStream)
	 */
	@Override
	public String load(InputStream source){
		Scanner scanner = null;
		try {
			scanner = new Scanner(source,CHARSET);
			scanner.useDelimiter(SEPARATOR);

			StringBuffer templateSQL = new StringBuffer();
			int mode = 0; // モーチE
			while (scanner.hasNext()) {
				String line = scanner.next();
				if (line.startsWith("--% end") && mode == 1) {
					// defineモード終亁E
					mode = 0;
				} else if (line.startsWith("--% define")) {
					// defineモード開姁E
					mode = 1;
				} else {
					if (mode != 1) {
						// defineモード以外だと行追加
						templateSQL.append(line).append(SEPARATOR);
					}
				}
			};
			return convert(templateSQL.toString());
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}
	
	/**
	 * 型変換する
	 * @param template SQL
	 * @return  変換後SQL
	 */
	protected String convert(String template){
		
		String vtl = template;
		// '#'めE\#"としてエスケーチE
		vtl = vtl.replaceAll("#", "\\\\#");
		// 制御構文"--%"めE#"に変換
		vtl = controlStatementPattern.matcher(vtl).replaceAll("#$1");
		// SQLコメントを削除
		vtl = singleLineCommentPattern.matcher(vtl).replaceAll("##$1");
		vtl = multiLineCommentPattern.matcher(vtl).replaceAll("#*$1*#");
		// if斁E�Eの変数置揁E
		String[] lines = vtl.split("[\r\n]");
		StringBuilder buff = new StringBuilder();
		for (String line : lines) {
			Set<String> duplicationCheck = new HashSet<String>();
			if (line.contains("#if") || line.contains("#elseif")) {
				// fixed [バグ #30] シングルクオートを認識しなぁE
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
					// 定数設宁Ec_で始まる物琁E��称は定数なので、定数値に置き換える
					Object[] val = accessor.getConstTarget(token);
					if (val.length > 0) {
						Object o = val[0];					
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
	 * @see framework.sqlengine.builder.TemplateEngine#evaluate(java.lang.String, java.util.Map)
	 */
	@Override
	public String evaluate(String rowString, Map<String, Object> parameter) {
		// 変換開姁E
		Map<String, Object> evaluatingParam = createEvaluatingParam(parameter);
		VelocityContext context = new VelocityContext(evaluatingParam);
		StringWriter writer = new StringWriter();
		try {
			Velocity.evaluate(context, writer, "", rowString);
		} catch (Exception e) {
			throw new SQLEngineException(e);
		}
		writer.flush();
		// エスケープしてめE\#'となる�EでそれめE#'に戻ぁE
		String sql = writer.toString().replaceAll("\\\\#", "#");
		try {
			writer.close();
		} catch (IOException e) {
			throw new SQLEngineException(e);
		}
		return sql;
	}
	
	/**
	 * 評価用のパラメータ作�E.
	 * 型変換などが忁E��であればここで実施する.
	 * 
	 * @param parameter パラメータ
	 * @return 変換後パラメータ
	 */
	protected Map<String,Object> createEvaluatingParam(Map<String,Object> parameter){		
		return parameter;
	}
	
	/** 斁E���E比輁E*/
	private static class LengthComparator implements Comparator<String> {
		
		/**
		 * @param arg0 ト�Eク
		 * @param arg1 ト�Eクン
		 * @return 比輁E��果
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
