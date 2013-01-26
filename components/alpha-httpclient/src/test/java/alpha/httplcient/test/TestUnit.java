package alpha.httplcient.test;
import java.util.concurrent.ExecutionException;

import junit.framework.Assert;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.coder.alpha.httpclient.config.KeepAliveScope;
import org.coder.alpha.httpclient.config.RequestProperty;
import org.coder.alpha.httpclient.context.HttpClientContext;
import org.coder.alpha.httpclient.core.HttpClientFactory;
import org.coder.alpha.httpclient.core.impl.HttpClientFactoryImpl;
import org.coder.alpha.httpclient.handler.HttpAsyncCallback;
import org.coder.alpha.httpclient.handler.HttpCallback;
import org.junit.Ignore;
import org.junit.Test;


/**
 * Copyright 2011 the original author
 */

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@Ignore
public class TestUnit extends Assert implements HttpCallback{

	@Test
	public void mostSimple() {
		RequestProperty property = new RequestProperty();
		property.setContextRoot("http://10.23.25.2:8080/general-prep-center-web/api");
		HttpClientFactory factory = new HttpClientFactoryImpl();
		OrderService service = factory.createService(OrderService.class, property);
		
		SearchFastCheckRankingDisplayTermRequest request = new SearchFastCheckRankingDisplayTermRequest();
		request.setSearchFastCheckRankingDisplayTermWebServiceRequest(new SearchFastCheckRankingDisplayTermWebServiceRequest());
		SearchFastCheckRankingDisplayTermResponse res = service.order(request);		
		res.getSearchFastCheckRankingDisplayTermWebServiceResponse();
	}
	
	@Test
	public void fullSync() {
		HttpClientContext context = new HttpClientContext();
		context.initialize();
		try{
			RequestProperty property = new RequestProperty();
			property.getHeaderProperty().addHeader(new BasicHeader("User-Agent", "HTTP CLIENT TEST"));
			property.setContextRoot("http://10.23.25.2:8080/general-prep-center-web/api");
			HttpClientFactory factory = new HttpClientFactoryImpl();
			OrderComplexService service = factory.createService(OrderComplexService.class, property);
			
			SearchFastCheckRankingDisplayTermRequest request = new SearchFastCheckRankingDisplayTermRequest();
			request.setSearchFastCheckRankingDisplayTermWebServiceRequest(new SearchFastCheckRankingDisplayTermWebServiceRequest());
			SearchFastCheckRankingDisplayTermResponse res = service.order(request);		
			res.getSearchFastCheckRankingDisplayTermWebServiceResponse();
			service.order(request);		
			service.order(request);		
		}finally{
			context.release();
		}
	}
	
	@Test
	public void fullOverrideSync() {
		HttpClientContext context = new HttpClientContext();
		context.initialize();
		try{
			RequestProperty property = new RequestProperty();
			HttpHost host = new HttpHost("10.42.5.10",8000);
			ConnRouteParams.setDefaultProxy(property.getHttpParams(), host);
			
			property.getExecutionProperty().setHttpCallback(this);
			property.addScheme("http", 8080);
			property.addScheme("https", 443);
			property.getConnectionProperty().setKeepAliveScope(KeepAliveScope.THREAD);
			HttpConnectionParams.setSoTimeout(property.getHttpParams(),20000);
			HttpConnectionParams.setConnectionTimeout(property.getHttpParams(),10000);
			property.getHeaderProperty().addHeader(new BasicHeader("User-Agent", "HTTP CLIENT TEST"));
			property.setContextRoot("http://10.23.25.2:8080/general-prep-center-web/api");
			
			HttpClientFactory factory = new HttpClientFactoryImpl();
			OrderComplexService service = factory.createService(OrderComplexService.class, property);
			
			SearchFastCheckRankingDisplayTermRequest request = new SearchFastCheckRankingDisplayTermRequest();
			request.setSearchFastCheckRankingDisplayTermWebServiceRequest(new SearchFastCheckRankingDisplayTermWebServiceRequest());
			SearchFastCheckRankingDisplayTermResponse res = service.order(request);		
			res.getSearchFastCheckRankingDisplayTermWebServiceResponse();
			service.order(request);		
			service.order(request);		
			assertTrue(callbacked);
		}finally{
			context.release();
		}
	}
	@SuppressWarnings("unused")
	@Test
	public void asyncPerThread() throws InterruptedException, ExecutionException {
		
		HttpClientContext context = new HttpClientContext();
		context.initialize();
		
		try{
			RequestProperty property = new RequestProperty();
			property.getConnectionProperty().setKeepAliveScope(KeepAliveScope.THREAD);
			property.getExecutionProperty().setAsynchronous(true);
			HttpAsyncCallback<SearchFastCheckRankingDisplayTermResponse> callback = new HttpAsyncCallback<SearchFastCheckRankingDisplayTermResponse>();
			property.getExecutionProperty().setHttpCallback(callback);
			property.setContextRoot("http://10.23.25.2:8080/general-prep-center-web/api");
			HttpClientFactory factory = new HttpClientFactoryImpl();
			OrderService service = factory.createService(OrderService.class, property);
			
			SearchFastCheckRankingDisplayTermRequest request = new SearchFastCheckRankingDisplayTermRequest();
			request.setSearchFastCheckRankingDisplayTermWebServiceRequest(new SearchFastCheckRankingDisplayTermWebServiceRequest());
			service.order(request);		
			
			HttpAsyncCallback<SearchFastCheckRankingDisplayTermResponse> callback2 = new HttpAsyncCallback<SearchFastCheckRankingDisplayTermResponse>();
			property.getExecutionProperty().setHttpCallback(callback2);
			service = factory.createService(OrderService.class, property);
			service.order(request);
			
			HttpAsyncCallback<SearchFastCheckRankingDisplayTermResponse> callback3 = new HttpAsyncCallback<SearchFastCheckRankingDisplayTermResponse>();
			property.getExecutionProperty().setHttpCallback(callback3);
			service = factory.createService(OrderService.class, property);
			service.order(request);
	
			SearchFastCheckRankingDisplayTermResponse res1 = callback.get();
			SearchFastCheckRankingDisplayTermResponse res2 = callback2.get();
			SearchFastCheckRankingDisplayTermResponse res3 = callback3.get();
		}finally{
			context.release();
		}
	}
	
	private boolean callbacked = false;; 

	@Override
	public void callback(HttpResponse response) {
		callbacked = true;
	}
	
	
}
