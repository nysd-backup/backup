/**
 * Copyright 2011 the original author
 */
package kosmos.framework.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility for Name.
 * 
 * @author yoshida-n
 * @version 2010/10/30 新規作成
 */
public final class NamingUtils {

	/**
	 * インスタンス生成不可
	 */
	private NamingUtils() {
	}

	/**
	 * 先頭の文字を大文字に変換する.
	 * 
	 * @param name 文字列
	 * @return 先頭の文字を大文字に変換した文字列
	 */
	public static final String capitalizeName(String name) {
		return StringUtils.capitalize(name);
	}

	/**
	 * 先頭の文字を小文字に変換する.
	 * 
	 * @param name 文字列
	 * @return 先頭の文字を小文字に変換した文字列
	 */
	public static final String uncapitalizeName(String name) {
		return StringUtils.uncapitalize(name);
	}

	/**
	 * Beanの名称を取得する.
	 * 
	 * @param clazz クラス
	 * @return Beanの名称
	 */
	public static final String resolveName(Class<?> clazz) {
		return StringUtils.uncapitalize(clazz.getSimpleName());
	}

	/**
	 * スネークケースをキャメルケースに変換する.
	 * 
	 * @param targetStr 対象文字列
	 * @return キャメルケースの対象文字列
	 */
	public static String snakeToCamel(String targetStr) {
		Pattern p = Pattern.compile("_([a-z])");
		Matcher m = p.matcher(targetStr.toLowerCase());

		StringBuffer sb = new StringBuffer(targetStr.length());
		while (m.find()) {
			m.appendReplacement(sb, m.group(1).toUpperCase());
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * キャメルケースをスネークケースに変換する.
	 * 
	 * @param targetStr 対象文字列
	 * @return スネークケースの対象文字列
	 */
	public static String camelToSnake(String targetStr) {
		String convertedStr = targetStr.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2").replaceAll("([a-z])([A-Z])", "$1_$2");
		return convertedStr.toUpperCase();
	}

}
