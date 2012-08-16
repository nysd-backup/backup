/**
 * Copyright 2011 the original author
 */
package sqlengine.executer.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sqlengine.executer.Selector;



/**
 * The query engine.
 * Retry the call if needed.
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
public class SelectorImpl implements Selector{


	/**
	 * @see sqlengine.executer.Selector#select(java.sql.PreparedStatement, int)
	 */
	@Override
	public ResultSet select(PreparedStatement stmt,int startPosition) throws SQLException {
		ResultSet rs =  stmt.executeQuery();
		if(startPosition <= 0){
			return rs;
		}
		if(rs.getType() >= ResultSet.TYPE_SCROLL_INSENSITIVE ){
			rs.absolute(startPosition);
		}else{
			for(int i=0; i < startPosition;i++){
				rs.next();
			}
		}		
		return rs;
	}

}
