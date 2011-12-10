/**
 * Copyright 2011 the original author
 */
package kosmos.lib.procedure;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import kosmos.lib.procedure.CallRequest.Bind;
import kosmos.lib.procedure.CallRequest.PlSqlDataType;

import org.apache.commons.lang.StringUtils;

/**
 * A result of stored procedure.
 * 
 * @author yoshida-n
 * @version 2010/10/29 新規作成
 */
public class CallResult extends HashMap<String, Bind> {

	/** serialVersionUID */
	private static final long serialVersionUID = 5902482027944109079L;

	/**
	 * PlSqlDataType.Varchar　-> String.
	 * PlSqlDataType.Charの  -> String (with trim).
	 * 
	 * @param key the key
	 * @return the value
	 */
	public String getString(String key) {
		Bind v = get(key);
		if (v == null || v.value == null) {
			return null;
		}
		if (PlSqlDataType.Char == v.type) {
			return StringUtils.trim(String.valueOf(v.value));
		} else if (PlSqlDataType.Varchar == v.type) {
			return String.valueOf(v.value);
		} else {
			throw new IllegalArgumentException("This method only support in data type 'Varchar' or 'Char'");
		}
	}

	/**
	 * PlSqlDataType.Number -> Long.
	 * 
	 * @param key the key
	 * @return the value
	 */
	public Long getLong(String key) {
		Bind v = get(key);
		if (v == null || v.value == null) {
			return null;
		}
		if (PlSqlDataType.Number == v.type) {
			return BigDecimal.class.cast(v.value).longValue();
		} else {
			throw new IllegalArgumentException("This method only support in data type 'Number'");
		}
	}

	/**
	 * PlSqlDataType.Number -> BigDecimal.
	 * 
	 * @param key the key
	 * @return the value
	 */
	public BigDecimal getBigDecimal(String key) {
		Bind v = get(key);
		if (v == null || v.value == null) {
			return null;
		}
		if (PlSqlDataType.Number == v.type) {
			return BigDecimal.class.cast(v.value);
		} else {
			throw new IllegalArgumentException("This method only support in data type 'Number'");
		}
	}

	/**
	 * PlSqlDataType.Date -> Date.
	 * 
	 * @param key the key
	 * @return the value
	 */
	public Date getDate(String key) {
		Bind v = get(key);
		if (v == null || v.value == null) {
			return null;
		}
		if (PlSqlDataType.Date == v.type) {
			return new Date(Timestamp.class.cast(v.value).getTime());
		} else {
			throw new IllegalArgumentException("This method only support in data type 'Date'");
		}
	}

	/**
	 * PlSqlDataType.VarcharArray -> String[].
	 * 
	 * @param key the key
	 * @return the value
	 */
	public String[] getStringArray(String key) {
		Bind v = get(key);
		if (v == null || v.value == null) {
			return null;
		}
		if (PlSqlDataType.VarcharArray == v.type) {
			return (String[]) v.value;
		} else {
			throw new IllegalArgumentException("This method only support in data type 'VarcharArray'");
		}
	}

	/**
	 * PlSqlDataType.NumberArrayの -> BigDecimal[].
	 * 
	 * @param key the key
	 * @return the value
	 */
	public BigDecimal[] getBigDecimalArray(String key) {
		Bind v = get(key);
		if (v == null || v.value == null) {
			return null;
		}
		if (PlSqlDataType.NumberArray == v.type) {
			return (BigDecimal[]) v.value;
		} else {
			throw new IllegalArgumentException("This method only support in data type 'NumberArray'");
		}
	}
}
