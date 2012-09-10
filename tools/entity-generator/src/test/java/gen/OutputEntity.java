/**
 * Copyright 2011 the original author
 */
package gen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import kosmos.tools.generator.entity.processor.Facade;

import org.junit.Test;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class OutputEntity {

	String sql = "oracleSql.sql";
	String user = "yoshida";
	String pass = "yoshida";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	
	static{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Oracle用出力
	 */
	@Test
	public void outputOracle() throws Exception{
		new Facade().output(user, pass, url, sql, "VERSION","generated.");
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void checkType() throws Exception {
		
		Connection con = null;
		Statement stmt = null;
			
		try{
			con = 	DriverManager.getConnection(url, user,pass);;
			stmt =con.createStatement(); 
			try{
				stmt.execute("drop table type_test");
			}catch(Exception e){
				
			}
			String sql = "create table type_test (";
			sql = sql + " one number(1,0)";
			sql = sql + ",two number(4,2)";
			sql = sql + ",three number(22,0)";
			sql = sql + ",four blob";
			sql = sql + ")";
			stmt.execute(sql);
			ResultSet rs = stmt.executeQuery("select * from type_test");
			ResultSetMetaData meta = rs.getMetaData();
			int c = meta.getColumnCount();
			for(int i = 0; i < c; i++){
				System.out.println("class:" + meta.getColumnClassName(i+1));
				System.out.println("name:" + meta.getColumnName(i+1));
			}
		}finally{
			if(stmt != null) stmt.close();
			if( con != null) con.close();
		}
	}
	
	
}
