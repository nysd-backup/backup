/**
 * Copyright 2011 the original author
 */
package kosmos.framework.utility;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility for String.
 *
 * @author yoshida-n
 * @version 2011/08/31	created.
 */
public final class StringUtils implements Utils.StringScope {

	/* パターンの保持 */
	private static Pattern patternNarrowKatakana;
	private static Pattern patternWideKatakana;
	private static Pattern patternHtmlForUrl;
	private static Pattern patternWideAlphaNum;
	private static Pattern patternAlphaNum;
	private static Pattern patternNarrowAlphNumKana;
	private static Pattern patternNumber;
	private static Pattern patternWideNumber;

	static {
		// パターンの作成(負荷軽減の為)
		patternNarrowKatakana = Pattern.compile(Formats.NAROW_KATAKANA);
		patternWideKatakana = Pattern.compile(Formats.WIDE_KATAKANA);
		patternHtmlForUrl = Pattern.compile(Formats.HTML);
		patternWideAlphaNum = Pattern.compile(Formats.WIDE_ALPHA_NUM);
		patternAlphaNum = Pattern.compile(Formats.ALPHA_NUM);
		patternNarrowAlphNumKana = Pattern.compile(Formats.NARROW_ALPHA_NUM_KANA);
		patternNumber = Pattern.compile(Formats.NUMBER);
		patternWideNumber = Pattern.compile(Formats.WIDE_NUMBER);
	}

	/**
	 * プライベートコンストラクタ.
	 */
	private StringUtils() {
	}
	
	

	// //////////////////////////////////////////////
	// 条件判断メソッド群
	// //////////////////////////////////////////////
	/**
	 * 空文字または<code>null</code>かを判定する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.isEmpty(null)	-&gt; true
	 * StringUtils.isEmpty(&quot;&quot;)	-&gt; true
	 * StringUtils.isEmpty(&quot;abc&quot;)	-&gt; false
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param s 対象文字列
	 * @return <code>true</code>:nullまたはlengthが0、<code>false</code>:lengthが1以上
	 */
	public static boolean isEmpty(String s) {
		return s == null || s.length() == 0;
	}

	/**
	 * 空文字または<code>null</code>でないかを判定する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.isNotEmpty(null)	-&gt; false
	 * StringUtils.isNotEmpty(&quot;&quot;)		-&gt; false
	 * StringUtils.isNotEmpty(&quot;abc&quot;)	-&gt; true
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param s 対象文字列
	 * @return <code>true</code>:lengthが1以上、<code>false</code>:nullまたはlengthが0
	 */
	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}

	/**
	 * ２つの文字列を比較して等価かどうかを返す
	 * 
	 * @param str1 比較文字列１
	 * @param str2 比較文字列２
	 * @return ２つの文字列が等しい場合もしくは両方とも<code>null</code>の場合<code>true</code>、それ以外は<code>false</code>
	 */
	public static boolean equals(String str1, String str2) {
		if (str1 == null) {
			return str2 == null;
		}
		return str1.equals(str2);
	}

	/**
	 * 文字列置換を行う
	 * <p>
	 * ※正規表現可 <br />
	 * ※引数どちらかが <code>null</code> の場合は <code>null</code> を返す<br />
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.regex(&quot;hoge&quot;, &quot;h&quot;) -&gt; &quot;oge&quot; <br>
	 * </
	 * 
	 * <pre>>
	 * 
	 * @param str1 置換対象文字列
	 * @param str2 置換文字列
	 * @return  置換後の文字列
	 */
	public static String regex(String str1, String str2) {
		if (StringUtils.isEmpty(str1) || StringUtils.isEmpty(str2)) {
			return str1;
		}
		return str1.replaceAll(str2, "");
	}

	/**
	 * 数値チェックを行う
	 * <p>
	 * ※小数、マイナス可<br />
	 * ※<code>null</code> 又は空文字の場合は<code>true</code>を返す<br />
	 * <br />
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.isNumber(null)	-&gt; true
	 * StringUtils.isNumber(&quot;123&quot;)	-&gt; true
	 * StringUtils.isNumber(&quot;0x123&quot;)	-&gt; true
	 * StringUtils.isNumber(&quot;a123&quot;)	-&gt; false
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str チェック対象文字列
	 * @return 数値の場合は<code>true</code>を返す
	 */
	public static boolean isNumber(String str) {
		if (isEmpty(str)) {
			return true;
		}

		char[] chars = str.toCharArray();
		int sz = chars.length;
		boolean hasExp = false;
		boolean hasDecPoint = false;
		boolean allowSigns = false;
		boolean foundDigit = false;
		int start = 0;
		if (chars[0] == '-') {
			start = 1;
		}

		if (sz > start + 1) {
			if (str.equals("0x")) {
				return false;
			}
			if (chars[start] == '0' && chars[start + 1] == 'x') {
				for (int i = start + 2; i < chars.length; i++) {
					if ((chars[i] < '0' || chars[i] > '9') && (chars[i] < 'a' || chars[i] > 'f') && (chars[i] < 'A' || chars[i] > 'F')) {
						return false;
					}
				}
				return true;
			}
		}
		sz--;
		int i = start;
		while (i < sz || (i < sz + 1 && allowSigns && !foundDigit)) {
			if (chars[i] >= '0' && chars[i] <= '9') {
				foundDigit = true;
				allowSigns = false;

			} else if (chars[i] == '.') {
				if (hasDecPoint || hasExp) {
					return false;
				}
				hasDecPoint = true;
			} else if (chars[i] == 'e' || chars[i] == 'E') {
				if (hasExp) {
					return false;
				}
				if (!foundDigit) {
					return false;
				}
				hasExp = true;
				allowSigns = true;
			} else if (chars[i] == '+' || chars[i] == '-') {
				if (!allowSigns) {
					return false;
				}
				allowSigns = false;
				foundDigit = false; // we need a digit after the E
			} else {
				return false;
			}
			i++;
		}
		if (i < chars.length) {
			if (chars[i] >= '0' && chars[i] <= '9') {
				return true;
			}
			if (chars[i] == 'e' || chars[i] == 'E') {
				return false;
			}
			if (!allowSigns && (chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F')) {
				return foundDigit;
			}
			if (chars[i] == 'l' || chars[i] == 'L') {
				return foundDigit && !hasExp;
			}
			return false;
		}
		return !allowSigns && foundDigit;
	}

	/**
	 * 数値チェックを行う
	 * <p>
	 * ※小数、マイナス不可<br />
	 * ※<code>null</code> 又は空文字の場合は<code>true</code>を返す<br />
	 * <br />
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.isDigit(null)	-&gt; true
	 * StringUtils.isDigit(&quot;123&quot;)	-&gt; true
	 * StringUtils.isDigit(&quot;-1234&quot;)	-&gt; false
	 * StringUtils.isDigit(&quot;12.88&quot;)	-&gt; false
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param value チェック対象文字列
	 * @return 数値の場合は<code>true</code>を返す
	 */
	public static boolean isDigit(String value) {
		if (StringUtils.isEmpty(value)) {
			return true;
		}
		return value.matches("^[0-9]+$");
	}

	/**
	 * 全角英数字チェックを行う
	 * 
	 * @param value 対象文字列
	 * @return <code>true</code>:全角英数字の場合,<code>false</code>:全角英数字以外の場合
	 */
	public static boolean isWideAlphaNum(String value) {
		if (StringUtils.isEmpty(value)) {
			return false;
		}
		Matcher matcher = patternWideAlphaNum.matcher(value);
		return matcher.matches();
	}

	/**
	 * 英数字チェックを行う<br>
	 * 
	 * @param value 対象文字列
	 * @return <code>true</code>:英数字の場合,<code>false</code>:英数字以外の場合
	 */
	public static boolean isAlphaNum(String value) {
		if (StringUtils.isEmpty(value)) {
			return false;
		}
		Matcher matcher = patternAlphaNum.matcher(value);
		return matcher.matches();
	}

	/**
	 * 半角英数カナチェックを行う<br>
	 * 
	 * @param value 対象文字列
	 * @return <code>true</code>:半角英数カナの場合,<code>false</code>:半角英数カナ以外の場合
	 */
	public static boolean isNarrowAlphaNumericKatakana(String value) {
		if (StringUtils.isEmpty(value)) {
			return false;
		}
		Matcher matcher = patternNarrowAlphNumKana.matcher(value);
		return matcher.matches();
	}

	/**
	 * 半角英数チェックを行う
	 * <p>
	 * ※<code>null</code> 又は空文字の場合は<code>true</code>を返す。<br />
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.isNarrowAlphabetNumber(null)	-&gt; true
	 * StringUtils.isNarrowAlphabetNumber(&quot;123 abc&quot;)	-&gt; true
	 * StringUtils.isNarrowAlphabetNumber(&quot;あいう&quot;)	-&gt; false
	 * StringUtils.isNarrowAlphabetNumber(&quot;ｱｲｳ&quot;)	-&gt; false
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str チェック対象文字列
	 * @return 半角英数と記号「.,_-」で構成されている文字列であれば<code>true</code>
	 */
	public static boolean isNarrowAlphabetNumber(String str) {
		if (isEmpty(str)) {
			return true;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (!isNarrowAlphabetNumber(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 文字列が全角カタカナであることをチェックする
	 * 
	 * @param s 対象文字列
	 * @return <code>true</code>:全角カタカナ,<code>false</code>:全角カタカナ以外が混ざっている
	 */
	public static boolean isWideKatakana(String s) {
		if (s == null || s.length() == 0) {
			return false;
		}
		Matcher matcher = patternWideKatakana.matcher(s);
		return matcher.matches();
	}

	/**
	 * 文字列が半角カタカナであることをチェックする
	 * 
	 * @param s 対象文字列
	 * @return <code>true</code>:半角カタカナ,<code>false</code>:半角カタカナ以外が混ざっている
	 */
	public static boolean isNarrowKatakana(String s) {
		if (s == null || s.length() == 0) {
			return false;
		}
		Matcher matcher = patternNarrowKatakana.matcher(s);
		return matcher.matches();
	}

	/**
	 * 全角数字であることをチェックする
	 * 
	 * @param str 対象文字列
	 * @return <code>true</code>:全角数字の場合,<code>false</code>:全角数字以外の場合
	 */
	public static boolean isWideNumeric(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		// 全角数字を半角数字に変換
		String narrowNumeric = toNarrowNumeric(str);
		Matcher matcher = patternNumber.matcher(narrowNumeric);
		// 数値として正しいか判定
		if (!matcher.matches()) {
			return false;
		}
		Matcher wideMatcher = patternWideNumber.matcher(str);
		return wideMatcher.matches();
	}

	/**
	 * 半角文字チェックメソッド<br>
	 * 対象文字列が半角文字か評価し、結果を返します。<br>
	 * 文字列のバイト長と文字列長を比較し判定します。<br>
	 * 
	 * <pre>
	 * [例1] <dd>StringUtils.isNarrowCharString("Aq*2ｶﾋﾞｱｨ.");
	 * 戻り値<dd>true
	 * </pre>
	 * 
	 * @param string 対象文字列
	 * @return boolean 半角文字列の場合、trueを返します。
	 */
	public static boolean isNarrowCharString(String string) {

		if (StringUtils.isEmpty(string)) {
			return false;
		}

		// チェック対象文字列のバイトを取得します。
		byte[] bytes;
		try {
			bytes = string.getBytes(Encodings.WINDOWS_31J);
		} catch (UnsupportedEncodingException e) {
			bytes = string.getBytes();
		}

		// チェック対象文字列長を取得します。
		int length = string.length();

		return length == bytes.length;
	}

	/**
	 * 文字列が、指定された接頭辞で始まるかどうかを判定する
	 * 
	 * @param val 値
	 * @param preffix 接頭辞
	 * @return 指定された接頭辞で始まる場合は<code>true</code>
	 */
	public static boolean startsWith(String val, String preffix) {
		if (isEmpty(val)) {
			return false;
		}
		return val.startsWith(preffix);
	}

	/**
	 * 文字列が、指定された接尾辞で終わるかどうかを判定する
	 * 
	 * @param val 値
	 * @param suffix 接尾辞
	 * @return 指定された接尾辞で終わる場合は<code>true</code>
	 */
	public static boolean endsWith(String val, String suffix) {
		if (isEmpty(val)) {
			return false;
		}
		return val.endsWith(suffix);
	}

	// //////////////////////////////////////////////
	// 文字列変換メソッド群
	// //////////////////////////////////////////////
	/**
	 * 空白を除去する
	 * 
	 * <pre>
	 * StringUtils.trim(null)          = null
	 * StringUtils.trim("")            = ""
	 * StringUtils.trim("     ")       = ""
	 * StringUtils.trim("abc")         = "abc"
	 * StringUtils.trim("    abc    ") = "abc"
	 * </pre>
	 * 
	 * @param s 対象文字列
	 * @return 空白除去をした文字列（引数がnullの場合はnullを返す）
	 */
	public static String trim(String s) {
		return s == null ? null : s.trim();
	}

	/**
	 * 先頭の文字を大文字に変換する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.capitalize(null)  = null
	 * StringUtils.capitalize("")    = ""
	 * StringUtils.capitalize("cat") = "Cat"
	 * StringUtils.capitalize("cAt") = "CAt"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str 文字列
	 * @return 変換後の文字列
	 */
	public static String capitalize(String str) {
		if (isEmpty(str)) {
			return str;
		}
		return new StringBuilder(str.length()).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
	}

	/**
	 * 先頭の文字を大文字に変換する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.uncapitalize(null)  = null
	 * StringUtils.uncapitalize("")    = ""
	 * StringUtils.uncapitalize("Cat") = "cat"
	 * StringUtils.uncapitalize("CAT") = "cAT"
	 * </pre>
	 * 
	 * @param str 文字列
	 * @return 変換後の文字列
	 */
	public static String uncapitalize(String str) {
		if (isEmpty(str)) {
			return str;
		}
		return new StringBuilder(str.length()).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
	}

	/**
	 * 指定した文字列で左詰めする
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.leftPad(&quot;hoge&quot;, 6, &quot; &quot;)	-&gt; &quot;  hoge&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str 文字列
	 * @param size パディング回数
	 * @param padStr パディング文字列
	 * @return パディング後の文字列
	 */
	public static String leftPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		String workPadStr = padStr;
		if (isEmpty(workPadStr)) {
			workPadStr = " ";
		}
		int padLen = workPadStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return leftPad(str, size, workPadStr.charAt(0));
		}

		if (pads == padLen) {
			return workPadStr.concat(str);
		} else if (pads < padLen) {
			return workPadStr.substring(0, pads).concat(str);
		} else {
			char[] padding = new char[pads];
			char[] padChars = workPadStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return new String(padding).concat(str);
		}
	}

	/**
	 * 指定した文字列で右詰めする
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.rightPad(&quot;hoge&quot;, 6, &quot; &quot;)	-&gt; &quot;hoge  &quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str 文字列
	 * @param size パディング回数
	 * @param padStr パディング文字列
	 * @return パディング後の文字列
	 */
	public static String rightPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		String workPadStr = padStr;
		if (isEmpty(workPadStr)) {
			workPadStr = " ";
		}
		int padLen = workPadStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return rightPad(str, size, workPadStr.charAt(0));
		}

		if (pads == padLen) {
			return str.concat(workPadStr);
		} else if (pads < padLen) {
			return str.concat(workPadStr.substring(0, pads));
		} else {
			char[] padding = new char[pads];
			char[] padChars = workPadStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return str.concat(new String(padding));
		}
	}

	/**
	 * 0で左詰めする
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.leftPadZero(&quot;111&quot;,6)	-&gt; &quot;000111&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str 文字列
	 * @param size パディング回数
	 * @return パディング後の文字列
	 */
	public static String leftPadZero(String str, int size) {
		return leftPad(str, size, "0");
	}

	/**
	 * 0で右詰めする
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.rightPadZero(&quot;111&quot;,6)	-&gt; &quot;111000&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str 文字列
	 * @param size パディング回数
	 * @return パディング後の文字列
	 */
	public static String rightPadZero(String str, int size) {
		return rightPad(str, size, "0");
	}

	/**
	 * 指定した位置まで文字列を左から切り捨てる
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.left(null, *)    = null
	 * StringUtils.left(*, -ve)     = ""
	 * StringUtils.left("", *)      = ""
	 * StringUtils.left("abc", 0)   = ""
	 * StringUtils.left("abc", 2)   = "ab"
	 * StringUtils.left("abc", 4)   = "abc"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str 文字列
	 * @param len 切捨て位置
	 * @return 切捨て後の文字列
	 */
	public static String left(String str, int len) {
		if (str == null) {
			return null;
		}
		if (str.length() <= len) {
			return str;
		} else {
			return str.substring(0, len);
		}
	}

	/**
	 * 指定した位置まで文字列を右から切り捨てる 　
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.right(null, *)    = null
	 * StringUtils.right(*, -ve)     = ""
	 * StringUtils.right("", *)      = ""
	 * StringUtils.right("abc", 0)   = ""
	 * StringUtils.right("abc", 2)   = "bc"
	 * StringUtils.right("abc", 4)   = "abc"
	 * </pre>
	 * 
	 * @param str 文字列
	 * @param len 切捨て位置
	 * @return 切捨て後の文字列 　　　
	 */
	public static String right(String str, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0) {
			return "";
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(str.length() - len);
	}

	/**
	 * 数値文字列をカンマ付きフォーマットへ変換する
	 * 
	 * @param string 文字列
	 * @return カンマ付き文字列、引数が数値と判断できない場合は空文字列
	 */
	public static String toCommaFormat(String string) {
		return toCommaFormat(string, 0);
	}

	/**
	 * 数値文字列をカンマ付きフォーマットへ変換する
	 * 
	 * @param string 文字列
	 * @param minFractionDigits 最小小数桁数
	 * @return カンマ付き文字列、引数が数値と判断できない場合は空文字列
	 */
	public static String toCommaFormat(String string, int minFractionDigits) {
		if (isEmpty(string)) {
			return "";
		}
		if (isNumber(string)) {
			NumberFormat nf = new DecimalFormat();
			nf.setMinimumFractionDigits(minFractionDigits);
			nf.setMaximumFractionDigits(10);
			return nf.format(Double.parseDouble(string));
		}
		return "";
	}

	/**
	 * 指定した文字で文字列を文字列配列に分割する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.split(&quot;hoge:piyo&quot;, &quot;:&quot;)	-&gt; [&quot;hoge&quot;, &quot;piyo&quot;]
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str 文字列
	 * @param separatorChars セパレータ
	 * @return 分割後の文字列配列
	 */
	public static String[] split(String str, String separatorChars) {
		return splitWorker(str, separatorChars, -1, false);
	}

	/**
	 * <code>null</code>または空文字以外の場合に文字列を結合する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.join(&quot;hoge&quot;, &quot;piyo&quot;)	-&gt; &quot;hogepiyo&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param values 結合文字列
	 * @return 結合した文字列
	 */
	public static String join(String... values) {
		return joinDelimiter("", values);
	}

	/**
	 * nullまたは空文字以外の場合に文字列を結合する
	 * <p>
	 * ※指定した区切り文字列を文字間に付与する<br />
	 * <br />
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.join(&quot;:&quot;, &quot;hoge&quot;, &quot;piyo&quot;)	-&gt; &quot;hoge:piyo&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param delimiter セパレート文字列
	 * @param values 結合文字列
	 * @return 結合した文字列
	 */
	public static String joinDelimiter(String delimiter, String... values) {

		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			if (!isEmpty(values[i])) {
				buf.append(values[i]);
				if (i < values.length - 1) {
					buf.append(delimiter);
				}
			}
		}
		return buf.toString();
	}

	/**
	 * 先頭から指定した文字数を抽出する
	 * <p>
	 * Example
	 * 
	 * <pre>
	 * StringUtils.abbreviate(null, *)		-&gt; null
	 * StringUtils.abbreviate(&quot;&quot;, 4)		-&gt; &quot;&quot;
	 * StringUtils.abbreviate(&quot;abcdefg&quot;, 6)	-&gt; &quot;abc...&quot;
	 * StringUtils.abbreviate(&quot;abcdefg&quot;, 7)	-&gt; &quot;abcdefg&quot;
	 * StringUtils.abbreviate(&quot;abcdefg&quot;, 8)	-&gt; &quot;abcdefg&quot;
	 * StringUtils.abbreviate(&quot;abcdefg&quot;, 4)	-&gt; &quot;a...&quot;
	 * StringUtils.abbreviate(&quot;abcdefg&quot;, 3)	-&gt; IllegalArgumentException
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str 文字列
	 * @param maxWidth 文字数
	 * @return 抽出された文字
	 */
	public static String abbreviate(String str, int maxWidth) {
		return abbreviate(str, 0, maxWidth);
	}

	/**
	 * 先頭から指定した文字数を抽出する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.abbreviate(null, *, *)		-&gt; null
	 * StringUtils.abbreviate(&quot;&quot;, 0, 4)			-&gt; &quot;&quot;
	 * StringUtils.abbreviate(&quot;abcdefghijklmno&quot;, -1, 10)	-&gt; &quot;abcdefg...&quot;
	 * StringUtils.abbreviate(&quot;abcdefghijklmno&quot;, 0, 10)	-&gt;&quot;abcdefg...&quot;
	 * StringUtils.abbreviate(&quot;abcdefghijklmno&quot;, 1, 10)  -&gt; &quot;abcdefg...&quot;
	 * StringUtils.abbreviate(&quot;abcdefghijklmno&quot;, 4, 10)  -&gt; &quot;abcdefg...&quot;
	 * StringUtils.abbreviate(&quot;abcdefghijklmno&quot;, 5, 10)  -&gt; &quot;...fghi...&quot;
	 * StringUtils.abbreviate(&quot;abcdefghijklmno&quot;, 6, 10)  -&gt; &quot;...ghij...&quot;
	 * StringUtils.abbreviate(&quot;abcdefghijklmno&quot;, 8, 10)  -&gt; &quot;...ijklmno&quot;
	 * StringUtils.abbreviate(&quot;abcdefghijklmno&quot;, 10, 10) -&gt; &quot;...ijklmno&quot;
	 * StringUtils.abbreviate(&quot;abcdefghijklmno&quot;, 12, 10) -&gt; &quot;...ijklmno&quot;
	 * StringUtils.abbreviate(&quot;abcdefghij&quot;, 0, 3)        -&gt; IllegalArgumentException
	 * StringUtils.abbreviate(&quot;abcdefghij&quot;, 5, 6)        -&gt; IllegalArgumentException
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str 文字列
	 * @param offset 開始位置
	 * @param maxWidth 文字数
	 * @return 抽出された文字
	 */
	public static String abbreviate(String str, int offset, int maxWidth) {
		if (str == null) {
			return null;
		}
		int workOffset = offset;
		if (maxWidth < 4) {
			throw new IllegalArgumentException("Minimum abbreviation width is 4");
		}
		if (str.length() <= maxWidth) {
			return str;
		}
		if (workOffset > str.length()) {
			workOffset = str.length();
		}
		if ((str.length() - workOffset) < (maxWidth - 3)) {
			workOffset = str.length() - (maxWidth - 3);
		}
		if (workOffset <= 4) {
			return str.substring(0, maxWidth - 3) + "...";
		}
		if (maxWidth < 7) {
			throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
		}
		if ((workOffset + (maxWidth - 3)) < str.length()) {
			return "..." + abbreviate(str.substring(workOffset), maxWidth - 3);
		}
		return "..." + str.substring(str.length() - (maxWidth - 3));
	}

	/**
	 * 全角文字列（数値/英語/カタカナ）へ変換する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.toWide(&quot;abc123&quot;) -&gt; &quot;ａｂｃ１２３&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param s 対象文字列
	 * @return 変換後文字列
	 */
	public static String toWide(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if ('0' <= ch && ch <= '9') {
				buf.append(convertWideNumeric(ch));
			} else if ('a' <= ch && ch <= 'z') {
				buf.append(convertWideLowerAlpha(ch));
			} else if ('A' <= ch && ch <= 'Z') {
				buf.append(convertWideUpperAlpha(ch));
			} else {
				buf.append(ch);
			}
		}
		return toWideKatakana(buf.toString());
	}

	/**
	 * 半角文字列（数値/英語/カタカナ）へ変換する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.toNarrow(&quot;ａｂｃ１２３&quot;) -&gt; &quot;abc123&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param s 対象文字列
	 * @return 変換後文字列
	 */
	public static String toNarrow(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if ('０' <= ch && ch <= '９') {
				// 全角数字を半角数字に変換する
				buf.append(convertNarrowNumeric(ch));
			} else if ('ａ' <= ch && ch <= 'ｚ') {
				// 全角小文字を半角小文字に変換する
				buf.append(convertNarrowLowerAlpha(ch));
			} else if ('Ａ' <= ch && ch <= 'Ｚ') {
				// 全角大文字を半角大文字に変換する
				buf.append(convertNarrowUpperAlpha(ch));
			} else {
				buf.append(ch);
			}
		}
		return toNarrowKatakana(buf.toString());
	}

	/**
	 * 半角数字を全角数字へ変換する
	 * <p>
	 * ※小数点、カンマの変換は行わない<br />
	 * <br />
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.toWideNumeric(&quot;123&quot;) -&gt; &quot;１２３&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param s 対象文字列
	 * @return 変換後文字列
	 */
	public static String toWideNumeric(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if ('0' <= ch && ch <= '9') {
				// 半角数字を全角数字に変換する
				buf.append(convertWideNumeric(ch));
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	/**
	 * 全角数字を半角数字へ変換する
	 * <p>
	 * ※小数点、カンマの変換は行わない<br />
	 * <br />
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.toNarrowNumeric(&quot;１２３&quot;) -&gt; &quot;123&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param s 対象文字列
	 * @return 変換後文字列
	 */
	public static String toNarrowNumeric(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if ('０' <= ch && ch <= '９') {
				// 全角数字を半角数字に変換する
				buf.append(convertNarrowNumeric(ch));
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	/**
	 * 半角英語を全角英語へ変換する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.toWideAlphabet(&quot;abc&quot;) -&gt; &quot;ａｂｃ&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param s 対象文字列
	 * @return 変換後文字列
	 */
	public static String toWideAlphabet(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if ('a' <= ch && ch <= 'z') {
				// 半角小文字を全角小文字に変換する
				buf.append(convertWideLowerAlpha(ch));
			} else if ('A' <= ch && ch <= 'Z') {
				// 半角大文字を全角大文字に変換する
				buf.append(convertWideUpperAlpha(ch));
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	/**
	 * 全角英語を半角英語へ変換する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.toNarrowAlphabet(&quot;ａｂｃ&quot;) -&gt; &quot;abc&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param s 対象文字列
	 * @return 変換後文字列
	 */
	public static String toNarrowAlphabet(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if ('ａ' <= ch && ch <= 'ｚ') {
				// 全角小文字を半角小文字に変換する
				buf.append(convertNarrowLowerAlpha(ch));
			} else if ('Ａ' <= ch && ch <= 'Ｚ') {
				// 全角大文字を半角大文字に変換する
				buf.append(convertNarrowUpperAlpha(ch));
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	/**
	 * 半角カタカナを全角カタカナへ変換する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.toWideKatakana(&quot;ｱｲｳ&quot;) -&gt; &quot;アイウ&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param s 変換対象文字列
	 * @return 変換後文字列
	 */
	public static String toWideKatakana(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char hikaku = s.charAt(i);
			// 最後の文字をチェックし、変換する
			if (i >= s.length() - 1) {
				// 半角カタカナ以外のものを元に戻す
				if (hikaku < 'ｦ' || hikaku > 'ﾟ') {
					buf.append(hikaku);
				} else {
					int index = Chars.HANKAKU_KATAKANA.indexOf(hikaku);
					if (index >= 0) {
						hikaku = Chars.KATAKANA.charAt(index);
						buf.append(hikaku);
					}
				}
				break;
			} else {
				char hikaku2 = s.charAt(i + 1);
				// 半角カタカナ以外のものを元に戻す
				if (hikaku < 'ｦ' || hikaku > 'ﾟ') {
					buf.append(hikaku);
					// 変換済みの文字をチェックする
				} else if (hikaku == 'ﾞ' || hikaku == 'ﾟ') {
					continue;
					// 濁点文字を変換する
				} else if (hikaku2 == 'ﾞ') {
					int index = Chars.HANKAKU_DAKUTEN.indexOf(hikaku) / 2;
					buf.append(Chars.DAKUTEN.charAt(index));
					// 半濁点文字を変換する
				} else if (hikaku2 == 'ﾟ') {
					int index = Chars.HANKAKU_HAN_DAKUTEN.indexOf(hikaku) / 2;
					buf.append(Chars.HAN_DAKUTEN.charAt(index));
					// 濁点、半濁点以外の文字を変換する
				} else {
					int index = Chars.HANKAKU_KATAKANA.indexOf(hikaku);
					if (index >= 0) {
						hikaku = Chars.KATAKANA.charAt(index);
					}
					buf.append(hikaku);
				}
			}
		}
		return buf.toString();
	}

	/**
	 * 全角カタカナを半角カタカナへ変換する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.toNarrowKatakana(&quot;アイウ&quot;) -&gt; &quot;ｱｲｳ&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param s 変換対象文字列
	 * @return 変換後文字列
	 */
	public static String toNarrowKatakana(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char hikaku = s.charAt(i);
			StringBuilder hikakuStr = new StringBuilder();
			int index = 0;
			// 半角カタカナに変換する
			if (hikaku >= 'ァ' && hikaku <= 'ー') {
				index = Chars.KATAKANA.indexOf(hikaku);
				if (index >= 0) {
					hikaku = Chars.HANKAKU_KATAKANA.charAt(index);
				} else {
					// 濁点文字を変換する
					index = Chars.DAKUTEN.indexOf(hikaku);
					if (index >= 0) {
						hikakuStr.append(Chars.HANKAKU_DAKUTEN.charAt(index * 2)).append(Chars.HANKAKU_DAKUTEN.charAt(index * 2 + 1));
					} else {
						// 半濁点文字を変換する
						index = Chars.HAN_DAKUTEN.indexOf(hikaku);
						if (index >= 0) {
							hikakuStr.append(Chars.HANKAKU_HAN_DAKUTEN.charAt(index * 2)).append(Chars.HANKAKU_HAN_DAKUTEN.charAt(index * 2 + 1));
						}
					}
				}
			}
			if (hikakuStr.length() > 0) {
				buf.append(hikakuStr.toString());
			} else {
				buf.append(hikaku);
			}
		}
		return buf.toString();
	}

	/**
	 * <code>delimiter</code>で区切られた文字列をCamelCaseに変換する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.toCamelCase(&quot;HOGE-PIYO&quot;, &quot;-&quot;)	-&gt; hogePiyo
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param s 変換対象の文字列
	 * @param delimiter 区切り文字
	 * @return CamelCaseに変換した文字列
	 */
	public static String toCamelCase(String s, String delimiter) {
		StringBuilder builder = new StringBuilder();
		String[] words = s.split(delimiter);
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

	/**
	 * _(アンダースコア)で区切られた文字列をCamelCaseに変換する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.toCamelCase(&quot;HOGE_PIYO&quot;)	-&gt; hogePiyo
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param s 変換対象の文字列
	 * @return CamelCaseに変換した文字列
	 */
	public static String toCamelCase(String s) {
		return toCamelCase(s, "_");
	}

	/**
	 * 文字エンコーディング変換する（デフォルト＝プロパティー指定）
	 * <p>
	 * ※このメソッドでは、Windows_31JからUTF-8への変換を行う。
	 * </p>
	 * 
	 * @param s 対象文字列
	 * @return 変換後文字列
	 */
	public static String encode(String s) {
		return encodeParameter(s, Encodings.UTF_8, Encodings.WINDOWS_31J);
	}

	/**
	 * 文字エンコーディング変換する（エンコーディング指定）
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.encode(&quot;hoge&quot;, StringUtils.Formats.SHIFT_JIS)
	 * </pre>
	 * 
	 * @param s 対象文字列
	 * @param encoding 変換後のエンコード
	 * @return 変換後文字列
	 */
	public static String encode(String s, String encoding) {
		return encodeParameter(s, Encodings.UTF_8, encoding);
	}

	/**
	 * URL文字列からハイパーリンクを生成する
	 * <p>
	 * ※文字列の中から、HTMLのURL部（http://, https://）をハイパーリンク＋window.open() で処理するスクリプト文字列に変換<BR>
	 * </p>
	 * 
	 * @param s 対象文字列
	 * @return 変換後文字列
	 */
	public static String createHTMLForURL(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		Matcher matcher = patternHtmlForUrl.matcher(s);
		return matcher.replaceAll(Formats.HTML_FOR_URL);
	}

	/**
	 * Java名称からDB名称へ変換する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.convertFieldToColumn(&quot;rowVer&quot;) -&gt; &quot;ROW_VER&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param name Javaフィールド名
	 * @return DBカラム形式での名称
	 */
	public static String convertFieldToColumn(String name) {
		if (name == null || name.length() == 0) {
			return "";
		}

		StringBuilder sb = new StringBuilder(30);
		char c;
		for (int i = 0; i < name.length(); i++) {
			c = name.charAt(i);
			if (c != '_') {
				if (Character.isUpperCase(c)) {
					if (i > 0) {
						sb.append('_');
						sb.append(c);
					} else {
						sb.append(c);
					}
				} else {
					sb.append(Character.toUpperCase(c));
				}
			}
		}
		return sb.toString();
	}

	/**
	 * DB名称からJava名称へ変換する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * StringUtils.convertColumnToField(&quot;ROW_VER&quot;) -&gt; &quot;rowVer&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param name DBカラム名称
	 * @return Javaフィールド形式での名称
	 */
	public static String convertColumnToField(String name) {
		if (name == null || name.length() == 0) {
			return "";
		}
		/* トーカナイザのインスタンス作成 */
		/* 指定するのはカラム名とデリミタ */
		StringTokenizer st = new StringTokenizer(name, COLUMN_DELIM, true);

		/* バインド変数名 */
		StringBuilder bindPropertyName = new StringBuilder();

		/* トークンの数だけ処理繰り返し */
		while (st.hasMoreElements()) {

			/* トークン取得 */
			String token = st.nextToken();

			// トークンが"_"の場合は次のトークンを取得し数字かどうかチェックします
			// まだトークンが残っていれば次のトークンを取得
			if (COLUMN_DELIM.equals(token) && st.hasMoreElements()) {
				// 次のトークンを取得
				String nexttoken = st.nextToken();
				char c = nexttoken.charAt(0);
				// 先頭文字が数字かどうか評価,数字ならば"_"を文字として残す
				if (Character.getType(c) == Character.DECIMAL_DIGIT_NUMBER) {
					bindPropertyName.append(token);
				}
				token = nexttoken;
			}

			/* トークンの一文字目は大文字 */
			bindPropertyName.append(token.substring(0, 1).toUpperCase());

			/* トークンの二文字目以降は小文字 */
			if (token.length() > 1) {
				bindPropertyName.append(token.substring(1, token.length()).toLowerCase());
			}

		}
		/* バインド変数名を確定 */
		return bindPropertyName.toString();
	}

	/**
	 * 機種依存文字、JIS 第1, 第2水準チェックを行う
	 * 
	 * @param str 対象文字列
	 * @return <code>true</code>:機種依存文字、JIS 第1, 第2水準以外の場合,<code>false</code>:機種依存文字、JIS 第1, 第2水準いずれかの場合
	 */
	public static boolean checkCharCode(String str) {
		return checkCharCode(str, new CharCodeRange() {
			public boolean allowChar(int asciiCode) {
				// 機種依存文字チェック
				// 0x8540 - 0x889E
				if (0x8540 <= asciiCode && asciiCode <= 0x889E) {
					return false;
				}
				// JIS 第1, 第2水準以外チェック
				if (0xEB50 <= asciiCode) {
					return false;
				}
				return true;
			}
		});
	}

	/**
	 * 機種依存文字、JIS 第1, 第2水準チェックを行う。
	 * ただし、以下の範囲は許容する。
	 * 
	 * <pre>
	 * ・IBM拡張文字
	 * 	FA40～FC4F
	 * ・ＮＥＣ選定ＩＢＭ拡張文字
	 * 	ED40～EEFF
	 * ・ＮＥＣ特殊文字
	 * 	8740～879F
	 * </pre>
	 * 
	 * @param str 対象文字列
	 * @return <code>true</code>:機種依存文字、JIS 第1, 第2水準以外の場合,<code>false</code> :機種依存文字、JIS 第1, 第2水準いずれかの場合
	 */
	public static boolean checkExtendCharCode(String str) {
		return checkCharCode(str, new CharCodeRange() {
			public boolean allowChar(int asciiCode) {
				// 機種依存文字チェック
				if (0x8540 <= asciiCode && asciiCode <= 0x8739) {
					return false;
				}
				if (0x879F <= asciiCode && asciiCode <= 0x889E) {
					return false;
				}
				// JIS 第1, 第2水準以外チェック
				// -EB50
				if (0xEB50 <= asciiCode) {
					// IBM拡張文字、ＮＥＣ選定ＩＢＭ拡張文字は許容する
					if ((0xFA40 <= asciiCode && asciiCode <= 0xFC4F) || (0xED40 <= asciiCode && asciiCode <= 0xEEFF)) {
						return true;
					}
					return false;
				}
				return true;
			}
		});
	}

	/**
	 * 機種依存文字、JIS 第1, 第2水準チェックを行う
	 * 
	 * @param str 対象文字列
	 * @param range 文字コードチェックロジック
	 * @return <code>true</code>:機種依存文字、JIS 第1, 第2水準以外の場合,<code>false</code>:機種依存文字、JIS 第1, 第2水準いずれかの場合
	 */
	public static boolean checkCharCode(String str, CharCodeRange range) {
		// OS の文字コードに変換
		CharsetEncoder encoder = Charset.forName("MS932").newEncoder();
		int asciiCode;

		try {
			for (int i = 0; i < str.length(); i++) {
				asciiCode = getAsciiCode(str.charAt(i), encoder);
				if (!range.allowChar(asciiCode)) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	// ////////////////////////////////
	// private methods
	// ////////////////////////////////
	/**
	 * 文字列をエンコードし、返却する
	 * <p>
	 * ※各々のエンコード指定を変更できる。例外処理は起動元で行なう。
	 * </p>
	 * 
	 * @param str 文字列
	 * @param byteEncoding バイトエンコード指定
	 * @param strEncoding 文字列エンコード指定
	 * @return エンコード後の文字列
	 */
	private static String encodeParameter(String str, String byteEncoding, String strEncoding) {

		String ret = str;
		if (ret == null) {
			return "";
		}
		try {
			ret = new String(ret.getBytes(byteEncoding), strEncoding);
		} catch (java.io.UnsupportedEncodingException ex) {
			throw new RuntimeException("Encode error. " + ex.getMessage());
		}
		return ret;
	}

	/**
	 * 半角数字を全角数字に変換する
	 * 
	 * @param val 変換前文字
	 * @return 変換後文字
	 */
	private static char convertWideNumeric(char val) {
		return convert(val, '0', '０');
	}

	/**
	 * 全角数字を半角数字に変換する
	 * 
	 * @param val 変換前文字
	 * @return 変換後文字
	 */
	private static char convertNarrowNumeric(char val) {
		return convert(val, '０', '0');
	}

	/**
	 * 半角大文字を全角大文字に変換する
	 * 
	 * @param val 変換前文字
	 * @return 変換後文字
	 */
	private static char convertWideUpperAlpha(char val) {
		return convert(val, 'A', 'Ａ');
	}

	/**
	 * 全角大文字を半角大文字に変換する
	 * 
	 * @param val 変換前文字
	 * @return 変換後文字
	 */
	private static char convertNarrowUpperAlpha(char val) {
		return convert(val, 'Ａ', 'A');
	}

	/**
	 * 半角小文字を全角小文字に変換する
	 * 
	 * @param val 変換前文字
	 * @return 変換後文字
	 */
	private static char convertWideLowerAlpha(char val) {
		return convert(val, 'a', 'ａ');
	}

	/**
	 * 全角小文字を半角小文字に変換する
	 * 
	 * @param val 変換前文字
	 * @return 変換後文字
	 */
	private static char convertNarrowLowerAlpha(char val) {
		return convert(val, 'ａ', 'a');
	}

	/**
	 * 全角 <-> 半角の変換を行う
	 * 
	 * @param base 対象文字
	 * @param range 対象文字のレンジ
	 * @param next コンバート後のレンジ
	 * @return コンバート後の文字列
	 */
	private static char convert(char base, char range, char next) {
		return (char) (base - range + next);
	}

	/**
	 * 指定した文字で左詰めをする
	 * 
	 * @param str 文字列
	 * @param size サイズ
	 * @param padChar パディングの文字
	 * @return パディング後文字列
	 */
	private static String leftPad(String str, int size, char padChar) {
		int pads = size - str.length();
		return padding(pads, padChar).concat(str);
	}

	/**
	 * 指定した文字で右詰めをする
	 * 
	 * @param str 文字列
	 * @param size サイズ
	 * @param padChar パディングの文字
	 * @return パディング後文字列
	 */
	private static String rightPad(String str, int size, char padChar) {
		int pads = size - str.length();
		return str.concat(padding(pads, padChar));
	}

	/**
	 * 指定回数パディングする
	 * 
	 * @param repeat パディング回数
	 * @param padChar パディング文字列
	 * @return パディング後の文字列
	 */
	private static String padding(int repeat, char padChar) {
		final char[] buf = new char[repeat];
		for (int i = 0; i < buf.length; i++) {
			buf[i] = padChar;
		}
		return new String(buf);
	}

	/**
	 * 文字列分割を行います
	 * 
	 * @param str 処理対象文字列
	 * @param separatorChars セパレータ
	 * @param max 最大分割数
	 * @param preserveAllTokens 全てのトークンが予約済みかどうか
	 * @return 分割後の文字列配列
	 */
	private static String[] splitWorker(String str, String separatorChars, int max, boolean preserveAllTokens) {
		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return new String[0];
		}
		List<String> list = new ArrayList<String>();
		int sizePlus1 = 1;
		int i = 0;
		int start = 0;
		boolean match = false;
		boolean lastMatch = false;
		if (separatorChars == null) {
			// Null separator means use whitespace
			while (i < len) {
				if (Character.isWhitespace(str.charAt(i))) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				} else {
					lastMatch = false;
				}
				match = true;
				i++;
			}
		} else if (separatorChars.length() == 1) {
			// Optimise 1 character case
			char sep = separatorChars.charAt(0);
			while (i < len) {
				if (str.charAt(i) == sep) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				} else {
					lastMatch = false;
				}
				match = true;
				i++;
			}
		} else {
			// standard case
			while (i < len) {
				if (separatorChars.indexOf(str.charAt(i)) >= 0) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				} else {
					lastMatch = false;
				}
				match = true;
				i++;
			}
		}
		if (match || (preserveAllTokens && lastMatch)) {
			list.add(str.substring(start, i));
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	/**
	 * 半角英数チェックを行う（<code>Char</code>型）
	 * 
	 * @param ch チェック対象キャラクタ
	 * @return 半角英数であれば<code>true</code>
	 */
	private static boolean isNarrowAlphabetNumber(char ch) {
		return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') || ch == '.' || ch == ',' || ch == '_' || ch == '-' || ch == ' ';
	}

	/**
	 * アスキーコードを取得する
	 * 
	 * @param ch アスキーコード変換対象
	 * @param encoder エンコーダー
	 * @throws Exception 例外
	 * @return アスキーコード
	 */
	private static int getAsciiCode(char ch, CharsetEncoder encoder) throws Exception {
		char[] charArray = {ch};
		CharBuffer cbuf = CharBuffer.wrap(charArray);
		ByteBuffer bbuf = encoder.encode(cbuf);

		int asciiCode = 0;
		for (int j = 0; j < bbuf.limit(); j++) {
			int shift = (bbuf.limit() - (j + 1)) * 8;
			asciiCode = asciiCode | ((bbuf.get() & 0xFF) << shift);
		}
		return asciiCode;
	}

	/**
	 * 文字コードチェックロジック.
	 * 
	 * @author hoshi-k
	 * @version 2011/07/25 新規作成
	 */
	private static class CharCodeRange {
		/**
		 * その文字コードを許すかどうかを判定する
		 * 
		 * @param asciiCode asiiCode
		 * @return 許す場合は<code>true</code>、許さないなら<code>false</code>
		 */
		public boolean allowChar(int asciiCode) {
			return true;
		}
	}
}
