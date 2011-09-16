/**
 * 
 */
package framework.sqlclient.api.orm;

/**
 * ソート用キー.
 *
 * @author yoshida-n
 * @version	2011/05/08 created.
 */
public class SortKey {

	/** true:昇順 */
	private final boolean ascending;
	
	/** カラム名 */
	private final String column;
	
	/**
	 * @param ascending true:昇順
	 * @param column カラム
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
