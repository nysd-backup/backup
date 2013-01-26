package alpha.httplcient.test;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.coder.alpha.httpclient.config.ConnectionConfig;
import org.coder.alpha.httpclient.config.KeepAliveScope;
import org.coder.alpha.httpclient.config.ProxyConfig;
import org.coder.alpha.httpclient.config.SchemeConfig;
import org.coder.alpha.httpclient.config.Schemes;


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
public interface OrderOverridableService {

	@ProxyConfig(host="xx.xx.xx.xx", port=65536)
	@ConnectionConfig(connectionTimeout = 10000,socketTimeout = 5000,scope=KeepAliveScope.REQUEST)
	@Schemes( schemes={@SchemeConfig(name="http",port=60) ,@SchemeConfig(name="https",port=111)})
	@POST
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.TEXT_XML)
	@Path("/searchFastCheckRankingDisplayTerm")
	public SearchFastCheckRankingDisplayTermResponse order(SearchFastCheckRankingDisplayTermRequest request);
}
