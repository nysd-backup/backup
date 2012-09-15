import org.junit.Test;

import alpha.httpclient.config.ProxyProperty;
import alpha.httpclient.config.RequestProperty;
import alpha.httpclient.core.HttpClientFactory;

import alpha.httpclient.core.impl.HttpClientFactoryImpl;

/**
 * Copyright 2011 the original author
 */

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
public class TestUnit {

	@Test
	public void mostSimple() {
		RequestProperty property = new RequestProperty();
		ProxyProperty proxy = new ProxyProperty();
		proxy.setHost("10.42.5.10");
		proxy.setPort(8000);
		property.setProxyProperty(proxy);
		property.setContextRoot("http://kzemi-k3.benesse.ne.jp/center/api");
		HttpClientFactory factory = new HttpClientFactoryImpl();
		OrderService service = factory.createService(OrderService.class, property);
		
		SearchFastCheckRankingDisplayTermRequest request = new SearchFastCheckRankingDisplayTermRequest();
		request.setSearchFastCheckRankingDisplayTermWebServiceRequest(new SearchFastCheckRankingDisplayTermWebServiceRequest());
		service.order(request);
		service.order(request);
		service.order(request);
		service.order(request);
		service.order(request);
	}
	
	
}
