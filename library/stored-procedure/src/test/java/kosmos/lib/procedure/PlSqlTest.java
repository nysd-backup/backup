/**
 * Copyright 2011 the original author
 */
package kosmos.lib.procedure;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import kosmos.lib.procedure.CallRequest.PlSqlDataType;
import kosmos.lib.procedure.impl.PLSQLServiceImpl;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class PlSqlTest extends Assert{
	
	private static Connection con = null;
	
	@BeforeClass
	public static void initClass() throws ClassNotFoundException, SQLException{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","yoshida","yoshida");		
	}
	
	
	@AfterClass
	public static void termClass() throws SQLException {
		if(con != null){
			con.close();
		}
	}
	
	/**
	 * INパラメータ
	 */
	@Test
	public void addIn(){
		
		CallRequest parameter = new CallRequest("pkg_online_com.sp_start_online");
		parameter.add("1").add("2").add("3").add("4").add(new Date());
		CallableService service = new PLSQLServiceImpl();
		service.execute(parameter, con);
	}
	
	/**
	 * OUTパラメータ
	 */
	@Test
	public void addOut(){
		
		CallRequest parameter = new CallRequest("pkg_online_com.sp_end_online");
		parameter.addOut("one", PlSqlDataType.Char).addOut("two", PlSqlDataType.Varchar).addOut("three",PlSqlDataType.Number).addOut("four", PlSqlDataType.Varchar).addOut("five", PlSqlDataType.Number);
		CallableService service = new PLSQLServiceImpl();
		CallResult result = service.execute(parameter, con);
		String v1 = result.getString("one");
		assertNull(v1);
		String v2 = result.getString("two");
		assertNull(v2);
		BigDecimal v3 = result.getBigDecimal("three");
		assertNull(v3);
		String v4 = result.getString("four");
		assertNull(v4);
		Long v5 = result.getLong("five");
		assertEquals("0",v5.toString());
	}

}
