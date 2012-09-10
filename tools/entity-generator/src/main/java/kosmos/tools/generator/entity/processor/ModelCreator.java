/**
 * Copyright 2011 the original author
 */
package kosmos.tools.generator.entity.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kosmos.tools.generator.entity.model.Column;
import kosmos.tools.generator.entity.model.Table;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public abstract class ModelCreator {

	/**
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	public static List<Table> createModel(String user , String pass , String url,String sqlName , String versionColName) throws Exception {
		
		StringBuilder sql = new StringBuilder();
		try {
			InputStream stream = ModelCreator.class.getResourceAsStream("/META-INF/" + sqlName);
			BufferedReader reader = new BufferedReader( new InputStreamReader(stream));		
			String line = null;			
			while((line=reader.readLine()) != null){
				sql.append(line).append("\r\n");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	
		List<Table> tables = new ArrayList<Table>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{	
			
			//SQL実行してメタデータ取得
			con = DriverManager.getConnection(url, user, pass);
			stmt = con.prepareStatement(sql.toString());
			stmt.setString(1, versionColName);
			rs = stmt.executeQuery();
			Map<String, List<Map<String, Object>>> value = new LinkedHashMap<String,List<Map<String,Object>>>();
			while(rs.next()){
				String tableName = rs.getString("TABLE_NAME");				
				String tableComments = rs.getString("TABLE_COMMENTS");
				String columnName = rs.getString("COLUMN_NAME");
				String columnComemnts = rs.getString("COLUMN_COMMENTS");
				boolean isPk = "true".equals(rs.getString("ISPK"));
				Class<?> javaType = Class.forName(rs.getString("JAVA_TYPE"));
				Map<String,Object> columns = new LinkedHashMap<String,Object>();
				columns.put("TABLE_COMMENTS", tableComments);
				columns.put("COLUMN_NAME", columnName);
				columns.put("COLUMN_COMMENTS", columnComemnts);
				columns.put("ISPK", isPk);
				columns.put("JAVA_TYPE", javaType);
				columns.put("ISVERSION", "true".equals(rs.getString("ISVERSION")));
				if(value.containsKey(tableName)){
					List<Map<String,Object>> columnList = value.get(tableName);
					columnList.add(columns);
				}else{
					List<Map<String,Object>> columnList = new ArrayList<Map<String,Object>>();
					columnList.add(columns);
					value.put(tableName,columnList);
				}
			}
			
			//論理名取得用正規表現
			Pattern pattern = Pattern.compile("\\[(.+)\\].+");
			
			for(Map.Entry<String, List<Map<String, Object>>> e : value.entrySet()){
				Table table = new Table();
				table.physicalName = e.getKey();
				for(Map<String, Object> ee : e.getValue()){
					table.logicalName = (String)ee.get("TABLE_COMMENTS");
					Column col = new Column();
					col.physicalName = (String)ee.get("COLUMN_NAME");					
					col.logicalName = (String)ee.get("COLUMN_COMMENTS");
					if(col.logicalName != null){
						Matcher mt = pattern.matcher(col.logicalName);
						col.logicalName = mt.find() ? mt.group(1) : col.logicalName;
					}
					col.primaryKey = (Boolean)ee.get("ISPK");
					col.typeName = (Class<?>)ee.get("JAVA_TYPE");
					col.versionManaged = (Boolean)ee.get("ISVERSION");
					table.columns.add(col);
				}
				if(table.logicalName != null){
					Matcher mt = pattern.matcher(table.logicalName);
					table.logicalName = mt.find() ? mt.group(1) : table.logicalName;
				}
				tables.add(table);
			}
			return tables;
			
		}finally {
			try{			
				if(rs != null){
					rs.close();
				}
				if (stmt != null){
					stmt.close();
				}
			}finally {
				if( con != null){
					con.close();
				}
			}
		}
	}
}
