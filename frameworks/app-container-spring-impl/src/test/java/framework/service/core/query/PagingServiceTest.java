/**
 * Copyright 2011 the original author
 */
package framework.service.core.query;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import framework.api.query.services.PagingRequest;
import framework.api.query.services.PagingResult;
import framework.api.query.services.PagingService;
import framework.api.query.services.QueryRequest;
import framework.service.core.persistence.EntityManagerAccessor;
import framework.service.test.SampleNativeQuery;
import framework.service.test.ServiceUnit;
import framework.service.test.entity.TestEntity;

/**
 * 繝壹・繧ｸ繝ｳ繧ｰ繧ｵ繝ｼ繝薙せ縺ｮ繝・せ繝・
 *
 * @author yoshida-n
 * @version 2011/08/31 created.
 */
@ContextConfiguration(locations = "/META-INF/context/oracleAgentApplicationContext.xml")
public class PagingServiceTest extends ServiceUnit{

	@Resource
	private PagingService pagingService;
	
	@Autowired
	private EntityManagerAccessor per;
	
	private void dataset(){
		setUpData("TEST.xls");
		TestEntity e = new TestEntity();
		e.setTest("111").setAttr("1").setAttr2(100);
		per.persist(e);

		e = new TestEntity();
		e.setTest("112").setAttr("1").setAttr2(100);
		per.persist(e);
		
		e = new TestEntity();
		e.setTest("113").setAttr("1").setAttr2(100);
		per.persist(e);
		
		e = new TestEntity();
		e.setTest("114").setAttr("1").setAttr2(100);
		per.persist(e);
		
		per.flush();
	}

	
	/**
	 * 1繝壹・繧ｸ5莉ｶ蠅・阜蜿門ｾ・
	 */
	@Test
	public void onePageJust(){
		
		dataset();
		
		PagingRequest request = new PagingRequest();
		QueryRequest q = new QueryRequest(SampleNativeQuery.class);
		request.setInternal(q);
		request.setPageSize(6);
		PagingResult result = pagingService.prepare(request);
		assertEquals(6,result.getTotalCount());
		assertEquals(1,result.getCurrentPageNo());
		assertEquals(6,result.getCurrentPageData().size());
		List<TestEntity> en = result.getCurrentPageData();
		assertEquals("2",en.get(0).getTest());
		assertEquals("114",en.get(1).getTest());
		assertEquals("113",en.get(2).getTest());
		assertEquals("112",en.get(3).getTest());
		assertEquals("111",en.get(4).getTest());
		assertEquals("1",en.get(5).getTest());
	
	}
	

	/**
	 * 3繝壹・繧ｸ2莉ｶ蜿門ｾ・
	 */
	@Test
	public void threePagejust(){
		
		dataset();
		
		PagingRequest request = new PagingRequest();
		QueryRequest q = new QueryRequest(SampleNativeQuery.class);
		request.setInternal(q);
		request.setPageSize(2);
		PagingResult result = pagingService.prepare(request);
		assertEquals(6,result.getTotalCount());
		assertEquals(2,result.getCurrentPageData().size());
		assertEquals(1,result.getCurrentPageNo());
		List<TestEntity> en = result.getCurrentPageData();
		assertEquals("2",en.get(0).getTest());
		assertEquals("114",en.get(1).getTest());
		
		
		request.setStartPosition(2);
		request.setTotalCount(6);
		result = pagingService.getPageData(request);
		assertEquals(6,result.getTotalCount());
		assertEquals(2,result.getCurrentPageData().size());
		assertEquals(2,result.getCurrentPageNo());
		en = result.getCurrentPageData();
		assertEquals("113",en.get(0).getTest());
		assertEquals("112",en.get(1).getTest());
		
		request.setStartPosition(4);		
		result = pagingService.getPageData(request);
		assertEquals(6,result.getTotalCount());
		assertEquals(2,result.getCurrentPageData().size());
		assertEquals(3,result.getCurrentPageNo());
		en = result.getCurrentPageData();
		assertEquals("111",en.get(0).getTest());
		assertEquals("1",en.get(1).getTest());
		
		request.setStartPosition(6);
		result = pagingService.getPageData(request);
		assertEquals(6,result.getTotalCount());
		assertEquals(0,result.getCurrentPageData().size());
		assertEquals(0,result.getCurrentPageNo());
	}
	
	/**
	 * 2繝壹・繧ｸ4莉ｶ縲・莉ｶ蜿門ｾ・
	 */
	@Test
	public void twoPage(){
		
		dataset();
		
		PagingRequest request = new PagingRequest();
		QueryRequest q = new QueryRequest(SampleNativeQuery.class);
		request.setInternal(q);
		request.setPageSize(4);
		PagingResult result = pagingService.prepare(request);
		assertEquals(6,result.getTotalCount());
		assertEquals(4,result.getCurrentPageData().size());
		assertEquals(1,result.getCurrentPageNo());
		
		request.setStartPosition(4);
		request.setTotalCount(6);
		result = pagingService.getPageData(request);
		assertEquals(6,result.getTotalCount());
		assertEquals(2,result.getCurrentPageData().size());
		assertEquals(2,result.getCurrentPageNo());

		request.setStartPosition(8);
		result = pagingService.getPageData(request);
		assertEquals(6,result.getTotalCount());
		assertEquals(0,result.getCurrentPageData().size());
		assertEquals(0,result.getCurrentPageNo());
	}
	
	
}
