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
public class SearchFastCheckRankingDisplayTermResponse {

	private SearchFastCheckRankingDisplayTermWebServiceResponse searchFastCheckRankingDisplayTermWebServiceResponse;

	/**
	 * @return the searchFastCheckRankingDisplayTermWebServiceResponse
	 */
	public SearchFastCheckRankingDisplayTermWebServiceResponse getSearchFastCheckRankingDisplayTermWebServiceResponse() {
		return searchFastCheckRankingDisplayTermWebServiceResponse;
	}

	/**
	 * @param searchFastCheckRankingDisplayTermWebServiceResponse the searchFastCheckRankingDisplayTermWebServiceResponse to set
	 */
	public void setSearchFastCheckRankingDisplayTermWebServiceResponse(
			SearchFastCheckRankingDisplayTermWebServiceResponse searchFastCheckRankingDisplayTermWebServiceResponse) {
		this.searchFastCheckRankingDisplayTermWebServiceResponse = searchFastCheckRankingDisplayTermWebServiceResponse;
	}

}
