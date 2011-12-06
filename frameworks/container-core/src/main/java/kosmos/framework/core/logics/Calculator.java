/**
 * Copyright 2011 the original author
 */
package kosmos.framework.core.logics;

import static kosmos.framework.utility.DateUtils.format;
import static kosmos.framework.utility.DateUtils.parse;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import kosmos.framework.utility.DateUtils;
import kosmos.framework.utility.Utils;

/**
 * 計算エンジン.
 * 
 * @author yoshida-n
 * @version 2010/10/25 新規作成
 */
public class Calculator {

	/**
	 * BigDecimalの足し算を行う。
	 * 
	 * @param src ソース
	 * @param target 追加値
	 * @return 計算結果
	 */
	public BigDecimal plus(BigDecimal src, BigDecimal target) {
		return src.add(target);
	}

	/**
	 * 任意の数のBigDecimalの足し算を行う。
	 * 
	 * @param num 数値
	 * @return result 計算結果
	 */
	public BigDecimal sum(BigDecimal... num) {
		BigDecimal result = new BigDecimal("0");
		for (int i = 0; i < num.length; i++) {
			if (num != null) {
				result = result.add(num[i]);
			}
		}
		return result;
	}

	/**
	 * BigDecimalの引き算を行う。
	 * 
	 * @param src ソース
	 * @param target マイナス値
	 * @return 計算結果
	 */
	public BigDecimal minus(BigDecimal src, BigDecimal target) {
		return src.subtract(target);
	}

	/**
	 * BigDecimalの割り算を行う。
	 * 
	 * @param src ソース
	 * @param target 除算値
	 * @return 計算結果
	 */
	public BigDecimal divide(BigDecimal src, BigDecimal target) {
		return src.divide(target);
	}

	/**
	 * BigDecimalの割り算を行う。
	 * 
	 * @param src ソース
	 * @param target 除算値
	 * @param scale 小数点精度（切り捨て）
	 * @param round 丸めモード
	 * @return 計算結果
	 */
	public BigDecimal divide(BigDecimal src, BigDecimal target, int scale, int round) {
		return src.divide(target, scale, round);
	}

	/**
	 * BigDecimalの掛け算を行う。
	 * 
	 * @param src ソース
	 * @param target 掛け算値
	 * @return 計算結果
	 */
	public BigDecimal multiply(BigDecimal src, BigDecimal target) {
		return src.multiply(target);
	}

	/**
	 * システム日付を取得する<br/>
	 * DBのSYSDATEではないことに注意
	 * 
	 * @return システム日付
	 */
	public String getSysDate() {
		return format(getSystemDateTime(), Utils.DateScope.Formats.DEFAULT_FORMAT);
	}

	/**
	 * 秒数まで含むシステム日付を取得する<br/>
	 * DBのSYSDATEではないことに注意
	 * 
	 * @return システム日付
	 */
	public String getSysDateTime() {
		return format(getSystemDateTime(), Utils.DateScope.Formats.DATETIME_LONG_SECOND_FORMAT);
	}

	/**
	 * 秒数まで含むシステム日付を取得する<br/>
	 * DBのSYSDATEではないことに注意
	 * 
	 * @return システム日付
	 */
	public String getSysDateTimeHm() {
		return format(getSystemDateTime(), Utils.DateScope.Formats.HOUR_MIN_FORMAT);
	}

	/**
	 * 秒数まで含むシステム日付を取得する<br/>
	 * DBのSYSDATEではないことに注意
	 * 
	 * @return システム日付
	 */
	public String getSysDateTimeShortSec() {
		return format(getSystemDateTime(), Utils.DateScope.Formats.DATETIME_SHORT_SECOND_FORMAT);
	}

	/**
	 * 年を加算する <br />
	 * Example:
	 * 
	 * <pre>
	 * String date -&gt; "20100101"
	 * calc.addYears(date, 1) -&gt; "20110101"
	 * </pre>
	 * 
	 * @param date 基準年月日（YYYYMMDD）
	 * @param amount 追加する年数
	 * @return インクリメント後の年月日（YYYYMMDD）
	 */
	public String addYears(String date, int amount) {
		return incrementDate(date, Calendar.YEAR, amount);
	}

	/**
	 * 月を加算する <br />
	 * Example:
	 * 
	 * <pre>
	 * String date -&gt; "20100101"
	 * calc.addMonths(date, 1) -&gt; "20100201"
	 * </pre>
	 * 
	 * @param date 基準年月日（YYYYMMDD）
	 * @param amount 追加する月数
	 * @return インクリメント後の年月日（YYYYMMDD）
	 */
	public String addMonths(String date, int amount) {
		return incrementDate(date, Calendar.MONTH, amount);
	}

	/**
	 * 日を加算する <br />
	 * Example:
	 * 
	 * <pre>
	 * String date -&gt; "20100101"
	 * calc.addDays(date, 1) -&gt; "20100102"
	 * </pre>
	 * 
	 * @param date 基準年月日（YYYYMMDD）
	 * @param amount 追加する日数
	 * @return インクリメント後の年月日（YYYYMMDD）
	 */
	public String addDays(String date, int amount) {
		return incrementDate(date, Calendar.DATE, amount);
	}

	/**
	 * 日付をインクリメントする
	 * <p>
	 * 基準年月日に日付を加えた日付を返す<br />
	 * <br />
	 * Example:
	 * 
	 * <pre>
	 * String date -&gt; "20080101"
	 * calc.incrementDate(date, Calendar.MONTH, 1) -&gt; "20080201"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param date 基準年月日（YYYYMMDD）
	 * @param field フィールドの値
	 * @param amount 追加される日付
	 * @return インクリメント後の年月日（YYYYMMDD）
	 */
	public String incrementDate(String date, int field, int amount) {
		return format(DateUtils.increment(parse(date), field, amount));
	}

	/**
	 * 年月の文字列から、その年月の最初の日付をyyyyMMdd形式で取得する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * calc.getFirstDay(&quot;200901&quot;)	-&gt; "20090101"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param yearMonth 年月(yyyyMM)
	 * @return 月初の日付
	 */
	public String getFirstDay(String yearMonth) {
		return format(DateUtils.getFirstDayOfMonth(yearMonth));
	}

	/**
	 * 年月の文字列から、その年月の末日をyyyyMMdd形式で取得する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * calc.getLastDay(&quot;200901&quot;)	-&gt; "20090131"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param yearMonth 年月(yyyyMM)
	 * @return 末日
	 */
	public String getLastDay(String yearMonth) {
		return format(DateUtils.getLastDayOfMonth(yearMonth));
	}

	/**
	 * 指定された年月の翌月を取得する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * calc.getNextMonth(&quot;200801&quot;)	-&gt; "200802"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param yearMonth yyyyMM形式の日付文字列
	 * @return 翌月 (yyyyMM)
	 */
	public String getNextMonth(String yearMonth) {
		return DateUtils.getNextMonth(yearMonth);
	}

	/**
	 * 指定された年月の前月を取得する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * calc.getPreviousMonth(&quot;200802&quot;)	-&gt; "200801"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param yearMonth yyyyMM形式の日付文字列
	 * @return 前月 (yyyyMM)
	 */
	public String getPreviousMonth(String yearMonth) {
		return DateUtils.getPreviousMonth(yearMonth);
	}

	/**
	 * 年月の差を計算する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * calc.getMonthsBetween(&quot;200801&quot;, &quot;200711&quot;)	-&gt; 2
	 * calc.getMonthsBetween(&quot;200711&quot;, &quot;200801&quot;)	-&gt; 2
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param fromYearMonth 開始年月
	 * @param toYearMonth 終了年月
	 * @return 開始年月と終了年月の月数
	 */
	public int getMonthsBetween(String fromYearMonth, String toYearMonth) {
		return DateUtils.getMonthsBetween(fromYearMonth, toYearMonth);
	}

	/**
	 * 年間の日数を取得する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * calc.getDaysOfYear(&quot;2008&quot;)	-&gt; 366
	 * calc.getDaysOfYear(&quot;2011&quot;)	-&gt; 365
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param year 年
	 * @return 年間の日数
	 */
	public int getDaysOfYear(String year) {
		int y = Integer.parseInt(year);
		return (y % 4 == 0 && y % 100 != 0) || (y % 400 == 0) ? 366 : 365;
	}

	/**
	 * 終了日付-開始日付の日数 （閾値は含まない）を取得する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * calc.countDays(&quot;20080101&quot;, &quot;20080205&quot;) -&gt; 35
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param fromDate 開始日付
	 * @param toDate 終了日付
	 * @return 日数
	 */
	public int countDays(String fromDate, String toDate) {
		return DateUtils.count(parse(fromDate), parse(toDate), Calendar.DATE);
	}

	/**
	 * 指定された数値の最小値を取得する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * calc.min(10, 150)	-&gt; 10
	 * calc.min(0, -100)	-&gt; -100
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param num1 数値
	 * @param num2 数値
	 * @return 最小値
	 */
	public int min(int num1, int num2) {
		return Math.min(num1, num2);
	}

	/**
	 * 指定された数値の最小値を取得する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * calc.min(0, -100)	-&gt; -100
	 * calc.min(10, 10.0)	-&gt; 10
	 * calc.min(10.0, 10)	-&gt; 10.0
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param num1 数値
	 * @param num2 数値
	 * @return 最小値
	 */
	public BigDecimal min(BigDecimal num1, BigDecimal num2) {
		return num1 == null || num2 == null ? null : num1.min(num2);
	}

	/**
	 * 指定された数値の最大値を取得する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * calc.min(10, 150)	-&gt; 150
	 * calc.min(0, -100)	-&gt; 0
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param num1 数値
	 * @param num2 数値
	 * @return 最大値
	 */
	public int max(int num1, int num2) {
		return Math.max(num1, num2);
	}

	/**
	 * 指定された数値の最小値を取得する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * calc.max(null, -100)	-&gt; null
	 * calc.max(0, null)	-&gt; 0
	 * calc.max(0, -100)	-&gt; 0
	 * calc.max(10, 10.0)	-&gt; 10
	 * calc.max(10.0, 10)	-&gt; 10.0
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param num1 数値
	 * @param num2 数値
	 * @return 最小値
	 */
	public BigDecimal max(BigDecimal num1, BigDecimal num2) {
		return num1 == null || num2 == null ? null : num1.max(num2);
	}

	/**
	 * 指定された数値の絶対値を取得する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * calc.abs(1)	-&gt; 1
	 * calc.abs(-1)	-&gt; 1
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param target 数値
	 * @return 絶対値
	 */
	public int abs(int target) {
		return Math.abs(target);
	}

	/**
	 * 指定された数値の絶対値を取得する
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * calc.abs(null)	-&gt; null
	 * calc.abs(1)	-&gt; 1
	 * calc.abs(-1.0)	-&gt; 1.0
	 * calc.abs(-123456.78901)	-&gt; 123456.78901
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param target 数値
	 * @return 絶対値
	 */
	public BigDecimal abs(BigDecimal target) {
		return target == null ? null : target.abs();
	}

	/**
	 * システム日時を取得する(戻り値 Date型)
	 * <p>
	 * 
	 * <pre>
	 * calc.getSystemDateTime()	-&gt; Wed Mar 02 19:54:27 JST 2011
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @return システム日時を取得する
	 */
	public Date getSystemDateTime() {
		return DateUtils.getSystemDateTime();
	}

	/**
	 * 現在時刻の1秒前の時刻(HHmm形式)を取得する<br/>
	 * 
	 * @return 現在時刻の1秒前の時刻(HHmm形式)を取得する
	 */
	public String getHHMMTimeBeforeOneSec() {
		return DateUtils.format(getDateTimeBeforeOneSec(), Utils.DateScope.Formats.HOUR_MIN_FORMAT);
	}

	/**
	 * 現在時刻の1秒前の時刻を取得する<br/>
	 * 
	 * @return 現在時刻の1秒前の時刻を取得する
	 */
	public Date getDateTimeBeforeOneSec() {
		return getDateTimeBeforeOneSec(getSystemDateTime());
	}

	/**
	 * 引数で指定された時刻の1秒前の時刻を取得する<br/>
	 * Example:
	 * 
	 * <pre>
	 * Date currentTime = calc.getSystemDateTime();
	 * Date previousOneSecTime = calc.getPrevOneSecSystemDateTime(currentTime);
	 * </pre>
	 * 
	 * @param currentTime 現在時刻
	 * @return 引数で指定された時刻の1秒前の時刻
	 */
	public Date getDateTimeBeforeOneSec(Date currentTime) {
		return new Date(currentTime.getTime() - 1000L);
	}
	
	/**
	 * Mapのキーの先頭の値を取得する<br/>
	 * Example:
	 * 
	 * <pre>
	 * 	Map<String,String> map = getResultMap();
	 *  String first = getFirstKey(map);
	 * <pre>
	 * 
	 * @param map map
	 * @return 先頭のキー
	 */
	public <T> T getFirstKey(Map<T,Object> map){
		if(map != null){
			Iterator<T> keyset = map.keySet().iterator();
			if(keyset.hasNext()) {
				return keyset.next();
			}
		}
		return null;
	}
	
	/**
	 * Mapの先頭の値を取得する<br/>
	 * Example:
	 * 
	 * <pre>
	 * 	Map<String,String> map = getResultMap();
	 *  String first = getFirstValue(map);
	 * <pre>
	 * 
	 * @param map map
	 * @return 先頭の値
	 */
	public <T> T getFirstValue(Map<Object,T> map){
		if(map != null){
			Iterator<T> values = map.values().iterator();
			if(values.hasNext()) {
				return values.next();
			}
		}
		return null;
	}
}
