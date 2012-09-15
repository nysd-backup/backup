import javax.xml.bind.annotation.XmlRootElement;

/**
 * Copyright 2011 the original author
 */

/**
 * function.
 *
 * @author yoshida-n
 * @version	created.
 */
@XmlRootElement
public class SearchFastCheckRankingDisplayTermRequest {

	private SearchFastCheckRankingDisplayTermWebServiceRequest searchFastCheckRankingDisplayTermWebServiceRequest;

	/**
	 * @return the searchFastCheckRankingDisplayTermWebServiceRequest
	 */
	public SearchFastCheckRankingDisplayTermWebServiceRequest getSearchFastCheckRankingDisplayTermWebServiceRequest() {
		return searchFastCheckRankingDisplayTermWebServiceRequest;
	}

	/**
	 * @param searchFastCheckRankingDisplayTermWebServiceRequest the searchFastCheckRankingDisplayTermWebServiceRequest to set
	 */
	public void setSearchFastCheckRankingDisplayTermWebServiceRequest(
			SearchFastCheckRankingDisplayTermWebServiceRequest searchFastCheckRankingDisplayTermWebServiceRequest) {
		this.searchFastCheckRankingDisplayTermWebServiceRequest = searchFastCheckRankingDisplayTermWebServiceRequest;
	}
}
