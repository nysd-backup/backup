package kosmos.lib.report.test;

import java.math.BigDecimal;
import java.util.Date;


public class Data {
	private String value1 = "00109";
	
	private BigDecimal value2 = new BigDecimal("-19538.119222");
	
	private Date value3 = new Date();
	

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public void setValue2(BigDecimal value2) {
		this.value2 = value2;
	}

	public void setValue3(Date value3) {
		this.value3 = value3;
	}

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