/**
 * Copyright 2011 the original author
 */
package alpha.utility;

import java.util.Calendar;

/**
 * Constant.
 *
 * @author yoshida-n
 * @version 2011/08/31	created.
 */
public interface Scopes {

	/** for StringUtils */
	interface StringScope extends Scopes {
		/** カラム名の区切り文字 */
		String COLUMN_DELIM = "_";
		/** パディング時の限界値 */
		int PAD_LIMIT = 8192;

		/** エンコーディング */
		interface Encodings {
			/** エンコーディング：JISAutoDetect */
			String JIS_AUTO_DETECT = "JISAutoDetect";
			/** エンコーディング：Windows-31J */
			String WINDOWS_31J = "Windows-31J";
			/** エンコーディング：ISO-8859-1 */
			String ISO_8859_1 = "ISO-8859-1";
			/** エンコーディング：Shift_JIS */
			String SHIFT_JIS = "Shift_JIS";
			/** エンコーディング：8859_1 */
			String E8859_1 = "8859_1";
			/** エンコーディング：UTF-81（デフォルトエンコーディング） */
			String UTF_8 = "UTF-8";
		}

		/** カナ文字変換用テーブル */
		interface Chars {
			String KATAKANA = "アイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホマミムメモヤユヨラリルレロワヲンァィゥェォッャュョー";
			String HANKAKU_KATAKANA = "ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜｦﾝｧｨｩｪｫｯｬｭｮｰ";
			String DAKUTEN = "ガギグゲゴザジズゼゾダヂヅデドバビブベボヴ";
			String HANKAKU_DAKUTEN = "ｶﾞｷﾞｸﾞｹﾞｺﾞｻﾞｼﾞｽﾞｾﾞｿﾞﾀﾞﾁﾞﾂﾞﾃﾞﾄﾞﾊﾞﾋﾞﾌﾞﾍﾞﾎﾞｳﾞ";
			String HAN_DAKUTEN = "パピプペポ";
			String HANKAKU_HAN_DAKUTEN = "ﾊﾟﾋﾟﾌﾟﾍﾟﾎﾟ";
		}

		/** Regexpのフォーマット */
		interface Formats {
			/** HTMLのフォーマット */
			String HTML = "(http://|https://){1}[\\w\\.\\-/:]+";
			/** 変換用のフォーマット */
			String HTML_FOR_URL = "<a href=\"#\" onClick=\"window.open('$0')\">$0</a>";
			/** 半角カタカナを表す(範囲チェック用) */
			String NAROW_KATAKANA = "^[･-ﾟ]+$";
			/** 全角カタカナを表す(範囲チェック用) */
			String WIDE_KATAKANA = "^[ァ-ー]+$";
			/** 全角英数字のフォーマット */
			String WIDE_ALPHA_NUM = "[０-９Ａ-Ｚａ-ｚ]+";
			/** 英数字のフォーマット */
			String ALPHA_NUM = "[０-９Ａ-Ｚａ-ｚ0-9A-Za-z]+";
			/** 半角英数カナのフォーマット */
			String NARROW_ALPHA_NUM_KANA = "[A-Za-z0-9｡-ﾟ]+";
			/** 半角数値のフォーマット */
			String NUMBER = "^-?([1-9][0-9]*)?[0-9](\\.[0-9]*)?$";
			/** 全角数値のフォーマット */
			String WIDE_NUMBER = "[０-９]+";
		}

		/** StringValidatorのcharType */
		interface CharTypeFormats {
			/** 文字種別：全角英数 */
			String WIDE_ALPHA_NUMERIC = "1";
			/** 文字種別：半角英数 */
			String NARROW_ALPHA_NUMERIC = "2";
			/** 文字種別：英数 */
			String ALPHA_NUMERIC = "3";
			/** 文字種別：半角英数カタカナ */
			String NARROW_ALPHA_NUMERIC_KATAKANA = "4";
			/** 文字種別：全角数字 */
			String WIDE_NUMERIC = "5";
			/** 文字種別：半角数字 */
			String NARROW_NUMERIC = "6";
			/** 文字種別：全角カタカナ */
			String WIDE_KATAKANA = "7";
			/** 文字種別：半角カタカナ */
			String NARROW_KATAKANA = "8";
			/** 文字種別：全角 */
			String WIDE = "9";
			/** 文字種別：半角 */
			String NARROW = "10";
			/** 文字種別：数値 */
			String NUMBER = "11";
			/** 文字種別：全半角 */
			String WIDE_NARROW = "12";
			/** 文字種別：文字種別無 */
			String CHARTYPE_NONE = "13";
		}
	}

	/** for ObjectUtils */
	interface ObjectScope extends Scopes {
		/** 行セパレータ */
		String LINE_SEPARATER = "----------------------------------------";

		/** フォーマッター(KEY = VALUE) */
		String FORMAT_KEY_VALUE = "%s%s = %s";

		/** フォーマッター( field( classtype )) */
		String FORMAT_CLASS_TYPE = "%s%s (%s)";

		/** フォーマッター(VALUE) */
		String FORMAT_VALUE = "%s%s";

		/** フォーマッター(VALUE) */
		String FORMAT_COLLECTION_VALUE = "%s* [%s]";

		/** フォーマッター(VALUE) */
		String FORMAT_COLLECTION_SIZE = "%s<<  Size : %s >>";

		/** フォーマッター(VALUE) */
		String FORMAT_COLLECTION_NULL = "%s<< NULL >>";

		/** エラー発生時のダンプ文字列 */
		String ERROR_DUMP = "----- DUMP ERROR -----";

		/** インデント文字列 */
		String INDENT = "    ";

		/** インデント文字列(コレクション値) */
		String INDENT_LISTVALUE = "  ";

		/** prefix */
		String PREFIX = " ";

		/** フィールド名prefix */
		String PREFIX_FIELD = "+ ";

		/** NULL文字列 */
		String NULL_STRING = "NULL";

		/** 表示最大階層数 */
		int TREE_LIMIT = 5;
	}

	/** for DateUtils */
	interface DateScope extends Scopes {
		/** 固定日付 */
		int DATE_FIX_DATE = 25;

		int MODIFY_ROUND = 1;

		interface Fields {
			/** 年を示すフィールド値 */
			int YEAR = Calendar.YEAR;
			/** 月を示すフィールド値 */
			int MONTH = Calendar.MONTH;
			/** 月の日を示すフィールド値 */
			int DATE = Calendar.DATE;
		}

		interface Formats {
			/** 年フォーマット */
			String YEAR_FORMAT = "yyyy";

			/** yy形式 */
			String SHORT_YEAR_FORMAT = "yy";

			/** 月フォーマット */
			String MONTH_FORMAT = "MM";

			/** 日フォーマット */
			String DATE_FORMAT = "dd";

			/** 年月フォーマット */
			String YEAR_MONTH_FORMAT = "yyyyMM";

			/** 年月フォーマット（スラッシュ有） */
			String YEAR_SLA_MONTH_FORMAT = "yyyy/MM";

			/** MMdd形式 */
			String MONTH_DATE_FORMAT = "MMdd";

			/** MM/dd形式 */
			String MONTH_SLA_DATE_FORMAT = "MM/dd";

			/** MM/dd HH:mm形式 */
			String MONTH_SLA_DATE_HOUR_COLON_MIN_FORMAT = "MM/dd HH:mm";

			/** MM/dd(E)形式 */
			String MONTH_SLA_DATE_WEEK_FORMAT = "MM/dd(E)";

			/** 年月日フォーマット（スラッシュ有） yyyy/MM/dd形式 */
			String YEAR_SLA_MONTH_SLA_DATE_FORMAT = "yyyy/MM/dd";

			/** 年月日(曜日)フォーマット（スラッシュ有） yyyy/MM/dd(E)形式 */
			String YEAR_SLA_MONTH_SLA_DATE_WEEK_FORMAT = "yyyy/MM/dd(E)";

			/** 標準で使用する日付のフォーマット。yyyyMMdd形式 */
			String DEFAULT_FORMAT = "yyyyMMdd";

			/** yyMMdd形式 */
			String SHORT_DATE_FORMAT = "yyMMdd";

			/** yy/MM/dd形式 */
			String SHORT_SLA_DATE_FORMAT = "yy/MM/dd";

			/** 日付＋時刻の形式 */
			String DATETIME_FORMAT = "yyyy/MM/dd HH:mm";

			/** 日付＋時刻の形式 */
			String DATETIME_SEC_FORMAT = "yyyy/MM/dd HH:mm:ss";

			/** 時刻の形式 */
			String TIME_FORMAT = "HHmmss";

			/** HH:mm:ss形式 */
			String TIME_COLON_FORMAT = "HH:mm:ss";

			/** HHmm形式 */
			String HOUR_MIN_FORMAT = "HHmm";

			/** HH:mm形式 */
			String HOUR_COLON_MIN_FORMAT = "HH:mm";

			/** yyyyMMddHHmmssSSS形式 */
			String DATETIME_LONG_SECOND_FORMAT = "yyyyMMddHHmmssSSS";

			/** yyyyMMddHHmmss形式 */
			String DATETIME_SHORT_SECOND_FORMAT = "yyyyMMddHHmmss";

			/** yy/MM */
			String SHORT_SLA_YEAR_MONTH_FORMAT = "yy/MM";
		}
	}

	/** for BigDecimalScope */
	interface BigDecimalScope extends Scopes {
		interface Formats {
			/** 数値(カンマ無し) */
			String NUMBER_NO_COMMA = "1";
			/** 数値(カンマ有り) */
			String NUMBER = "2";
			/** 数値(小数) */
			String NUMBER_POINT = "3";
		}
	}

	interface SpecialScope extends Scopes {
		enum Formats {
			/** 郵便番号 */
			PostalCode,
			/** 電話番号 */
			TelNumber,
			/** メールアドレス */
			MailAddress;
		}
	}
}
