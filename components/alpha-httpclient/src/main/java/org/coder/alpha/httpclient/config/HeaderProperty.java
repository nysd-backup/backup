package org.coder.alpha.httpclient.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;

/**
 * Header property.
 *
 * @author yoshida-n
 * @version	created.
 */
public class HeaderProperty {
	
	/** version of http */
	private String httpVersion = "1.1";

	/** request headers */
	private List<Header> httpHeader = new ArrayList<Header>();
	
	/** request coolies */
	private List<Cookie> cookies = new ArrayList<Cookie>();
	
	/** coolie policy */
	private String cookiePolicy = null;
	
	/**
	 * @param header the header to add
	 */
	public void addHeader(Header header){
		this.httpHeader.add(header);
	}
	
	/**
	 * @param cookie the cookie to add
	 */
	public void addCookie(Cookie cookie){
		this.cookies.add(cookie);
	}

	/**
	 * @return the httpVersion
	 */
	public String getHttpVersion() {
		return httpVersion;
	}

	/**
	 * @param httpVersion the httpVersion to set
	 */
	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}

	/**
	 * @return the httpHeader
	 */
	public List<Header> getHttpHeader() {
		return httpHeader;
	}

	/**
	 * @param httpHeader the httpHeader to set
	 */
	public void setHttpHeader(List<Header> httpHeader) {
		this.httpHeader = httpHeader;
	}

	/**
	 * @return the cookies
	 */
	public List<Cookie> getCookies() {
		return cookies;
	}

	/**
	 * @param cookies the cookies to set
	 */
	public void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}

	/**
	 * @return the cookiePolicy
	 */
	public String getCookiePolicy() {
		return cookiePolicy;
	}

	/**
	 * @param cookiePolicy the cookiePolicy to set
	 */
	public void setCookiePolicy(String cookiePolicy) {
		this.cookiePolicy = cookiePolicy;
	}
	
	
}
