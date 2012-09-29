package alpha.httplcient.test;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import alpha.httpclient.config.ConnectionConfig;
import alpha.httpclient.config.KeepAliveScope;
import alpha.httpclient.config.ProxyConfig;
import alpha.httpclient.config.SchemeConfig;
import alpha.httpclient.config.Schemes;

/**
 * Copyright 2011 the original author
 */

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@Path("/fastcheckranking")
public interface OrderComplexService {

	@ProxyConfig(host="10.42.5.10", port=8000)
	@ConnectionConfig(connectionTimeout = 100,socketTimeout = 50,scope=KeepAliveScope.THREAD)	
	@Schemes( schemes={@SchemeConfig(name="http",port=8080) ,@SchemeConfig(name="https",port=443)})
	@POST
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.TEXT_XML)
	@Path("/searchFastCheckRankingDisplayTerm")
	public SearchFastCheckRankingDisplayTermResponse order(SearchFastCheckRankingDisplayTermRequest request);
}
