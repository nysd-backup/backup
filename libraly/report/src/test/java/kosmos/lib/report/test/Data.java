package kosmos.lib.report.test;

import java.math.BigDecimal;
import java.util.Date;


public class Data {
	private String value1 = "00109";
	
	private BigDecimal value2 = new BigDecimal(-19538.119222);
	
	private Date value3 = new Date();
	
	public String getValue1(){
		return value1;
	}
	
	public BigDecimal getValue2(){
		return value2;
	}
	
	public Date getValue3(){
		return value3;
	}
}