/**
 * Copyright (C) 2010 by Future Architect, Inc. Japan
 * Use is subject to license terms.
 */
package framework.service.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 独自ReplacementTable.
 * 
 * @author S.Hamamoto
 * @version 2010/10/29 新規作成
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class OriginalReplacementTable implements ITable {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(OriginalReplacementTable.class);

	private final ITable _table;
	private final Map _objectMap;
	private final Map _substringMap;
	private String _startDelim;
	private String _endDelim;
	private boolean _strictReplacement;

	/**
	 * Create a new ReplacementTable object that decorates the specified table.
	 * 
	 * @param table
	 *            the decorated table
	 */
	public OriginalReplacementTable(ITable table) {
		this(table, new HashMap(), new HashMap(), null, null);
	}

	public OriginalReplacementTable(ITable table, Map objectMap, Map substringMap, String startDelimiter, String endDelimiter) {
		_table = table;
		_objectMap = objectMap;
		_substringMap = substringMap;
		_startDelim = startDelimiter;
		_endDelim = endDelimiter;
	}

	/**
	 * Setting this property to true indicates that when no replacement is found
	 * for a delimited substring the replacement will fail fast.
	 * 
	 * @param strictReplacement
	 *            true if replacement should be strict
	 */
	public void setStrictReplacement(boolean strictReplacement) {
		if (logger.isDebugEnabled())
			logger.trace("setStrictReplacement(strictReplacement={}) - start", String.valueOf(strictReplacement));

		this._strictReplacement = strictReplacement;
	}

	/**
	 * Add a new Object replacement mapping.
	 * 
	 * @param originalObject
	 *            the object to replace
	 * @param replacementObject
	 *            the replacement object
	 */
	public void addReplacementObject(Object originalObject, Object replacementObject) {
		logger.trace("addReplacementObject(originalObject={}, replacementObject={}) - start", originalObject, replacementObject);

		_objectMap.put(originalObject, replacementObject);
	}

	/**
	 * Add a new substring replacement mapping.
	 * 
	 * @param originalSubstring
	 *            the substring to replace
	 * @param replacementSubstring
	 *            the replacement substring
	 */
	public void addReplacementSubstring(String originalSubstring, String replacementSubstring) {
		logger.trace("addReplacementSubstring(originalSubstring={}, replacementSubstring={}) - start", originalSubstring, replacementSubstring);

		if (originalSubstring == null || replacementSubstring == null) {
			throw new NullPointerException();
		}

		_substringMap.put(originalSubstring, replacementSubstring);
	}

	/**
	 * Sets substring delimiters.
	 */
	public void setSubstringDelimiters(String startDelimiter, String endDelimiter) {
		logger.trace("setSubstringDelimiters(startDelimiter={}, endDelimiter={}) - start", startDelimiter, endDelimiter);

		if (startDelimiter == null || endDelimiter == null) {
			throw new NullPointerException();
		}

		_startDelim = startDelimiter;
		_endDelim = endDelimiter;
	}

	/**
	 * Replace occurrences of source in text with target. Operates directly on
	 * text.
	 */
	private void replaceAll(StringBuffer text, String source, String target) {
		int index = 0;
		while ((index = text.toString().indexOf(source, index)) != -1) {
			text.replace(index, index + source.length(), target);
			index += target.length();
		}
	}

	private String replaceStrings(String value, String lDelim, String rDelim) {
		StringBuffer buffer = new StringBuffer(value);

		for (Iterator it = _substringMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			String original = (String) entry.getKey();
			String replacement = (String) entry.getValue();
			replaceAll(buffer, lDelim + original + rDelim, replacement);
		}

		return buffer == null ? value : buffer.toString();
	}

	private String replaceSubstrings(String value) {
		return replaceStrings(value, "", "");
	}

	/**
	 * @throws DataSetException
	 *             when stringReplacement fails
	 */
	private String replaceDelimitedSubstrings(String value) throws DataSetException {
		StringBuffer buffer = null;

		int startIndex = 0;
		int endIndex = 0;
		int lastEndIndex = 0;
		for (;;) {
			startIndex = value.indexOf(_startDelim, lastEndIndex);
			if (startIndex != -1) {
				endIndex = value.indexOf(_endDelim, startIndex + _startDelim.length());
				if (endIndex != -1) {
					if (buffer == null) {
						buffer = new StringBuffer();
					}

					String substring = value.substring(startIndex + _startDelim.length(), endIndex);
					if (_substringMap.containsKey(substring)) {
						buffer.append(value.substring(lastEndIndex, startIndex));
						buffer.append(_substringMap.get(substring));
					} else if (_strictReplacement) {
						throw new DataSetException("Strict Replacement was set to true, but no" + " replacement was found for substring '" + substring + "' in the value '" + value + "'");
					} else {
						logger.trace("Did not find a replacement map entry for substring={}. " + "Leaving original value there.", substring);
						buffer.append(value.substring(lastEndIndex, endIndex + _endDelim.length()));
					}

					lastEndIndex = endIndex + _endDelim.length();
				}
			}

			// No more delimited substring
			if (startIndex == -1 || endIndex == -1) {
				if (buffer != null) {
					buffer.append(value.substring(lastEndIndex));
				}
				break;
			}
		}

		return buffer == null ? value : buffer.toString();
	}

	// //////////////////////////////////////////////////////////////////////
	// ITable interface

	public ITableMetaData getTableMetaData() {
		return _table.getTableMetaData();
	}

	public int getRowCount() {
		return _table.getRowCount();
	}

	public Object getValue(int row, String column) throws DataSetException {
		if (logger.isDebugEnabled())
			logger.trace("getValue(row={}, columnName={}) - start", Integer.toString(row), column);

		Object value = _table.getValue(row, column);

		if (value instanceof String) {
			String strValue = (String) value;
			if (!StringUtils.isEmpty(strValue)) {
				Object[] keys = _objectMap.keySet().toArray();
				for (Object key : keys) {
					strValue = StringUtils.replace(strValue, key.toString(), (String) _objectMap.get(key));
				}
				if (!strValue.equals(value)) {
					return strValue;
				}
			}
		}
		// Object replacement
		if (_objectMap.containsKey(value)) {
			return _objectMap.get(value);
		}

		// Stop here if substring replacement not applicable
		if (_substringMap.size() == 0 || !(value instanceof String)) {
			return value;
		}

		// Substring replacement
		if (_startDelim != null && _endDelim != null) {
			return replaceDelimitedSubstrings((String) value);
		}
		return replaceSubstrings((String) value);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getClass().getName()).append("[");
		sb.append("_strictReplacement=").append(_strictReplacement);
		sb.append(", _table=").append(_table);
		sb.append(", _objectMap=").append(_objectMap);
		sb.append(", _substringMap=").append(_substringMap);
		sb.append(", _startDelim=").append(_startDelim);
		sb.append(", _endDelim=").append(_endDelim);
		sb.append("]");
		return sb.toString();
	}
}
