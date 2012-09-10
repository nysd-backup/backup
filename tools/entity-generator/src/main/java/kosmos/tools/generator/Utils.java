/**
 * Copyright 2011 the original author
 */
package kosmos.tools.generator;

import java.util.Locale;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class Utils {

	/**
	 * キャメルケースに変換する。
	 * @param s 元
	 * @param delimiter デリミッタ
	 * @return
	 */
	public static String toCamelCase(String s) {
		StringBuilder builder = new StringBuilder();
		String[] words = s.split("_");
		for (int i = 0; i < words.length; i++) {
			String word = words[i];
			if (i == 0) {
				builder.append(word.toLowerCase(Locale.getDefault()));
			} else {
				builder.append(word.substring(0, 1).toUpperCase());
				builder.append(word.substring(1).toLowerCase());
			}
		}
		return builder.toString();
	}

}
