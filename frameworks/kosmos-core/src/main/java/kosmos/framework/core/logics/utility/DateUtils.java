/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.logics.utility;

import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Utility for date.
 *
 * @author yoshida-n
 * @version 2011/08/31	created.
 */
public final class DateUtils implements Utils.DateScope {

	/**
	 * プライベートコンストラクタ.
	 */
	private DateUtils() {
	}

	/**
	 * <code>Date</code>オブジェクトから指定された日付フォーマットで文字列へ変換する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * //date -&gt; 2008/01/02 10:11:12
	 * DateUtils.format(date)	-&gt; &quot;20080102&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param date 日付(<code>Date</code>形式)
	 * @return 日付(<code>String</code>形式)
	 */
	public static String format(Date date) {
		return format(date, Formats.DEFAULT_FORMAT);
	}

	/**
	 * <code>Date</code>オブジェクトから指定された日付フォーマットで文字列へ変換する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * //date -&gt; 2008/01/02 10:11:12
	 * DateUtils.format(date, DateUtils.DATE_DEFAULT_FORMAT)	-&gt; &quot;20080102&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param date 日付(<code>Date</code>形式)
	 * @param pattern 日付フォーマット({@link #DATE_YEAR_FORMAT }, {@link #DATE_MONTH_FORMAT }, {@link #DATE_DATE_FORMAT }, {@link #DATE_YEAR_MONTH_FORMAT },
	 *            {@link #DATE_YEAR_SLA_MONTH_FORMAT }, {@link #DATE_YEAR_SLA_MONTH_SLA_DATE_FORMAT }, {@link #DATE_DEFAULT_FORMAT },
	 *            {@link #DATETIME_FORMAT }, {@link #TIME_FORMAT })
	 * @return 日付(<code>String</code>形式)
	 */
	public static String format(Date date, String pattern) {
		return dateToString(date, pattern);
	}

	/**
	 * 文字列から<code>Date</code>オブジェクトを取得する
	 * <p>
	 * 文字列は下記のいずれかの形式にする必要がある。
	 * <ul>
	 * <li>yyyyMMdd</li>
	 * <li>yyyyMM</li>
	 * <li>yyyy/MM/dd</li>
	 * </ul>
	 * <br />
	 * Example:
	 * 
	 * <pre>
	 * DateUtils.parse(&quot;200801&quot;)	-&gt; '2008/01/01'
	 * DateUtils.parse(&quot;20080102&quot;)	-&gt; '2008/01/02'
	 * DateUtils.parse(&quot;2008/01/02&quot;)	-&gt; '2008/01/02'
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param date 文字列表現の日付(yyyyMMdd or yyyyMM or yyyy/MM/dd)
	 * @return 文字列に対応する<code>Date</code>オブジェクト
	 * @see #parse(String, String...)
	 */
	public static Date parse(String date) {
		return parseDate(date, Formats.DEFAULT_FORMAT, Formats.YEAR_MONTH_FORMAT, Formats.YEAR_SLA_MONTH_SLA_DATE_FORMAT);
	}

	/**
	 * 文字列から<code>Date</code>オブジェクトを取得する
	 * <p>
	 * 文字列は下記のいずれかの形式にする必要がある。
	 * <ul>
	 * <li>yyyyMMdd</li>
	 * <li>yyyyMM</li>
	 * <li>yyyy/MM/dd</li>
	 * <li>yyyyMMddHHmm</li>
	 * </ul>
	 * <br />
	 * Example:
	 * 
	 * <pre>
	 * DateUtils.parse(&quot;200801&quot;)	-&gt; '2008/01/01'
	 * DateUtils.parse(&quot;20080102&quot;)	-&gt; '2008/01/02'
	 * DateUtils.parse(&quot;2008/01/02&quot;)	-&gt; '2008/01/02'
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param date 文字列表現の日付(yyyyMMdd or yyyyMM or yyyy/MM/dd)
	 * @return 文字列に対応する<code>Date</code>オブジェクト
	 * @see #parse(String, String...)
	 */
	public static Date parseWithHhmm(String date) {
		return parseDate(date, Formats.DEFAULT_FORMAT, Formats.YEAR_MONTH_FORMAT, Formats.YEAR_SLA_MONTH_SLA_DATE_FORMAT, Formats.DEFAULT_FORMAT + Formats.HOUR_MIN_FORMAT, Formats.HOUR_COLON_MIN_FORMAT, Formats.HOUR_MIN_FORMAT);
	}

	/**
	 * フォーマットパターンを指定して文字列から<code>Date</code>オブジェクトを取得する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * DateUtils.parse(&quot;200801&quot;, DateUtils.Formats.YEAR_MONTH_FORMAT) -&gt; '2008/01/01'
	 * DateUtils.parse(&quot;2008/01/02&quot;, DateUtils.Formats.YEAR_SLA_MONTH_SLA_DATE_FORMAT) -&gt; '2008/01/02'
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param date 文字列表現の日付
	 * @param parsePatterns 日付フォーマット({@link #Formats.YEAR_FORMAT }, {@link #Formats.MONTH_FORMAT }, {@link #Formats.DATE_FORMAT }, {@link #Formats.YEAR_MONTH_FORMAT }, {@link #Formats.YEAR_SLA_MONTH_FORMAT }, {@link #Formats.YEAR_SLA_MONTH_SLA_DATE_FORMAT },
	 *            {@link #Formats.DEFAULT_FORMAT }, {@link #DATETIME_FORMAT }, {@link #TIME_FORMAT })
	 * @return 文字列に対応する<code>Date</code>オブジェクト
	 */
	public static Date parse(String date, String... parsePatterns) {
		return parseDate(date, parsePatterns);
	}

	/**
	 * 日付が指定したフォーマットかどうか、実際に存在する日付であるかどうかを判定する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * //「2008年1月」の妥当性をチェックする場合
	 * DateUtils.isDate(&quot;200805&quot;, DateUtils.Formats.YEAR_MONTH_FORMAT)	-&gt; true
	 * //「2008年5月1日17時」の妥当性をチェックする場合
	 * DateUtils.isDate(&quot;2001050117&quot;, DateUtils.Formats.DEFAULT_FORMAT) -&gt; false(フォーマットが異なるため)
	 * //「2009年2月29日」の妥当性をチェックする場合
	 * DateUtils.isDate(&quot;20090229&quot;, DateUtils.Formats.DEFAULT_FORMAT)	-&gt; false(日付が妥当でない)
	 * </li>
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param date 日付
	 * @param pattern 日付のフォーマット
	 * @return 妥当な日付である場合<code>true</code>、それ以外は<code>false</code>
	 */
	public static boolean isDate(String date, String pattern) {
		if (ObjectUtils.isLeastEmpty(date, pattern)) {
			return false;
		}
		return check(date, pattern);
	}

	/**
	 * 日付(yyyyMMdd形式)が実際に存在する日付であるかどうかを判定する
	 * <p>
	 * ※日付フォーマットは{@link #Formats.DEFAULT_FORMAT}となる
	 * </p>
	 * 
	 * @param date 日付(yyyyMMdd)
	 * @return 妥当な日付である場合<code>true</code>、それ以外は<code>false</code>
	 * @see #isDate(String, String)
	 */
	public static boolean isDate(String date) {
		return isDate(date, Formats.DEFAULT_FORMAT);
	}

	/**
	 * 月の日数を取得する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * DateUtils.countDayOfMonth(&quot;200901&quot;)	-&gt; 31
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param yearMonth 基準年月(yyyymm)
	 * @return int 月の日数(月末日)
	 */
	public static int countDayOfMonth(String yearMonth) {
		// 日付の妥当性チェック
		if (!check(yearMonth, Formats.YEAR_MONTH_FORMAT)) {
			throw new IllegalArgumentException(String.format("Illegal format of date: %s", yearMonth));
		}

		Calendar calendar = createCalendar(yearMonth);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 月の日数を取得する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * //Date date -&gt; '2009/01/01'
	 * DateUtils.countDayOfMonth(date)	-&gt; 31
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param date 日数を取得したい年月を含む<code>Date</code>オブジェクト
	 * @return int 月の日数(月末日)
	 */
	public static int countDayOfMonth(Date date) {
		String formatted = format(date, Formats.YEAR_MONTH_FORMAT);
		return countDayOfMonth(formatted);
	}

	/**
	 * 年月の文字列から、その年月の最初の日付を<code>Date</code>型で取得する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * DateUtils.getFirstDayOfMonth(&quot;200901&quot;)	-&gt; '2009/01/01'
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param yearMonth 年月(yyyyMM)
	 * @return 月初の日付
	 */
	public static Date getFirstDayOfMonth(String yearMonth) {
		// 日付の妥当性チェック
		if (!check(yearMonth, Formats.YEAR_MONTH_FORMAT)) {
			throw new IllegalArgumentException(String.format("Illegal format of date: %s", yearMonth));
		}

		Calendar calendar = createCalendar(yearMonth);
		return calendar.getTime();
	}

	/**
	 * 年月の文字列から、その年月の末日を<code>Date</code>型で取得する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * DateUtils.getLastDayOfMonth(&quot;200901&quot;)	-&gt; '2009/01/31'
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param yearMonth 年月(yyyyMM)
	 * @return 末日
	 */
	public static Date getLastDayOfMonth(String yearMonth) {
		// 日付の妥当性チェック
		if (!check(yearMonth, Formats.YEAR_MONTH_FORMAT)) {
			throw new IllegalArgumentException(String.format("Illegal format of date: %s", yearMonth));
		}

		Calendar calendar = createCalendar(yearMonth);
		// 日付を月末にセット
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		return calendar.getTime();
	}

	/**
	 * 指定された年月の翌月を取得する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * DateUtils.getNextMonth(&quot;200801&quot;)	-&gt; '200802'
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param yearMonth yyyyMM形式の日付文字列
	 * @return 翌月 (yyyyMM)
	 */
	public static String getNextMonth(String yearMonth) {
		// 日付の妥当性チェック
		if (!check(yearMonth, Formats.YEAR_MONTH_FORMAT)) {
			throw new IllegalArgumentException(String.format("Illegal format of date: %s", yearMonth));
		}

		Calendar calendar = createCalendar(yearMonth);
		// 次月
		calendar.add(Calendar.MONTH, 1);

		return format(calendar.getTime(), Formats.YEAR_MONTH_FORMAT);
	}

	/**
	 * 指定された年月の前月を取得する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * DateUtils.getPreviousMonth(&quot;200802&quot;)	-&gt; '200801'
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param yearMonth yyyyMM形式の日付文字列
	 * @return 前月 (yyyyMM)
	 */
	public static String getPreviousMonth(String yearMonth) {
		// 日付の妥当性チェック
		if (!check(yearMonth, Formats.YEAR_MONTH_FORMAT)) {
			throw new IllegalArgumentException(String.format("Illegal format of date: %s", yearMonth));
		}

		Calendar calendar = createCalendar(yearMonth);
		// 前月
		calendar.add(Calendar.MONTH, -1);

		return format(calendar.getTime(), Formats.YEAR_MONTH_FORMAT);
	}

	/**
	 * 年月の差を計算する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * DateUtils.getMonthsBetween(&quot;200801&quot;, &quot;200711&quot;)	-&gt; 2
	 * DateUtils.getMonthsBetween(&quot;200711&quot;, &quot;200801&quot;)	-&gt; 2
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param fromYearMonth 開始年月
	 * @param toYearMonth 終了年月
	 * @return 開始年月と終了年月の月数
	 */
	public static int getMonthsBetween(String fromYearMonth, String toYearMonth) {
		// 日付の妥当性チェック
		if (!check(fromYearMonth, Formats.YEAR_MONTH_FORMAT)) {
			throw new IllegalArgumentException(String.format("Illegal format of date: %s", fromYearMonth));
		}
		if (!check(toYearMonth, Formats.YEAR_MONTH_FORMAT)) {
			throw new IllegalArgumentException(String.format("Illegal format of date: %s", toYearMonth));
		}

		Calendar fromCal = createCalendar(fromYearMonth);
		Calendar toCal = createCalendar(toYearMonth);

		int diff = 0;
		int unit = 1; //
		// 開始年月が終了年月より後の場合
		if (fromCal.compareTo(toCal) > 0) {
			unit = -1;
		}
		while (!fromCal.equals(toCal)) {
			fromCal.add(Calendar.MONTH, unit);
			diff += 1;
		}

		return diff;
	}

	/**
	 * 基準日付が、2つの日付で表された期間内にあるかを判定する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * Date baseDate -&gt; 2008/01/15
	 * Date fromDate -&gt; 2008/01/01
	 * Date toDate   -&gt; 2008/01/31
	 * DateUtils.between(baseDate, fromDate, toDate, DateUtils.Formats.DEFAULT_FORMAT) -&gt; true
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param baseDate 基準日付
	 * @param fromDate 開始日付
	 * @param toDate 終了日付
	 * @param fmt 日付フォーマット({@link #Formats.YEAR_FORMAT }, {@link #Formats.MONTH_FORMAT }, {@link #Formats.DATE_FORMAT }, {@link #Formats.YEAR_MONTH_FORMAT }, {@link #Formats.YEAR_SLA_MONTH_FORMAT }, {@link #Formats.YEAR_SLA_MONTH_SLA_DATE_FORMAT },
	 *            {@link #Formats.DEFAULT_FORMAT }, {@link #DATETIME_FORMAT }, {@link #TIME_FORMAT })
	 * @return 開始日付 <= 基準日付 <= 終了日付の場合<code>true</code>、それ以外の場合は<code>false</code>
	 */
	public static boolean between(Date baseDate, Date fromDate, Date toDate, String fmt) {
		if (ObjectUtils.isLeastNull(baseDate, fromDate, toDate, fmt)) {
			return false;
		}

		// 比較結果判定初期値セット
		boolean result = false;
		if (compare(baseDate, fromDate, fmt) >= 0 && compare(baseDate, toDate, fmt) <= 0) {
			// 基準日付が開始日付～終了日付の期間内の場合はtrue
			result = true;
		}
		return result;
	}

	/**
	 * 終了日付-開始日付の間隔 （閾値は含まない）を取得する
	 * <p>
	 * 開始日付と終了日付の間の<strong>日数</strong>を計算<br />
	 * <br />
	 * Example:
	 * 
	 * <pre>
	 * Date fromDate -&gt; 2008/01/01
	 * Date toDate   -&gt; 2008/02/01
	 * DateUtils.count(fromDate, toDate, DateUtils.FIELD_MONTH) -&gt; 31
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param fromDate 日数計算開始日付
	 * @param toDate 日数計算終了日付
	 * @param field フィールドの値({@link #FIELD_YEAR}, {@link #FIELD_MONTH}, {@link #FIELD_DATE})
	 * @return 日数
	 */
	public static int count(Date fromDate, Date toDate, int field) {
		// 場合分けした日付として日数カウントを呼び出す。
		Calendar fromCal = Calendar.getInstance();
		fromCal.clear();
		fromCal.setTime(fromDate);

		Calendar toCal = Calendar.getInstance();
		toCal.clear();
		toCal.setTime(toDate);

		if (field == Fields.YEAR) {
			fromCal.set(Calendar.MONTH, 1);
			toCal.set(Calendar.MONTH, 1);
		}
		if (field == Fields.YEAR || field == Fields.MONTH) {
			// 年の場合は月も設定する
			fromCal.set(Calendar.DATE, 1);
			toCal.set(Calendar.DATE, 1);
		}
		return Integer.parseInt(countDayBetween(fromCal.getTime(), toCal.getTime()));
	}

	/**
	 * 日付を比較する
	 * <p>
	 * ※時間部分は考慮されない<br />
	 * <br />
	 * Example:
	 * 
	 * <pre>
	 * Date date1 -&gt; 2008/01/01
	 * Date date2 -&gt; 2008/02/01
	 * DateUtils.compare(date1, date2) -&gt; -1
	 * DateUtils.compare(date2, date1) -&gt; 1
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param date1 比較元日付
	 * @param date2 比較先日付
	 * @return 比較元 < 比較先の場合は -1、元 = 先の場合は 0、元 > 先の場合は 1
	 */
	public static int compare(Date date1, Date date2) {
		// 日付比較結果を返す
		return compare(date1, date2, Formats.DEFAULT_FORMAT);
	}

	/**
	 * 日付・時間を比較する
	 * 
	 * @param date1 日付・時間1
	 * @param date2 日付・時間2
	 * @param fmt 日付・時間のフォーマット({@link #Formats.YEAR_FORMAT }, {@link #Formats.MONTH_FORMAT }, {@link #Formats.DATE_FORMAT }, {@link #Formats.YEAR_MONTH_FORMAT }, {@link #Formats.YEAR_SLA_MONTH_FORMAT }, {@link #Formats.YEAR_SLA_MONTH_SLA_DATE_FORMAT },
	 *            {@link #Formats.DEFAULT_FORMAT }, {@link #DATETIME_FORMAT }, {@link #TIME_FORMAT })
	 * @return date1 < date2の場合は -1、date1 = date2の場合は 0、date1 > date2の場合は 1
	 */
	public static int compare(Date date1, Date date2, String fmt) {
		// 一見無駄なことをしているように見えるが、
		// 時刻を無視して比較する際等には有効なため、
		// このメソッドのフォーマット引数は意味がある
		Date fmtDate1 = stringToDate(format(date1, fmt), fmt);
		Date fmtDate2 = stringToDate(format(date2, fmt), fmt);

		// 比較元日付をカレンダーにセット
		Calendar cal1 = Calendar.getInstance();
		cal1.clear();
		cal1.setTime(fmtDate1);

		// 比較先日付をカレンダーにセット
		Calendar cal2 = Calendar.getInstance();
		cal2.clear();
		cal2.setTime(fmtDate2);

		// 日付比較結果を返す
		return cal1.compareTo(cal2);
	}

	/**
	 * 年を加算する <br />
	 * Example:
	 * 
	 * <pre>
	 * Date date -&gt; 2010/01/01
	 * DateUtils.addYears(date, 1) -&gt; 2011/01/01
	 * </pre>
	 * 
	 * @param date 基準年月日（YYYYMMDD）
	 * @param amount 追加する年数
	 * @return インクリメント後の年月日（YYYYMMDD）
	 */
	public static Date addYears(Date date, int amount) {
		return increment(date, Fields.YEAR, amount);
	}

	/**
	 * 月を加算する <br />
	 * Example:
	 * 
	 * <pre>
	 * Date date -&gt; 2010/01/01
	 * DateUtils.addMonths(date, 1) -&gt; 2010/02/01
	 * </pre>
	 * 
	 * @param date 基準年月日（YYYYMMDD）
	 * @param amount 追加する月数
	 * @return インクリメント後の年月日（YYYYMMDD）
	 */
	public static Date addMonths(Date date, int amount) {
		return increment(date, Fields.MONTH, amount);
	}

	/**
	 * 日を加算する <br />
	 * Example:
	 * 
	 * <pre>
	 * Date date -&gt; 2010/01/01
	 * DateUtils.addDays(date, 1) -&gt; 2010/01/02
	 * </pre>
	 * 
	 * @param date 基準年月日（YYYYMMDD）
	 * @param amount 追加する日数
	 * @return インクリメント後の年月日（YYYYMMDD）
	 */
	public static Date addDays(Date date, int amount) {
		return increment(date, Fields.DATE, amount);
	}

	/**
	 * 日付をインクリメントする
	 * <p>
	 * 基準年月日に日付を加えた日付を返す<br />
	 * <br />
	 * Example:
	 * 
	 * <pre>
	 * Date date -&gt; 2008/01/01
	 * DateUtils.increment(date, DateUtils.Fields.MONTH, 1) -&gt; 2008/02/01
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param date 基準年月日（YYYYMMDD）
	 * @param field フィールドの値
	 * @param amount 追加される日付
	 * @return インクリメント後の年月日（YYYYMMDD）
	 */
	public static Date increment(Date date, int field, int amount) {
		String param = dateToString(date, Formats.DEFAULT_FORMAT);
		return dateCalc(param, field, amount);
	}

	/**
	 * 日付の指定したフィールドを丸める
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * Date date -&gt; 2008/01/01 19:00:00
	 * DateUtils.round(date, DateUtils.Fields.DATE, 1) -&gt; 2008/01/02 00:00:00
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param date 基準年月日
	 * @param field フィールドの値({@link #Fields.YEAR}, {@link #Fields.MONTH}, {@link #Fields.DATE})
	 * @return 四捨五入が行われた日付
	 */
	public static Date round(Date date, int field) {
		return org.apache.commons.lang.time.DateUtils.round(date, field);
	}

	/**
	 * 日付の指定したフィールドを切捨てる
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * Date date -&gt; 2008/01/01 19:00:00
	 * DateUtils.truncate(date, DateUtils.Fields.DATE, 1) -&gt; 2008/01/01 00:00:00
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param date 基準年月日(YYYYMMDD)
	 * @param field フィールドの値({@link #Fields.YEAR}, {@link #Fields.MONTH}, {@link #Fields.DATE})
	 * @return 切り捨てが行われた日付
	 */
	public static Date truncate(Date date, int field) {
		return DateUtils.truncate(date, field);
	}

	/**
	 * <code>Date</code>の指定範囲月を<code>Map</code>で取得する
	 * <p>
	 * ※年月プルダウン作成で使用
	 * </p>
	 * 
	 * @param date 基準年月日
	 * @param before 基準年月日より過去の月数
	 * @param after 基準年月日より未来の月数
	 * @return プルダウンリストデータ(<code>Map&lt;String, String&gt;</code>形式)
	 * @see {@link #getRangeMonth(Date, int, int, boolean)}
	 */
	public static Map<String, String> getRangeMonth(Date date, int before, int after) {
		return getRangeMonth(date, before, after, true);
	}

	/**
	 * <code>Date</code>の指定範囲月を<code>Map</code>で取得する
	 * <p>
	 * ※年月プルダウン作成で使用<br />
	 * ※ソート順指定<br />
	 * <br />
	 * Sample:
	 * 
	 * <pre>
	 * Date date -&gt; 2008/01/01
	 * // 前1ヶ月、後2ヶ月の月を取得
	 * DateUtils.getRangeMonth(date, 1, 2) -&gt; {&quot;2007/12&quot;:&quot;200712&quot;, &quot;2008/01&quot;:&quot;200801&quot;, &quot;2008/02&quot;:&quot;200802&quot;, &quot;2008/03&quot;:&quot;200803&quot;}
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param date 基準年月日
	 * @param before 基準年月日より過去の月数
	 * @param after 基準年月日より未来の月数
	 * @param asc 昇順ソートフラグ（降順の場合は<code>false</code>を設定する）
	 * @return プルダウンリストデータ(<code>Map&lt;String, String&gt;</code>形式)
	 */
	public static Map<String, String> getRangeMonth(Date date, int before, int after, boolean asc) {
		Map<String, String> range = new LinkedHashMap<String, String>();

		// 範囲開始日付を設定する
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTime(date);

		if (asc) {
			calendar.add(Fields.MONTH, -before);
			// 昇順ソート
			for (int i = 0; i <= before + after; i++) {
				// キーを設定する
				String key = dateToString(calendar.getTime(), Formats.YEAR_SLA_MONTH_FORMAT);
				// 値を設定する
				String value = dateToString(calendar.getTime(), Formats.YEAR_MONTH_FORMAT);
				// リストデータに追加する
				range.put(key, value);

				// 次月を設定する
				calendar.add(Fields.MONTH, 1);
			}
		} else {
			calendar.add(Fields.MONTH, +after);
			// 降順ソート
			for (int i = before + after; i >= 0; i--) {
				// キーを設定する
				String key = dateToString(calendar.getTime(), Formats.YEAR_SLA_MONTH_FORMAT);
				// 値を設定する
				String value = dateToString(calendar.getTime(), Formats.YEAR_MONTH_FORMAT);
				// リストデータに追加する
				range.put(key, value);

				// 次月を設定する
				calendar.add(Fields.MONTH, -1);
			}
		}

		return range;
	}

	/**
	 * 指定年月がセットされたカレンダーを取得する
	 * 
	 * @param date 日付(<code>Date</code>形式)
	 * @return 年月をセットしたカレンダー(<code>Calendar</code>形式)
	 */
	public static Calendar getCalYearMonth(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR, Integer.parseInt(format(date, Formats.YEAR_FORMAT)));
		cal.set(Calendar.MONTH, Integer.parseInt(format(date, Formats.MONTH_FORMAT)) - 1);

		return cal;
	}

	/**
	 * 指定日付の月の１日を取得する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * Date date -&gt; 2008/01/15
	 * DateUtils.getMinDate(date) -&gt; '2008/01/01'
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param date 日付(<code>Date</code>形式)
	 * @return 指定日付の月の１日(<code>Date</code>形式)
	 */
	public static Date getMinDate(Date date) {
		// 指定日付カレンダー生成（年月のみセット分）
		Calendar cal = getCalYearMonth(date);

		// 指定日付の１日を取得
		cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DAY_OF_MONTH));

		return cal.getTime();
	}

	/**
	 * 指定日付の月の末日を取得する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * Date date -&gt; 2008/01/15
	 * DateUtils.getMaxDate(date) -&gt; '2008/01/31'
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param date 日付(<code>Date</code>形式)
	 * @return 指定日付の月の末日(<code>String</code>形式)
	 */
	public static Date getMaxDate(Date date) {
		// 指定日付カレンダー生成（年月のみセット分）
		Calendar cal = getCalYearMonth(date);

		// 指定日付の末日を取得
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

		return cal.getTime();
	}

	/**
	 * 指定日付の年月を取得する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * Date date -&gt; 2008/01/15
	 * DateUtils.getYearMonth(date) -&gt; &quot;200801&quot;
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param date 日付(<code>Date</code>形式)
	 * @return 指定日付の年月(<code>String</code>形式)
	 */
	public static String getYearMonth(Date date) {
		// 指定日付の年月を取得
		String strYearMonth = format(date, Formats.YEAR_MONTH_FORMAT);

		return strYearMonth;
	}

	/**
	 * 指定日付を基準とした次月の25日を取得する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * Date date -&gt; 2008/01/30
	 * DateUtils.getNextFixDay(date) -&gt; '2008/02/25'
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param date 日付(<code>Date</code>形式)
	 * @return 指定日付を基準とした次月の25日(<code>Date</code>形式)
	 */
	public static Date getNextFixDay(Date date) {

		// 指定日付の年月をカレンダーにセット
		Calendar cal = getCalYearMonth(date);

		// 指定日付の月のカレンダー（25日分）生成
		Calendar calFixDay = (Calendar) cal.clone();
		calFixDay.set(Calendar.DATE, DATE_FIX_DATE);

		// 日付大小判定
		int intCompResult = compare(calFixDay.getTime(), date);
		if (intCompResult == -1) {
			// 指定日付が26日以上の場合は翌月をセット
			cal.add(Calendar.MONTH, 1);
		}

		// 算出した年月に25日をセット
		cal.set(Calendar.DATE, DATE_FIX_DATE);

		return cal.getTime();
	}

	/**
	 * システム日時を取得する
	 * 
	 * @return システム日時
	 */
	public static Date getSystemDateTime() {
		return new Date();
	}

	/*-------------------------------------------------------------------------
		privateメソッド
	-------------------------------------------------------------------------*/
	/**
	 * <code>Date</code>から指定したフォーマットの<code>String</code>型へ変換する
	 * 
	 * @param date 日付
	 * @param fmt フォーマット
	 * @return フォーマット変換された日付文字列
	 */
	private static String dateToString(Date date, String fmt) {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.format(date);
	}

	/**
	 * 指定したフォーマットの<code>String</code>から<code>Date</code>型へ変換する
	 * 
	 * @param date 日付
	 * @param fmt フォーマット
	 * @return <code>Date</code>型に変換された日付
	 */
	public static Date stringToDate(String date, String fmt) {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.parse(date, new ParsePosition(0));
	}

	/**
	 * 日付の妥当性をチェックする
	 * <p>
	 * ※フォーマット指定可能<br />
	 * ※日付が指定したフォーマットかどうか、実際に存在する日付であるかどうかを判定する。<br />
	 * ※java.text.SimpleDateFormatで指定可能なフォーマットをサポート。<br />
	 * <ul>
	 * <li>例1）「2001年5月」の妥当性をチェックする場合<br />
	 * isDate("200105", "yyyyMM") とする（結果は true ）</li>
	 * <li>例2）「2001年5月1日17時」の妥当性をチェックする場合<br />
	 * isDate("2001050117", "yyyyMMdd") とする（結果は false ：フォーマットが異なる）</li>
	 * <li>例3）「2001年2月29日」の妥当性をチェックする場合<br />
	 * isDate("20010229", "yyyyMMdd") とする（結果は false を返す：日付が妥当でない）</li>
	 * </ul>
	 * </p>
	 * 
	 * @param date 日付
	 * @param fmt 日付のフォーマット
	 * @return 妥当な日付である場合<code>true</code>、それ以外は<code>false</code>
	 */
	private static boolean check(String date, String fmt) {
		// 桁数チェック
		if (date.length() != fmt.length()) {
			return false;
		}

		// 日付文字列がフォーマットに適合しているかどうか解析
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		Date date2 = sdf.parse(date, new ParsePosition(0));
		if (date2 == null) {
			return false;
		}

		// 日付の妥当性をチェック
		// 日付が妥当でない場合、引数で渡された日付文字列と、それをDate型に変換した後
		// 再度文字列に変換し直した日付文字列との間に違いが生じる。
		// 例えば、引数で20010230（2001年2月30日）が渡された場合、
		// Date型に変換すると2001年3月2日となる。
		if (!date.equals(sdf.format(date2))) {
			return false;
		}

		// 日付が妥当
		return true;
	}

	/**
	 * 日付を計算する
	 * 
	 * @param date 基準日付
	 * @param field フィールドの値
	 * @param value 加減算の値
	 * @return 算出された日付
	 */
	private static Date dateCalc(String date, int field, int value) {

		// 基準年月日をカレンダーオブジェクトにセット
		Calendar cal = createCalendar(date);

		// 日付計算
		if (field == Fields.YEAR) {
			cal.add(Calendar.YEAR, value);
		} else if (field == Fields.MONTH) {
			cal.add(Calendar.MONTH, value);
		} else {
			cal.add(Calendar.DATE, value);
		}
		Date ret = cal.getTime();
		return ret;
	}

	/**
	 * フォーマットパターンを指定して文字列から<code>Date</code>オブジェクトを取得する
	 * 
	 * @param strDate 文字列表現の日付
	 * @param parsePatterns フォーマットのパターン（<code>DateUtils</code>の定数を使用のこと）
	 * @return 文字列に対応するDateオブジェクト
	 */
	private static Date parseDate(String strDate, String... parsePatterns) {
		if (strDate == null || parsePatterns.length == 0) {
			throw new IllegalArgumentException("Date and Patterns must not be null");
		}

		SimpleDateFormat parser = null;
		ParsePosition pos = new ParsePosition(0);
		for (String parsePattern : parsePatterns) {
			if (parser == null) {
				parser = new SimpleDateFormat(parsePattern);
				parser.setLenient(false);
			} else {
				parser.applyPattern(parsePattern);
			}
			pos.setIndex(0);
			Date date = parser.parse(strDate, pos);
			if (date != null && pos.getIndex() == strDate.length()) {
				return date;
			}
		}
		throw new IllegalArgumentException("Unable to parse the date: " + strDate);
	}

	/**
	 * 日付文字列(yyyyMMddもしくはyyyyMM)からカレンダーを取得する
	 * <p>
	 * ※yyyyMMの場合はその年月の月初を表すCalendarを返す
	 * </p>
	 * 
	 * @param date 日付文字列(yyyyMMddもしくはyyyyMM)
	 * @return 引数が設定された<code>Calendar</code>クラス
	 */
	private static Calendar createCalendar(String date) {

		Calendar cal = Calendar.getInstance();
		cal.clear();

		int year = Integer.parseInt(date.substring(0, 4));// 年
		int month = Integer.parseInt(date.substring(4, 6)) - 1;// 月
		int dayOfMonth = 1; // 日
		if (date.length() > 6) {
			dayOfMonth = Integer.parseInt(date.substring(6));
		}

		cal.set(year, month, dayOfMonth);
		return cal;
	}

	/**
	 * 開始日付と終了日付の間の日数を計算する
	 * <p>
	 * ※開始日付が終了日付より後の場合、<code>IllegalArgumentException</code>が発生します。<br />
	 * ※開始日付が4月1日、終了日付が4月3日の場合、2を返します。
	 * </p>
	 * 
	 * @param fromDate 日数計算開始日付
	 * @param toDate 日数計算終了日付
	 * @return 日数
	 */
	private static String countDayBetween(Date fromDate, Date toDate) {
		// 1日のミリ秒単位での時間
		final BigDecimal miliSecondPerDay = new BigDecimal(1000 * 24 * 60 * 60);

		Calendar fromCal = Calendar.getInstance();
		fromCal.clear();

		int cmpResult = compare(fromDate, toDate);
		// 開始日付 > 終了日付
		if (cmpResult == 1) {
			String errMsg = "開始日付" + format(fromDate, Formats.DEFAULT_FORMAT) + " ＞ 終了日付" + format(toDate, Formats.DEFAULT_FORMAT);
			throw new IllegalArgumentException(errMsg);
		}
		// 開始日付 = 終了日付
		if (cmpResult == 0) {
			return "0";
		}

		// 1970年1月1日から開始日付までの経過時間(msec)を取得
		Calendar calStart = createCalendar(format(fromDate, Formats.DEFAULT_FORMAT));
		long start = (calStart.getTime()).getTime();

		// 1970年1月1日から終了日付までの経過時間(msec)を取得
		Calendar calEnd = createCalendar(format(toDate, Formats.DEFAULT_FORMAT));
		long end = (calEnd.getTime()).getTime();
		// 2つの日付の間の日数を取得
		String dayCnt = Long.toString((end - start) / miliSecondPerDay.toBigInteger().longValue());

		return dayCnt;
	}
}
