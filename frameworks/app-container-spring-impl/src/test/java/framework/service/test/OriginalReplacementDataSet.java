/**
 * Copyright (C) 2010 by Future Architect, Inc. Japan
 * Use is subject to license terms.
 */
package framework.service.test;

import java.util.HashMap;
import java.util.Map;

import org.dbunit.dataset.AbstractDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.ITableMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 独自ReplacementDataSet.
 * 
 * @author S.Hamamoto
 * @version 2010/10/29 新規作成
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class OriginalReplacementDataSet extends AbstractDataSet {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(OriginalReplacementDataSet.class);

	private final IDataSet _dataSet;
	
	private final Map _objectMap;
	private final Map _substringMap;
	private String _startDelim;
	private String _endDelim;
	private boolean _strictReplacement;

	/**
	 * Create a new ReplacementDataSet object that decorates the specified dataset.
	 * 
	 * @param dataSet the decorated table
	 */
	public OriginalReplacementDataSet(IDataSet dataSet) {
		_dataSet = dataSet;
		_objectMap = new HashMap();
		_substringMap = new HashMap();
	}

	/**
	 * Create a new ReplacementDataSet object that decorates the specified dataset.
	 * 
	 * @param dataSet the decorated dataset
	 * @param objectMap the replacement objects mapping
	 * @param substringMap the replacement substrings mapping
	 */
	public OriginalReplacementDataSet(IDataSet dataSet, Map objectMap, Map substringMap) {
		_dataSet = dataSet;
		_objectMap = objectMap == null ? new HashMap() : objectMap;
		_substringMap = substringMap == null ? new HashMap() : substringMap;
	}

	/**
	 * Setting this property to true indicates that when no replacement
	 * is found for a delimited substring the replacement will fail fast.
	 * 
	 * @param strictReplacement true if replacement should be strict
	 */
	public void setStrictReplacement(boolean strictReplacement) {
		this._strictReplacement = strictReplacement;
	}

	/**
	 * Add a new Object replacement mapping.
	 * 
	 * @param originalObject the object to replace
	 * @param replacementObject the replacement object
	 */
	public void addReplacementObject(Object originalObject, Object replacementObject) {
		logger.trace("addReplacementObject(originalObject={}, replacementObject={}) - start", originalObject, replacementObject);

		_objectMap.put(originalObject, replacementObject);
	}

	/**
	 * Add a new substring replacement mapping.
	 * 
	 * @param originalSubstring the substring to replace
	 * @param replacementSubstring the replacement substring
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

	private OriginalReplacementTable createReplacementTable(ITable table) {
		logger.trace("createReplacementTable(table={}) - start", table);

		OriginalReplacementTable replacementTable = new OriginalReplacementTable(table, _objectMap, _substringMap, _startDelim, _endDelim);
		replacementTable.setStrictReplacement(_strictReplacement);
		return replacementTable;
	}

	// //////////////////////////////////////////////////////////////////////////
	// AbstractDataSet class

	protected ITableIterator createIterator(boolean reversed) throws DataSetException {
		if (logger.isDebugEnabled())
			logger.trace("createIterator(reversed={}) - start", String.valueOf(reversed));

		return new ReplacementIterator(reversed ? _dataSet.reverseIterator() : _dataSet.iterator());
	}

	// //////////////////////////////////////////////////////////////////////////
	// IDataSet interface

	public String[] getTableNames() throws DataSetException {
		logger.trace("getTableNames() - start");

		return _dataSet.getTableNames();
	}

	public ITableMetaData getTableMetaData(String tableName) throws DataSetException {
		logger.trace("getTableMetaData(tableName={}) - start", tableName);

		return _dataSet.getTableMetaData(tableName);
	}

	public ITable getTable(String tableName) throws DataSetException {
		logger.trace("getTable(tableName={}) - start", tableName);

		return createReplacementTable(_dataSet.getTable(tableName));
	}

	// //////////////////////////////////////////////////////////////////////////
	// ReplacementIterator class

	private class ReplacementIterator implements ITableIterator {

		/**
		 * Logger for this class
		 */
		private final Logger logger = LoggerFactory.getLogger(ReplacementIterator.class);

		private final ITableIterator _iterator;

		public ReplacementIterator(ITableIterator iterator) {
			_iterator = iterator;
		}

		// //////////////////////////////////////////////////////////////////////
		// ITableIterator interface

		public boolean next() throws DataSetException {
			logger.trace("next() - start");

			return _iterator.next();
		}

		public ITableMetaData getTableMetaData() throws DataSetException {
			logger.trace("getTableMetaData() - start");

			return _iterator.getTableMetaData();
		}

		public ITable getTable() throws DataSetException {
			logger.trace("getTable() - start");

			return createReplacementTable(_iterator.getTable());
		}
	}
}
