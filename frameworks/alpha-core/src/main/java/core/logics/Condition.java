/**
 * Copyright 2011 the original author
 */
package core.logics;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import core.exception.PoorImplementationException;
import core.logics.utility.DateUtils;
import core.logics.utility.StringUtils;
import core.logics.utility.Scopes;


/**
 * 条件判断クラス
 *
 * @author yoshida-n
 * @version 2010/10/13
 */
public class Condition extends Validator implements Scopes.DateScope {
	/**
	 * 引数のオブジェクトが<code>null</code>かを判定する
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * cond.isNull(null)		-&gt; true
	 * cond.isNull(&quot;abc&quot;)	-&gt; false
	 * cond.isNull(&quot;&quot;)		-&gt; false
	 * </pre>
	 *
	 * </p>
	 *
	 * @param obj オブジェクト
	 * @return <code>true</code>:<code>null</code>の場合,<code>false</code>:<code>null</code>ではない場合
	 */
	public boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		}
		return false;
	}

	/**
	 * 引数のオブジェクトが<code>null</code>でないかを判定する
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * cond.isNotNull(null)		-&gt; false
	 * cond.isNotNull(&quot;abc&quot;)		-&gt; true
	 * cond.isNotNull(&quot;&quot;)		-&gt; true
	 * </pre>
	 *
	 * </p>
	 *
	 * @param obj オブジェクト
	 * @return <code>true</code>:<code>null</code>ではない場合,<code>false</code>:<code>null</code>の場合
	 */
	public boolean isNotNull(Object obj) {
		return !isNull(obj);
	}

	/**
	 * 引数のオブジェクトが空文字または<code>null</code>かを判定する
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * cond.isEmpty(null)	-&gt; true
	 * cond.isEmpty(&quot;&quot;)		-&gt; true
	 * cond.isEmpty(&quot;abc&quot;)	-&gt; false
	 * </pre>
	 *
	 * </p>
	 *
	 * @param obj オブジェクト
	 * @return <code>true</code>:nullまたはlengthが0、<code>false</code>:lengthが1以上
	 */
	public boolean isEmpty(Object obj) {
		if (obj instanceof String) {
			return StringUtils.isEmpty(obj.toString());
		} else {
			return isNull(obj);
		}
	}

	/**
	 * 引数のオブジェクトが空文字または<code>null</code>でないかを判定する
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * cond.isNotEmpty(null)	-&gt; false
	 * cond.isNotEmpty(&quot;&quot;)	-&gt; false
	 * cond.isNotEmpty(&quot;abc&quot;)	-&gt; true
	 * </pre>
	 *
	 * </p>
	 *
	 * @param obj オブジェクト
	 * @return <code>true</code>:nullまたはlengthが0、<code>false</code>:lengthが1以上
	 */
	public boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	/**
	 * 引数で渡されたすべてのオブジェクトが<code>null</code>かどうかをチェックする<br />
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * cond.isAllNull(null, null, null)	-&gt; true
	 * cond.isAllNull(&quot;&quot;, null, null)	-&gt; true
	 * cond.isAllNull(&quot;&quot;, null, &quot;abc&quot;)	-&gt; false
	 * </pre>
	 *
	 * </p>
	 *
	 * @param objects オブジェクト配列（可変長引数）
	 * @return <code>true</code>:すべて<code>null</code>
	 */
	public boolean isAllNull(Object... objects) {
		for (Object obj : objects) {
			if (!isNull(obj)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 引数で渡されたすべてのObjectが<code>null</code>または空文字かどうかをチェックする<br />
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * cond.isAllEmpty(null, &quot;&quot;, null)	-&gt; true
	 * cond.isAllEmpty(null, &quot;abc&quot;, &quot;&quot;)	-&gt; false
	 * </pre>
	 *
	 * </p>
	 *
	 * @param objects <code>Object</code>配列（可変長引数）
	 * @return <code>true</code>:すべてNULLまたは空文字
	 */
	public boolean isAllEmpty(Object... objects) {
		for (Object obj : objects) {
			if (isNotEmpty(obj)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 引数で渡されたすべてのList<?>が<code>null</code>またはisEmpty()かどうかをチェックする<br/>
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * cond.isAllEmptyList(null, #Listサイズ0)	-&gt; true
	 * cond.isAllEmptyList(null, #Listサイズ1以上)	-&gt; false
	 * cond.isAllEmptyList(#Listサイズ0, #Listサイズ1以上)	-&gt; false
	 * cond.isAllEmptyList(#Listサイズ1以上, #Listサイズ1以上)	-&gt; false
	 * </pre>
	 *
	 * </p>
	 *
	 * @param lists <code>List<?></code>配列（可変長引数）
	 * @return <code>true</code>:すべてNULLまたはisEmpty()
	 */
	public boolean isAllEmptyList(List<?>... lists) {
		for (List<?> list : lists) {
			if (list != null && !list.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * NULLチェックを行う
	 * <p>
	 * ※引数で渡されたオブジェクトが少なくとも１つが<code>null</code>かどうかをチェックする<br />
	 * <br />
	 * Example:
	 *
	 * <pre>
	 * cond.isLeastNull(null, null, null)		-&gt; true
	 * cond.isLeastNull(null, &quot;abc&quot;, &quot;def&quot;)	-&gt; true
	 * cond.isLeastNull(&quot;&quot;, &quot;abc&quot;, &quot;def&quot;)		-&gt; false
	 * </pre>
	 *
	 * </p>
	 *
	 * @param objects オブジェクト配列（可変長引数）
	 * @return <code>true</code>:少なくとも１つのオブジェクトが<code>null</code>
	 */
	public boolean isLeastNull(Object... objects) {
		for (Object obj : objects) {
			if (isNull(obj)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * NULLまたは空文字チェックを行う
	 * <p>
	 * ※引数で渡されたオブジェクトが少なくとも１つが<code>null</code>または空文字かどうかをチェックする<br />
	 * <br />
	 * Example:
	 *
	 * <pre>
	 * cond.isLeastEmpty(&quot;&quot;, &quot;&quot;, &quot;&quot;)		-&gt; true
	 * cond.isLeastEmpty(&quot;&quot;, &quot;abc&quot;, null)		-&gt; true
	 * cond.isLeastEmpty(&quot;abc&quot;, &quot;def&quot;, null)	-&gt; true
	 * cond.isLeastEmpty(&quot;abc&quot;, &quot;def&quot;)		-&gt; false
	 * </pre>
	 *
	 * </p>
	 *
	 * @param objects <code>Object</code>配列（可変長引数）
	 * @return <code>true</code>:少なくとも１つの文字列が<code>null</code>または空文字
	 */
	public boolean isLeastEmpty(Object... objects) {

		for (Object obj : objects) {
			if (isEmpty(obj)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 引数で渡されたすべてのオブジェクトが<code>null</code>以外かつ空文字以外かどうかをチェックする<br />
	 * <br />
	 * Example:
	 *
	 * <pre>
	 * cond.isAllNotEmpty(null, null, null)		-&gt; false
	 * cond.isAllNotEmpty(null, &quot;abc&quot;, &quot;def&quot;)	-&gt; fales
	 * cond.isAllNotEmpty(&quot;ghi&quot;, &quot;abc&quot;, &quot;def&quot;)		-&gt; true
	 * </pre>
	 *
	 * @param objects <code>Object</code>配列（可変長引数）
	 * @return <code>true</code>:すべてNULL以外かつ空文字以外
	 */
	public boolean isAllNotEmpty(Object... objects) {
		return !isLeastEmpty(objects);
	}

	/**
	 * ※引数で渡されたオブジェクトが少なくとも１つが<code>null</code>以外かつ空文字以外かどうかをチェックする<br />
	 * <br />
	 * Example:
	 *
	 * <pre>
	 * cond.isLeastNotEmpty(null, null, null)		-&gt; false
	 * cond.isLeastNotEmpty(null, &quot;&quot;, null)	-&gt; fales
	 * cond.isLeastNotEmpty(null, &quot;abc&quot;, &quot;&quot;)		-&gt; true
	 * </pre>
	 *
	 * @param objects <code>Object</code>配列（可変長引数）
	 * @return <code>true</code>:少なくとも１つのオブジェクトが<code>null</code>以外かつ空文字以外
	 */
	public boolean isLeastNotEmpty(Object... objects) {
		return !isAllEmpty(objects);
	}

	/**
	 * ※引数で渡されたbooleanにtrueが含まれているかチェックする<br />
	 * <br />
	 *
	 * Example:
	 *
	 * <pre>
	 * cond.or(false, false, false)		-&gt; false
	 * cond.or(false, false, true)	-&gt; true
	 * cond.or(true, true, true)		-&gt; true
	 * </pre>
	 *
	 * @param booleans <code>boolean</code>配列（可変長引数）
	 * @return <code>true</cod>:引数に<code>true</code>が含まれている場合
	 */
	public boolean or(boolean... booleans) {
		for (boolean b : booleans) {
			if (b) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ※引数で渡されたbooleanが全てtrueかどうかチェックする<br />
	 * <br />
	 *
	 * Example:
	 *
	 * <pre>
	 * cond.and(false, false, false)		-&gt; false
	 * cond.and(true, false, false)	-&gt; false
	 * cond.and(true, true, true)		-&gt; true
	 * </pre>
	 *
	 * @param booleans <code>boolean</code>配列（可変長引数）
	 * @return <code>true</code>:引数の全てが<code>true</code>の場合
	 */
	public boolean and(boolean... booleans) {
		for (boolean b : booleans) {
			if (!b) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ※引数で渡されたオブジェクトが全て同じ値かどうかをチェックする<br />
	 * <br />
	 * Example:
	 *
	 * <pre>
	 * cond.eq(null, null, null)		-&gt; true
	 * cond.eq(null, &quot;&quot;, &quot;&quot;)	-&gt; false
	 * cond.eq(&quot;abc&quot;, &quot;abc&quot;, &quot;abc&quot;)		-&gt; true
	 * </pre>
	 *
	 * @param objects <code>Object</code>配列（可変長引数）
	 * @return <code>true</code>:すべて同じ値
	 */
	public boolean eq(Object... objects) {
		if (objects.length < 2) {
			throw new PoorImplementationException(String.format("Please set the argument from two or more."));
		}
		Object firstObj = objects[0];

		for (Object obj : objects) {
			if (!equals(firstObj, obj)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * オブジェクトを比較する
	 * <p>
	 * ※どちらか一方でも<code>null</code>であれば<code>false</code>を返す
	 * </p>
	 *
	 * <pre>
	 * cond.equals(null, null)			-&gt; true
	 * cond.equals(null, &quot;&quot;)			-&gt; false
	 * cond.equals(&quot;&quot;, null)			-&gt; false
	 * cond.equals(&quot;&quot;, &quot;&quot;)			-&gt; true
	 * cond.equals(Boolean.TRUE, null)		-&gt; false
	 * cond.equals(Boolean.TRUE, &quot;&quot;)		-&gt; false
	 * cond.equals(Boolean.TRUE, Boolean.TRUE)	-&gt; true
	 * cond.equals(Boolean.TRUE, Boolean.FALSE)	-&gt; false
	 * </pre>
	 *
	 * @param obj1 the first object, may be <code>null</code>
	 * @param obj2 the second object, may be <code>null</code>
	 * @return <code>true</code> if the values of both objects are the same
	 */
	public boolean equals(Object obj1, Object obj2) {
		if (obj1 == obj2) {
			return true;
		}
		if ((obj1 == null) || (obj2 == null)) {
			return false;
		}
		return obj1.equals(obj2);
	}

	/**
	 * 日付を比較する
	 * <p>
	 * ※時間部分は考慮されない<br />
	 * <br />
	 * Example:
	 *
	 * <pre>
	 * String yyyymmdd1 -&gt; 2008/01/01
	 * String yyyymmdd2 -&gt; 2008/02/01
	 * cond.compareDate(yyyymmdd1, yyyymmdd2) -&gt; -1
	 * cond.compareDate(yyyymmdd2, yyyymmdd1) -&gt; 1
	 * </pre>
	 *
	 * </p>
	 *
	 * @param yyyymmdd1 比較元日付
	 * @param yyyymmdd2 比較先日付
	 * @return 比較元 < 比較先の場合は -1、元 = 先の場合は 0、元 > 先の場合は 1
	 */
	public int compareDate(String yyyymmdd1, String yyyymmdd2) {
		return DateUtils.compare(DateUtils.parse(yyyymmdd1), DateUtils.parse(yyyymmdd2));
	}

	/**
	 * 基準日付が、2つの日付で表された期間内にあるかを判定する
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * String baseDate -&gt; 20080115
	 * String fromDate -&gt; 20080101
	 * String toDate   -&gt; 20080131
	 * cond.betweenDate(baseDate, fromDate, toDate) -&gt; true
	 * </pre>
	 *
	 * </p>
	 *
	 * @param baseDate 基準日付
	 * @param fromDate 開始日付
	 * @param toDate 終了日付
	 * @return 開始日付 <= 基準日付 <= 終了日付の場合<code>true</code>、それ以外の場合は<code>false</code>
	 */
	public boolean betweenDate(String baseDate, String fromDate, String toDate) {
		return betweenDate(baseDate, fromDate, toDate, Formats.DEFAULT_FORMAT);
	}

	/**
	 * 基準日付が、2つの日付で表された期間内にあるかを判定する
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * String baseDate -&gt; 20080115
	 * String fromDate -&gt; 20080101
	 * String toDate   -&gt; 20080131
	 * cond.betweenDate(baseDate, fromDate, toDate, cond.Formats.DEFAULT_FORMAT) -&gt; true
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
	public boolean betweenDate(String baseDate, String fromDate, String toDate, String fmt) {
		return DateUtils.between(DateUtils.parse(baseDate, fmt), DateUtils.parse(fromDate, fmt), DateUtils.parse(toDate, fmt), fmt);
	}

	/**
	 * <code>BigDecimal</code>の値の比較を行う
	 * <p>
	 * Example:
	 *
	 * <pre>
	 * BigDecimal val1 = BigDecimal.ZERO;
	 * BigDecimal val2 = BigDecimal.valueOf(0.000);
	 *
	 * cond.equalsNum(val1, val2)		-&gt; true
	 * cond.equalsNum(null, null)		-&gt; false
	 * cond.equalsNum(val1, null)		-&gt; false
	 * cond.eqaulsNum(null, val2)		-&gt; false
	 * </pre>
	 *
	 * </p>
	 *
	 * @param val1
	 *            比較対象1つ目の<code>BigDecimal</code>
	 * @param val2
	 *            比較対象2つ目の<code>BigDecimal</code>
	 * @return 等しい場合は<code>true</code>
	 */
	public boolean equalsNum(BigDecimal val1, BigDecimal val2) {
		if (val1 == null || val2 == null) {
			return false;
		}

		return val1.compareTo(val2) == 0;
	}

	/**
	 * @param collection
	 * @return
	 */
	public boolean isEmpty(Collection<?> collection){
		return collection == null || collection.isEmpty();
	}
	
	/**
	 * @param collection
	 * @return
	 */
	public boolean isNotEmpty(Collection<?> collection){
		return ! isEmpty(collection);
	}
}
