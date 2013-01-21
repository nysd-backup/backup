/**
 * Copyright 2011 the original author
 */
package alpha.query.criteria;

/**
 * The key to sort.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class SortKey {

	/** sorting direction */
	private final boolean ascending;
	
	/** the column to sort */
	private final String column;
	
	/**
	 * @param ascending sorting direction
	 * @param column the column
	 */
	public SortKey(boolean ascending , String column ){
		this.ascending = ascending;
		this.column = column;
	}

	/**
	 * @return the column
	 */
	public String getColumn() {
		return column;
	}

	/**
	 * @return the ascending
	 */
	public boolean isAscending() {
		return ascending;
	}

}
