/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.criteria.statement;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.coder.gear.query.criteria.Criteria;
import org.coder.gear.query.criteria.FixString;
import org.coder.gear.query.criteria.Operand;
import org.coder.gear.query.criteria.SortKey;
import org.coder.gear.query.criteria.statement.JPQLBuilderFactory;
import org.coder.gear.query.criteria.statement.StatementBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * VelocityTemplateEngineTest.
 *
 * @author yoshida-n
 * @version	1.0
 */
public class JPQLBuilderTest extends Assert{

	/**
	 * load
	 */
	@Test
	public void select() {

		StatementBuilder builder = new JPQLBuilderFactory().createBuilder();
		Criteria criteria = new Criteria("col", 1, Operand.Equal, 1);
		Criteria criteria2 = new Criteria("co2", 1, Operand.Equal, new FixString("COL2"));
		builder.withWhere(Arrays.asList(criteria,criteria2));
		
		SortKey key = new SortKey(true, "SRT1");
		SortKey key2 = new SortKey(false, "SRT2");
		builder.withOrderBy(Arrays.asList(key,key2));		
		
		String actual = builder.buildSelect(this.getClass());
		
		StringBuilder expected = new StringBuilder();
		expected.append("select e from JPQLBuilderTest e ").append("\n");
		expected.append(" where  e.col  =  :col_1 ").append("\n");
		expected.append(" and  e.co2  =  COL2  ").append("\n");
		expected.append(" order by  e.SRT1  asc  ").append("\n");
		expected.append(" ,  e.SRT2  desc   ");
		
		assertEquals(expected.toString(),actual);
	}
	
	/**
	 * delete
	 */
	@Test
	public void delete() {
		StatementBuilder builder = new JPQLBuilderFactory().createBuilder();
		Criteria criteria = new Criteria("col", 1, Operand.GreaterEqual, 1);
		Criteria criteria2 = new Criteria("co2", 1, Operand.GreaterThan, new FixString("COL2"));
		builder.withWhere(Arrays.asList(criteria,criteria2));
		
		String actual = builder.buildDelete(this.getClass());
		
		StringBuilder expected = new StringBuilder();
		expected.append("delete from JPQLBuilderTest e ").append("\n");
		expected.append(" where  e.col  >=  :col_1 ").append("\n");
		expected.append(" and  e.co2  >  COL2  ");
		
		assertEquals(expected.toString(),actual);
	}
	
	/**
	 * update
	 */
	@Test
	public void update() {
		StatementBuilder builder = new JPQLBuilderFactory().createBuilder();
		Criteria criteria = new Criteria("col", 1, Operand.LessEqual, 1);
		Criteria criteria2 = new Criteria("co2", 2, Operand.LessThan, new FixString("COL2"));
		Criteria criteria3 = new Criteria("col3", 3, Operand.IN, Arrays.asList("100,200"));
		Criteria criteria4 = new Criteria("col4", 4, Operand.IsNotNull,null);
		Criteria criteria5 = new Criteria("col5", 5, Operand.IsNull,null);
		Criteria criteria6 = new Criteria("col6", 5, Operand.LIKE,"100");
		Criteria criteria7 = new Criteria("col7", 5, Operand.Between,Arrays.asList("100", "200"));
		builder.withWhere(Arrays.asList(criteria,criteria2,criteria3,criteria4,criteria5,criteria6,criteria7));
		
		Map<String,Object> set = new LinkedHashMap<String,Object>();
		set.put("col", "200");
		set.put("co2", "200");		
		builder.withSet(set);
		
		String actual = builder.buildUpdate(this.getClass());
		
		StringBuilder expected = new StringBuilder();
		expected.append("update JPQLBuilderTest e set ").append("\n");
		expected.append(" e.col = :col").append("\n");
		expected.append(" , e.co2 = :co2 ").append("\n");
		expected.append(" where  e.col  <=  :col_1 ").append("\n");
		expected.append(" and  e.co2  <  COL2 ").append("\n");
		expected.append(" and  e.col3 IN(:col3_3_0) ").append("\n");
		expected.append(" and  e.col4  IS NOT NULL ").append("\n");
		expected.append(" and  e.col5 IS NULL ").append("\n");
		expected.append(" and  e.col6 LIKE :col6_5 ").append("\n");
		expected.append(" and  e.col7  BETWEEN  :col7_5 and :col7_5_to  ");

		assertEquals(expected.toString(),actual);
	}
}
