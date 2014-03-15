/**
 * Copyright 2011 the original author, All Rights Reserved.
 */
package org.coder.gear.query.criteria.statement;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.coder.gear.query.criteria.Criteria;
import org.coder.gear.query.criteria.ListHolder;
import org.coder.gear.query.criteria.Operand;
import org.coder.gear.query.criteria.SortKey;
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

		StatementBuilder builder = new JPQLBuilder();
		ListHolder<Criteria> cs = new ListHolder<Criteria>();
		cs.add( new Criteria("col", Operand.Equal, 1));
		cs.add(new Criteria("co2", Operand.Equal,2));
		builder.withWhere(cs);

		ListHolder<SortKey> ss = new ListHolder<SortKey>();
		ss.add(new SortKey(true, "SRT1"));
		ss.add(new SortKey(false, "SRT2"));
		builder.withOrderBy(ss);		

		String actual = builder.buildSelect(this.getClass());

		StringBuilder expected = new StringBuilder();
		expected.append("select e from JPQLBuilderTest e ");
		expected.append(" where e.col  =  :col_0 ");
		expected.append(" and  e.co2  =  :co2_1  ");
		expected.append(" order by  e.SRT1  asc  ");
		expected.append(" , e.SRT2  desc  ");

		assertEquals(expected.toString(),actual);
	}

	/**
	 * delete
	 */
	@Test
	public void delete() {
		StatementBuilder builder = new JPQLBuilder();
		ListHolder<Criteria> cs = new ListHolder<Criteria>();
		cs.add(new Criteria("col",  Operand.GreaterEqual, 1));
		cs.add(new Criteria("co2",  Operand.GreaterThan, 2));
		builder.withWhere(cs);

		String actual = builder.buildDelete(this.getClass());

		StringBuilder expected = new StringBuilder();
		expected.append("delete from JPQLBuilderTest e ");
		expected.append(" where e.col  >=  :col_0 ");
		expected.append(" and  e.co2  >  :co2_1  ");

		assertEquals(expected.toString(),actual);
	}

	/**
	 * update
	 */
	@Test
	public void update() {
		StatementBuilder builder = new JPQLBuilder();
		ListHolder<Criteria> cs = new ListHolder<Criteria>();
		cs.add(new Criteria("col",  Operand.LessEqual, 1));
		cs.add(new Criteria("co2", Operand.LessThan, 2));
		cs.add( new Criteria("col3", Operand.IN, Arrays.asList("100,200")));
		cs.add(new Criteria("col4", Operand.IsNotNull,null));
		cs.add(new Criteria("col5",  Operand.IsNull,null));
		cs.add(new Criteria("col6",  Operand.LIKE,"100"));
		cs.add(new Criteria("col7",  Operand.Between,Arrays.asList("100", "200")));
				
		builder.withWhere(cs);

		Map<String,Object> set = new LinkedHashMap<String,Object>();
		set.put("col", "200");
		set.put("co2", "200");		
		builder.withSet(set);

		String actual = builder.buildUpdate(this.getClass());

		StringBuilder expected = new StringBuilder();
		expected.append("update JPQLBuilderTest e set ");
		expected.append(" e.col = :col");
		expected.append(" , e.co2 = :co2 ");
		expected.append(" where e.col  <=  :col_0 ");
		expected.append(" and  e.co2  <  :co2_1 ");
		expected.append(" and  e.col3 IN(:col3_2_0) ");
		expected.append(" and  e.col4  IS NOT NULL ");
		expected.append(" and  e.col5 IS NULL ");
		expected.append(" and  e.col6 LIKE :col6_5 ");
		expected.append(" and  e.col7  BETWEEN  :col7_6 and :col7_6_to  ");

		assertEquals(expected.toString(),actual);
	}
}