/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.logics;

import static kosmos.framework.utility.DateUtils.format;
import static kosmos.framework.utility.DateUtils.parse;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import kosmos.framework.utility.DateUtils;
import kosmos.framework.utility.FileUtils;
import kosmos.framework.utility.StringUtils;
import kosmos.framework.utility.Utils;

/**
 * コンバータ.
 * 
 * @author yoshida-n
 * @version 2010/10/30 新規作成
 */
public class Converter extends Calculator {
	/** 改行コード */
	private static String CRLF = "\r\n";
	/** カンマ */
	private static String COMMA = ",";

	/**
	 * オブジェクトの文字列表現を取得する
	 * <p>
	 * ※<code>obj</code>が<code>null</code>の場合は空文字(&quot;&quot)を返す<br />
	 * <br />
	 * Example:
	 * 
	 * <pre>
	 * conv.toString(null)		-&gt; &quot;&quot;
	 * conv.toString(&quot;&quot;)			-&gt; &quot;&quot;
	 * conv.toString(&quot;bat&quot;)		-&gt; &quot;bat&quot;
	 * conv.toString(Boolean.TRUE)	-&gt; &quot;true&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param val 文字列
	 * @return 文字列表現
	 */
	public String toString(Object val) {
		return toString(val, "");
	}

	/**
	 * オブジェクトの文字列表現を取得する
	 * <p>
	 * ※<code>obj</code>が<code>null</code>の場合は<code>nullString</code>を返す<br />
	 * <br />
	 * Example:
	 * 
	 * <pre>
	 * conv.toString(null, null)           = null
	 * conv.toString(null, "00")           = "00"
	 * conv.toString("", "null")           = ""
	 * conv.toString("bat", "null")        = "bat"
	 * conv.toString(Boolean.TRUE, "null") = "true"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param val 文字列
	 * @param nullString nullのときの文字列
	 * @return 文字列表現
	 */
	public String toString(Object val, String nullString) {
		return val == null ? nullString : val.toString();
	}

	/**
	 * <code>val</code>がnullの場合は、<code>nullVal</code>を返す <br />
	 * Example:
	 * 
	 * <pre>
	 * conv.nvl(null, null)           = null
	 * conv.nvl(null, BigDecimal.ONE) = BigDecimal.ONE
	 * conv.nvl("", "null")           = ""
	 * conv.nvl("bat", "null")        = "bat"
	 * conv.nvl(Boolean.TRUE, false)  =  true
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param <T> genericType
	 * @param val 値
	 * @param nullVal <code>val</code>がnullのときの値
	 * @return <code>val</code>がnullの場合は、<code>nullVal</code>
	 */
	public <T> T nvl(T val, T nullVal) {
		return val != null ? val : nullVal;
	}

	/**
	 * yyyyMMdd日付をyyyyMm形式へ変換する
	 * <p>
	 * Example.
	 * 
	 * <pre>
	 * conv.toDateYm("20101031")    -> "201010"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param yyyymmdd 日付
	 * @return yyyyMM形式
	 */
	public String toDateYm(String yyyymmdd) {
		return format(parse(yyyymmdd), Utils.DateScope.Formats.YEAR_MONTH_FORMAT);
	}

	/**
	 * yyyyMM日付をyyMm形式へ変換する
	 * <p>
	 * Example.
	 * 
	 * <pre>
	 * conv.toDateShortYm("202110")    -> "2110"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param yyyymm 年月
	 * @return yyMM形式
	 */
	public String toDateShortYm(String yyyymm) {
		return format(parse(yyyymm),  Utils.DateScope.Formats.SHORT_YEAR_FORMAT +  Utils.DateScope.Formats.MONTH_FORMAT);
	}

	/**
	 * yyyy日付をyy形式へ変換する
	 * <p>
	 * Example.
	 * 
	 * <pre>
	 * conv.toDateShortYear("2011")    -> "11"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param yyyy 年
	 * @return yy形式
	 */
	public String toDateShortYear(String yyyy) {
		return format(DateUtils.stringToDate(yyyy,  Utils.DateScope.Formats.YEAR_FORMAT),  Utils.DateScope.Formats.SHORT_YEAR_FORMAT);
	}

	/**
	 * yy日付をyyyy形式へ変換する
	 * <p>
	 * Example.
	 * 
	 * <pre>
	 * conv.toDateLongYear("11")    -> "2011"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param yy 年
	 * @return yyyy形式
	 */
	public String toDateLongYear(String yy) {
		return toDateLongYmd(yy);
	}

	/**
	 * 数値文字列をカンマ付きフォーマットへ変換する
	 * <p>
	 * Example.
	 * 
	 * <pre>
	 * conv.toCommaFormat("12345")    ->  "12,345"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param val 文字列
	 * @return カンマ付き文字列、引数が数値と判断できない場合は空文字列
	 */
	public String toCommaFormat(String val) {
		return StringUtils.toCommaFormat(val);
	}

	/**
	 * 指定した文字列で左詰めする
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * conv.leftPad(&quot;hoge&quot;, 6, &quot; &quot;)	-&gt; &quot;  hoge&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str 文字列
	 * @param size パディング回数
	 * @param padStr パディング文字列
	 * @return パディング後の文字列
	 */
	public String leftPad(String str, int size, String padStr) {
		return StringUtils.leftPad(str, size, padStr);
	}

	/**
	 * 指定した文字列で右詰めする
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * conv.rightPad(&quot;hoge&quot;, 6, &quot; &quot;)	-&gt; &quot;hoge  &quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str 文字列
	 * @param size パディング回数
	 * @param padStr パディング文字列
	 * @return パディング後の文字列
	 */
	public String rightPad(String str, int size, String padStr) {
		return StringUtils.rightPad(str, size, padStr);
	}

	/**
	 * 0で左詰めする
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * conv.leftPadZero(&quot;111&quot;,6)	-&gt; &quot;000111&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str 文字列
	 * @param size パディング回数
	 * @return パディング後の文字列
	 */
	public String leftPadZero(String str, int size) {
		return StringUtils.leftPadZero(str, size);
	}

	/**
	 * 0で右詰めする
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * conv.rightPadZero(&quot;111&quot;,6)	-&gt; &quot;111000&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str 文字列
	 * @param size パディング回数
	 * @return パディング後の文字列
	 */
	public String rightPadZero(String str, int size) {
		return StringUtils.rightPadZero(str, size);
	}

	/**
	 * 指定した位置まで文字列を左から切り捨てる
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * conv.left(null, 5)    -&gt; null
	 * conv.left("", 5)      -&gt; ""
	 * conv.left("abc", 0)   -&gt; ""
	 * conv.left("abc", 2)   -&gt; "ab"
	 * conv.left("abc", 4)   -&gt; "abc"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str 文字列
	 * @param len 切捨て位置
	 * @return 切捨て後の文字列
	 */
	public String left(String str, int len) {
		return StringUtils.left(str, len);
	}

	/**
	 * 指定した位置まで文字列を右から切り捨てる 　
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * conv.right(null, 5)    -&gt; null
	 * conv.right("", 5)      -&gt; ""
	 * conv.right("abc", 0)   -&gt; ""
	 * conv.right("abc", 2)   -&gt; "bc"
	 * conv.right("abc", 4)   -&gt; "abc"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param str 文字列
	 * @param len 切捨て位置
	 * @return 切捨て後の文字列
	 */
	public String right(String str, int len) {
		return StringUtils.right(str, len);
	}

	/**
	 * 文字列からBigDecimal型を作成する.
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * conv.toNum(&quot;500&quot;)
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param decimalValue 数値
	 * @return BigDecimal型
	 */
	public BigDecimal toNum(String decimalValue) {
		return new BigDecimal(decimalValue);
	}

	/**
	 * int型数値からBigDecimal型を作成する.
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * conv.toNum(500)
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param decimalValue 数値
	 * @return BigDecimal型
	 */
	public final BigDecimal toNum(int decimalValue) {
		return new BigDecimal(decimalValue);
	}

	/**
	 * "MM/dd HH:mm"形式を"yyyy/MM/dd HH:mm"形式のDate型を作成する.
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * conv.toDate(&quot;10/10 12:10&quot;)
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param dateValue 日付（MM/dd hh:mm）
	 * @return Date型
	 */
	public Date toDate(String dateValue) {
		if (DateUtils.isDate(dateValue, "MM/dd HH:mm")) {
			String year = DateUtils.format(DateUtils.getSystemDateTime(), "yyyy");
			return DateUtils.stringToDate(year + "/" + dateValue, "yyyy/MM/dd HH:mm");
		}
		return null;
	}

	/**
	 * 対象文字列のフォーマット yy/MM/dd から yyyy/MM/dd へ変更する.
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * conv.toDateLongYmd(&quot;10/08/02&quot;)          -&gt; &quot;2010/08/02&quot;
	 * 
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param value 文字列
	 * @return フォーマットがyyyy/MM/ddの文字列
	 */
	public String toDateLongYmd(String value) {
		String currentYear = DateUtils.format(DateUtils.getSystemDateTime(), "yyyy");
		return currentYear.substring(0, 2) + value;
	}

	/**
	 * yyyyMMdd形式をyyyy形式に変換する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * conv.toDateYear(&quot;&quot;)                  -&gt; null
	 * conv.toDateYear(null)                          -&gt; null
	 * conv.toDateYear(&quot;20100810&quot;)          -&gt; &quot;2010&quot;
	 * conv.toDateYear(&quot;20101310&quot;)          -&gt; &quot;20101310&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param value 文字列
	 * @return yyyy形式
	 */
	public String toDateYear(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		if (!DateUtils.isDate(value)) {
			return value;
		}
		return DateUtils.format(DateUtils.parse(value), DateUtils.Formats.YEAR_FORMAT);
	}

	/**
	 * yyyyMMdd形式をMM形式に変換する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * conv.toDateMonth(&quot;&quot;)                  -&gt; null
	 * conv.toDateMonth(null)                          -&gt; null
	 * conv.toDateMonth(&quot;20100810&quot;)          -&gt; &quot08&quot;
	 * conv.toDateMonth(&quot;20101310&quot;)          -&gt; &quot;20101310&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param value 文字列
	 * @return MM形式
	 */
	public String toDateMonth(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		if (!DateUtils.isDate(value)) {
			return value;
		}
		return DateUtils.format(DateUtils.parse(value), DateUtils.Formats.MONTH_FORMAT);
	}

	/**
	 * yyyyMMdd形式をdd形式に変換する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * conv.toDateDay(&quot;&quot;)                  -&gt; null
	 * conv.toDateDay(null)                          -&gt; null
	 * conv.toDateDay(&quot;20100810&quot;)          -&gt; &quot;10&quot;
	 * conv.toDateDay(&quot;20101310&quot;)          -&gt; &quot;20101310&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param value 文字列
	 * @return dd形式
	 */
	public String toDateDay(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		if (!DateUtils.isDate(value)) {
			return value;
		}
		return DateUtils.format(DateUtils.parse(value), DateUtils.Formats.DATE_FORMAT);
	}

	/**
	 * "HHmm"形式を"HH:mm"形式に変換する.
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * conv.toTimeHm(&quot;&quot;)                    -&gt; null
	 * conv.toTimeHm(&quot;1210&quot;)                -&gt; &quot;12:10&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param value 時刻（hhmm）
	 * @return hh:mm形式
	 */
	public String toTimeHm(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		if (!DateUtils.isDate(value, DateUtils.Formats.HOUR_MIN_FORMAT)) {
			return value;
		}
		return DateUtils.format(DateUtils.parse(value, DateUtils.Formats.HOUR_MIN_FORMAT), DateUtils.Formats.HOUR_COLON_MIN_FORMAT);
	}

	/**
	 * 指定した文字で文字列を文字列リストへ分割する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * conv.split("abc,def", ",") -> ["abc", "def"]
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param value 文字列
	 * @param separatorChars 区切り文字
	 * @return 文字列リスト
	 */
	public List<String> split(String value, String separatorChars) {
		String[] valArray = StringUtils.split(value, separatorChars);
		return Arrays.asList(valArray);
	}

	/**
	 * 改行コードで文字列リストへ分割する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * conv.splitLineSep("あいう\r\nえお\nかきく\rけこ") -> ["あいう", "えお", "かきく", "けこ"]
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param value 文字列
	 * @return 文字列リスト
	 */
	public List<String> splitLineSep(String value) {
		List<String> lineList = new ArrayList<String>();
		if (value == null) {
			return lineList;
		}
		BufferedReader br = null;
		try {
			InputStream is = new ByteArrayInputStream(value.getBytes());
			br = new BufferedReader(new InputStreamReader(is));
			String line = "";
			while ((line = br.readLine()) != null) {
				lineList.add(line);
			}
		} catch (IOException e) {
			throw new RuntimeException("not ", e);
		} finally {
			FileUtils.closeQuietly(br);
		}

		return lineList;
	}

	/**
	 * 文字列リストを連結する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * List&lt;String&gt; list -> ["あいうえお", "かきくけこ"]
	 * conv.join(list) -> "あいうえおかきくけこ";
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param list 文字列リスト
	 * @return 連結された文字列
	 */
	public String join(List<String> list) {
		return join(list, "");
	}

	/**
	 * 文字列リストをカンマで連結する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * List&lt;String&gt; list -> ["あいうえお", "かきくけこ"]
	 * conv.joinByComma(list) -> "あいうえお,かきくけこ";
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param list 文字列リスト
	 * @return 連結された文字列
	 */
	public String joinByComma(List<String> list) {
		return join(list, COMMA);
	}

	/**
	 * 文字列リストを改行コードで連結する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * List&lt;String&gt; list -> ["あいうえお", "かきくけこ"]
	 * conv.joinByLineSep(list) -> "あいうえお\r\nかきくけこ";
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param list 文字列リスト
	 * @return 連結された文字列
	 */
	public String joinByLineSep(List<String> list) {
		return join(list, CRLF);
	}

	/**
	 * 文字列リストを連結する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * List&lt;String&gt; list -> ["あいうえお", "かきくけこ"]
	 * conv.join(list, ":") -> "あいうえお:かきくけこ";
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param list 文字列リスト
	 * @param delimiter デリミタ
	 * @return 連結された文字列
	 */
	public String join(List<String> list, String delimiter) {
		StringBuilder sb = new StringBuilder();
		Iterator<String> iter = list.iterator();
		if (iter.hasNext()) {
			sb.append(toString(iter.next()));
			while (iter.hasNext()) {
				sb.append(delimiter);
				sb.append(toString(iter.next()));
			}
		}
		return sb.toString();
	}

	/**
	 * ２つのパスを結合する
	 * 
	 * @param path1 １つ目のパス
	 * @param path2 ２つ目のパス
	 * @return 結合したパス
	 */
	public String pathjoin(String path1, String path2) {
		return FileUtils.join(path1, path2);
	}

	/**
	 * ファイルパスからファイル名を取得する
	 * <p>
	 * ※Windows、UNIX系の両方のセパレータに対応<br>
	 * Example:
	 * 
	 * <pre>
	 * conv.getFileName(&quot;/temp/abc.txt&quot;)       -&gt;  abc.txt
	 * conv.getFileName(&quot;C:\\temp\\abc.txt&quot;)   -&gt;  abc.txt
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param path ファイルパス
	 * @return ファイル名
	 */
	public String getFileName(String path) {
		return FileUtils.getFileName(path);
	}

	/**
	 * 指定した文字列に改行コードを付与して返す<br />
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * conv.addLineSep("あいえうえお") -> "あいうえお\r\n";
	 * conv.addLineSep(null) -> "\r\n";
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param value 文字列
	 * @return 文字列に改行コードを付与したもの
	 */
	public String addLineSep(String value) {
		return toString(value).concat(CRLF);
	}

}
