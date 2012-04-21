/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.logics;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;

import kosmos.framework.core.logics.utility.DateUtils;
import kosmos.framework.core.logics.utility.StringUtils;

/**
 * バリデータ.
 *
 * @author yoshida-n
 * @version 2010/09/24 新規作成
 */
public class Validator {

	/**
	 * BigDecimal型の数値（小数）チェックを行う。
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * vali.isDecimal(100) -&gt; false
	 * vali.isDecimal(0.15) -&gt; true
	 * vali.isDecimal(0.0) -&gt; true
	 * vali.isDecimal(0) -&gt; false
	 * </pre>
	 *
	 * </p>
	 *
	 * @param num チェック対象
	 * @return <code>true</code>:小数の場合、<code>false</code>整数の場合
	 */
	public boolean isDecimal(BigDecimal num) {
		return 0 < num.scale();
	}

	/**
	 * BigDecimal型の大小比較を行う
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * vali.lessMin(1, 2) -&gt; true
	 * vali.lessMin(2, 1) -&gt; false
	 * vali.lessMin(1, 1) -&gt; false
	 * </pre>
	 *
	 * </p>
	 *
	 * @param num 比較数値
	 * @param min 比較数値(最小)
	 * @return <code>true</code>:numよりminが大きい場合、<code>false</code>numよりminが小さい場合
	 */
	public boolean lessMin(BigDecimal num, BigDecimal min) {
		if (num.compareTo(min) < 0) {
			return true;
		}
		return false;
	}

	/**
	 * BigDecimal型の大小比較を行う
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * vali.greaterMax(2, 1) -&gt; true
	 * vali.greaterMax(1, 2) -&gt; false
	 * vali.greaterMax(1, 1) -&gt; false
	 * </pre>
	 *
	 * </p>
	 *
	 * @param num 比較数値
	 * @param max 比較数値(最大)
	 * @return <code>true</code>:numよりmaxが小さい場合、<code>false</code>numよりmaxが大きい場合
	 */
	public boolean greaterMax(BigDecimal num, BigDecimal max) {
		if (num.compareTo(max) > 0) {
			return true;
		}
		return false;
	}

	/**
	 * BigDecimal型の範囲チェックを行う
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * vali.range(2, 1, 3) -gt; false
	 * vali.range(2, 3, 3) -gt; true
	 * vali.range(2, 1, 1) -gt; true
	 * </pre>
	 *
	 * @param num 比較数値
	 * @param min 比較数値(最小)
	 * @param max 比較数値(最大)
	 * @return <code>true</code>:numがminとmaxで設定した値の範囲外の場合、<code>false</code> :numがminとmaxで設定した値の範囲内の場合
	 */
	public boolean range(BigDecimal num, BigDecimal min, BigDecimal max) {
		return lessMin(num, min) || greaterMax(num, max);
	}

	/**
	 * 全角英数字チェックを行う
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * vali.isWideAlphaNum(&quot;&quot;)	-&gt; false
	 * vali.isWideAlphaNum(null)	-&gt; false
	 * vali.isWideAlphaNum(&quot;ああああ&quot;)	-&gt; false
	 * vali.isWideAlphaNum(&quot;aaaa&quot;)	-&gt; false
	 * vali.isWideAlphaNum(&quot;ＡＡＡＡ&quot;)	-&gt; true
	 * </pre>
	 *
	 * </P>
	 *
	 * @param str 対象文字列
	 * @return <code>true</code>:全角英数字の場合,<code>false</code>:全角英数字以外の場合
	 */
	public boolean isWideAlphaNum(String str) {
		return StringUtils.isWideAlphaNum(str);
	}

	/**
	 * 半角英数チェックを行う
	 * <p>
	 * ※<code>null</code> 又は空文字の場合は<code>true</code>を返す。<br />
	 * Example:
	 *
	 * <pre>
	 * vali.isNarrowAlphabetNumber(null)	-&gt; true
	 * vali.isNarrowAlphabetNumber(&quot;123 abc&quot;)	-&gt; true
	 * vali.isNarrowAlphabetNumber(&quot;あいう&quot;)	-&gt; false
	 * vali.isNarrowAlphabetNumber(&quot;ｱｲｳ&quot;)	-&gt; false
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str チェック対象文字列
	 * @return 半角英数と記号「.,_-」で構成されている文字列であれば<code>true</code>
	 */
	public boolean isNarrowAlphabetNumber(String str) {
		return StringUtils.isNarrowAlphabetNumber(str);
	}

	/**
	 * 英数字チェックを行う
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * vali.isAlphaNum(&quot;&quot;) -&gt; false
	 * vali.isAlphaNum(null) -&gt; false
	 * vali.isAlphaNum(&quot;ああああ&quot;) -&gt; false
	 * vali.isAlphaNum(&quot;aaaa&quot;) -&gt; true
	 * vali.isAlphaNum(&quot;ＡＡＡＡ&quot;) -&gt; true
	 * vali.isAlphaNum(&quot;111111&quot;) -&gt; true
	 * vali.isAlphaNum(&quot;２２２&quot;) -&gt; true
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str 対象文字列
	 * @return <code>true</code>:英数字の場合,<code>false</code>:英数字以外の場合
	 */
	public boolean isAlphaNum(String str) {
		return StringUtils.isAlphaNum(str);
	}

	/**
	 * 半角英数カナチェックを行う
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * vali.isNarrowAlphaNumericKatakana(&quot;&quot;) -&gt; false
	 * vali.isNarrowAlphaNumericKatakana(null) -&gt; false
	 * vali.isNarrowAlphaNumericKatakana(&quot;あいう&quot;) -&gt; false
	 * vali.isNarrowAlphaNumericKatakana(&quot;aaaa&quot;) -&gt; true
	 * vali.isNarrowAlphaNumericKatakana(&quot;111111&quot;) -&gt; true
	 * vali.isNarrowAlphaNumericKatakana(&quot;アアア&quot;) -&gt; false
	 * vali.isNarrowAlphaNumericKatakana(&quot;ｱｲｳ&quot;) -&gt;true
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str 対象文字列
	 * @return <code>true</code>:半角英数カナの場合,<code>false</code>:半角英数カナ以外の場合
	 */
	public boolean isNarrowAlphaNumericKatakana(String str) {
		return StringUtils.isNarrowAlphaNumericKatakana(str);
	}

	/**
	 * 全角数字であることをチェックする
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * vali.isWideNumeric(&quot;&quot;) -&gt; false
	 * vali.isWideNumeric(null) -&gt; false
	 * vali.isWideNumeric(&quot;あいう&quot;) -&gt; false
	 * vali.isWideNumeric(&quot;aaaa&quot;) -&gt; false
	 * vali.isWideNumeric(&quot;111111&quot;) -&gt; false
	 * vali.isWideNumeric(&quot;１１１１１&quot;) -&gt; true
	 * vali.isWideNumeric(&quot;アアア&quot;) -&gt; false
	 * vali.isWideNumeric(&quot;ｱｲｳ&quot;) -&gt; false
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str 対象文字列
	 * @return <code>true</code>:全角数字の場合,<code>false</code>:全角数字以外の場合
	 */
	public boolean isWideNumeric(String str) {
		return StringUtils.isWideNumeric(str);
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
	 * vali.isNumber(null)	-&gt; true
	 * vali.isNumber(&quot;123&quot;)	-&gt; true
	 * vali.isNumber(&quot;0x123&quot;)	-&gt; true
	 * vali.isNumber(&quot;a123&quot;)	-&gt; false
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str チェック対象文字列
	 * @return 数値の場合は<code>true</code>を返す
	 */
	public boolean isNumber(String str) {
		return StringUtils.isNumber(str);
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
	 * vali.isDigit(null)	-&gt; true
	 * vali.isDigit(&quot;123&quot;)	-&gt; true
	 * vali.isDigit(&quot;-1234&quot;)	-&gt; false
	 * vali.isDigit(&quot;12.88&quot;)	-&gt; false
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str チェック対象文字列
	 * @return 数値の場合は<code>true</code>を返す
	 */
	public boolean isDigit(String str) {
		return StringUtils.isDigit(str);
	}

	/**
	 * 全角チェックを行う
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * vali.isWide(null) -&gt; false
	 * vali.isWide(&quot;&quot;) -&gt; false
	 * vali.isWide(&quot;あいうえお&quot;) -&gt; true
	 * vali.isWide(&quot;あい１２&quot;) -&gt; true
	 * vali.isWide(&quot;１２３４&quot;) -&gt; true
	 * vali.isWide(&quot;あいuえお&quot;) -&gt; false
	 * vali.isWide(&quot;あいう①え&quot;) -&gt; false
	 * vali.isWide(&quot;ｱｲｳｴ&quot;) -&gt; false
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str チェック対象文字列
	 * @return <code>true</code>:全角の場合,<code>false</code>:全角以外の場合
	 */
	public boolean isJISWide(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}

		if (!checkJISCharCode(str)) {
			return false;
		}
		String src = str.replaceAll("\r|\n", "");

		for (int i = 0; i < src.length(); i++) {
			byte[] bytearray = null;
			try {
				bytearray = src.substring(i, i + 1).getBytes("MS932");
			} catch (UnsupportedEncodingException e) {}
			if (bytearray == null || bytearray.length <= 1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 文字列が全角カタカナであることをチェックする
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * vali.isWideKatakana(&quot;&quot;) -&gt; false
	 * vali.isWideKatakana(null) -&gt; false
	 * vali.isWideKatakana(&quot;あいう&quot;) -&gt; false
	 * vali.isWideKatakana(&quot;aaaa&quot;) -&gt; false
	 * vali.isWideKatakana(&quot;111111&quot;) -&gt; false
	 * vali.isWideKatakana(&quot;１１１１１&quot;) -&gt; false
	 * vali.isWideKatakana(&quot;アアア&quot;) -&gt; true
	 * vali.isWideKatakana(&quot;ｱｲｳ&quot;) -&gt; false
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str 対象文字列
	 * @return <code>true</code>:全角カタカナ,<code>false</code>:全角カタカナ以外が混ざっている
	 */
	public boolean isWideKatakana(String str) {
		return StringUtils.isWideKatakana(str);
	}

	/**
	 * 文字列が半角カタカナであることをチェックする
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * vali.isNarrowKatakana(&quot;&quot;) -&gt; false
	 * vali.isNarrowKatakana(null) -&gt; false
	 * vali.isNarrowKatakana(&quot;あいう&quot;) -&gt; false
	 * vali.isNarrowKatakana(&quot;aaaa&quot;) -&gt; false
	 * vali.isNarrowKatakana(&quot;111111&quot;) -&gt; false
	 * vali.isNarrowKatakana(&quot;１１１１１&quot;) -&gt; false
	 * vali.isNarrowKatakana(&quot;アアア&quot;) -&gt; false
	 * vali.isNarrowKatakana(&quot;ｱｲｳ&quot;) -&gt; true
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str 対象文字列
	 * @return <code>true</code>:半角カタカナ,<code>false</code>:半角カタカナ以外が混ざっている
	 */
	public boolean isNarrowKatakana(String str) {
		return StringUtils.isNarrowKatakana(str);
	}

	/**
	 * 半角文字チェックメソッド<br>
	 * 対象文字列が半角文字か評価し、結果を返します。<br>
	 * 文字列のバイト長と文字列長を比較し判定します。<br>
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * vali.isNarrowCharString(&quot;&quot;) -gt; false
	 * vali.isNarrowCharString(null) -gt; false
	 * vali.isNarrowCharString("Aq*2ｶﾋﾞｱｨ.") -gt; true
	 * vali.isNarrowCharString(&quot;aaaa&quot;) -gt; true
	 * vali.isNarrowCharString(&quot;111111&quot;) -gt; true
	 * vali.isNarrowCharString(&quot;１１１１１&quot;) -gt; false
	 * vali.isNarrowCharString(&quot;アアア&quot;) -gt; false
	 * vali.isNarrowCharString(&quot;ｱｲｳ&quot;) -gt; true
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str 対象文字列
	 * @return <code>true</code>:半角文字列の場合,<code>fase</code>:半角文字列以外の場合
	 */
	public boolean isNarrowCharString(String str) {
		return StringUtils.isNarrowCharString(str);
	}

	/**
	 * 機種依存文字、JIS 第1, 第2水準チェックを行う
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * vali.checkCharCode(&quot;&quot;) -&gt; true
	 * vali.checkCharCode(null) -&gt; false
	 * vali.checkCharCode(&quot;あいう&quot;) -&gt; true
	 * vali.checkCharCode(&quot;ｶｷｸ&quot;) -&gt; true
	 * vali.checkCharCode(&quot;①&quot;) -&gt; false
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str 対象文字列
	 * @return <code>true</code>:機種依存文字、JIS 第1, 第2水準以外の場合,<code>false</code>:機種依存文字、JIS 第1, 第2水準いずれかの場合
	 */
	public boolean checkJISCharCode(String str) {
		return StringUtils.checkJISCharCode(str);
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
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * vali.checkExtendCharCode(&quot;&quot;) -&gt; true
	 * vali.checkExtendCharCodee(null) -&gt; false
	 * vali.checkExtendCharCode(&quot;あいう&quot;) -&gt; true
	 * vali.checkExtendCharCode(&quot;ｶｷｸ&quot;) -&gt; true
	 * vali.checkExtendCharCode(&quot;①&quot;) -&gt; true
	 * vali.checkExtendCharCode(&quot;髙&quot;) -&gt; true
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str 対象文字列
	 * @return <code>true</code>:機種依存文字、JIS 第1, 第2水準以外の場合,<code>false</code>:機種依存文字、JIS 第1, 第2水準いずれかの場合
	 */
	public boolean checkJISExtendCharCode(String str) {
		return StringUtils.checkJISExtendCharCode(str);
	}

	/**
	 * 最大バイト数チェックを行う
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * vali.checkMaxbyte(&quot;&quot;, &quot;&quot;) -gt; true
	 * vali.checkMaxbyte(null, null) -gt; true
	 * vali.checkMaxbyte(&quot;ｶｷｸ&quot;, &quot;3&quot;) -gt; true
	 * vali.checkMaxbyte(&quot;あいう&quot;, &quot;6&quot;) -gt; true
	 * vali.checkMaxbyte(&quot;あいう&quot;, &quot;5&quot;) -gt; false
	 * </pre>
	 *
	 * </p>
	 *
	 * @param str 対象文字列
	 * @param maxbyte 最大バイト数
	 * @return <code>true</code>:対象文字列が最大バイト数を超えていない場合 <code>false</code>:対象文字列が最大バイト数を超えている場合
	 */
	public boolean checkMaxbyte(String str, String maxbyte,Charset charset) {
		if (StringUtils.isEmpty(str) || StringUtils.isEmpty(maxbyte)) {
			return true;
		}

		int length = Integer.parseInt(maxbyte);
		if (length >= 0) {
			int strBytes;			
			strBytes = str.getBytes(charset).length;			
			if (strBytes > length) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 引数で渡された全ての文字列のバイト数を返却する
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * vali.getByteLength(null) -gt; 0
	 * vali.getByteLength(&quot;&quot;) -gt; 0
	 * vali.getByteLength(&quot;1234&quot;) -gt; 4
	 * vali.getByteLength(&quot;あいう12&quot;) -gt; 8
	 * vali.getByteLength(&quot;12あ&quot;, &quot;あういうえ&quot;) -gt; 14
	 * </pre>
	 *
	 * </p>
	 *
	 * @param values <code>String</code>配列（可変長引数）
	 * @return 引数に設定された値のバイト数
	 */
	public int getByteLength(String... values) {
		StringBuilder sb = new StringBuilder();
		for (String str : values) {
			if (StringUtils.isNotEmpty(str)) {
				sb.append(str);
			}
		}

		if (StringUtils.isEmpty(sb.toString())) {
			return 0;
		}

		try {
			return sb.toString().getBytes("MS932").length;
		} catch (UnsupportedEncodingException e) {
			return sb.toString().getBytes().length;
		}
	}

	/**
	 * 日付が指定したフォーマットかどうか、実際に存在する日付であるかどうかを判定する
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * //「2008年1月」の妥当性をチェックする場合
	 * vali.isDate(&quot;200805&quot;, DateUtils.Formats.YEAR_MONTH_FORMAT)	-&gt; true
	 * //「2008年5月1日17時」の妥当性をチェックする場合
	 * vali.isDate(&quot;2001050117&quot;, DateUtils.Formats.DEFAULT_FORMAT) -&gt; false(フォーマットが異なるため)
	 * //「2009年2月29日」の妥当性をチェックする場合
	 * vali.isDate(&quot;20090229&quot;, DateUtils.Formats.DEFAULT_FORMAT)	-&gt; false(日付が妥当でない)
	 * </li>
	 * </pre>
	 *
	 * </p>
	 *
	 * @param date 日付
	 * @param pattern 日付のフォーマット
	 * @return 妥当な日付である場合<code>true</code>、それ以外は<code>false</code>
	 */
	public boolean isDate(String date, String pattern) {
		return DateUtils.isDate(date, pattern);
	}

	/**
	 * 日付(yyyyMMdd形式)が実際に存在する日付であるかどうかを判定する
	 * <p>
	 * ※日付フォーマットはyyyyMMddとなる
	 * </p>
	 *
	 * @param date 日付(yyyyMMdd)
	 * @return 妥当な日付である場合<code>true</code>、それ以外は<code>false</code>
	 */
	public boolean isDate(String date) {
		return DateUtils.isDate(date);
	}

	/**
	 * 日付(HHmmss形式)が実際に存在する日付であるかどうかを判定する
	 * <p>
	 * ※日付フォーマットはHHmmssとなる
	 * </p>
	 *
	 * @param date 日付(HHmmss)
	 * @return 妥当な日付である場合<code>true</code>、それ以外は<code>false</code>
	 */
	public boolean isTime(String date) {
		return isDate(date, DateUtils.Formats.TIME_FORMAT);
	}

	/**
	 * 何れかの桁数と一致するか判定する
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * vali.isFixLengths(null, 0, 4, 5) -gt; false
	 * vali.isFixLengths(&quot;あいう&quot;, 0, 4, 5) -gt; false
	 * vali.isFixLengths(&quot;あいう&quot;, 0, 3, 4) -gt; true
	 * </pre>
	 *
	 * </p>
	 *
	 * @param value 対象文字列
	 * @param lengths 桁数
	 * @return 何れかの桁数と一致する場合<code>true</code>、それ以外は<code>false</code>
	 */
	public boolean isFixLengths(String value, int... lengths) {
		if (value == null) {
			return false;
		}
		for (int length : lengths) {
			if (value.length() == length) {
				return true;
			}
		}
		return false;
	}
}
