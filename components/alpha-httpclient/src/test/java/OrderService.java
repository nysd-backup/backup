import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
public interface OrderService {

	@POST
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.TEXT_XML)
	@Path("/searchFastCheckRankingDisplayTerm")
	public SearchFastCheckRankingDisplayTermResponse order(SearchFastCheckRankingDisplayTermRequest request);
}
