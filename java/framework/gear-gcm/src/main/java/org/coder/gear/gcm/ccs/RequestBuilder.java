/**
 * 
 */
package org.coder.gear.gcm.ccs;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

/**
 * @author yoshida-n
 *
 */
public class RequestBuilder {

	private final JsonObjectBuilder builder;
	
	/**
	 * @param to to set
	 * @param messageId to set
	 * @return self
	 */
	public static RequestBuilder builderFor(String registrationId,String messageId){
		RequestBuilder builder = new RequestBuilder();
		builder.builder.add("to",registrationId);		
		builder.builder.add("message_id",messageId);		
		return builder;
	}
	
	/**
	 * Constructor 
	 */
	private RequestBuilder(){
		builder = Json.createObjectBuilder();
	}
	
	/**
	 * @param ttl to set
	 * @return self
	 */
	public RequestBuilder ttl(long ttl){
		builder.add("time_to_live", ttl);
		return this;
	}
	
	/**
	 * @param delayWhileIdle to set 
	 * @return self
	 */
	public RequestBuilder delayWhileIdle(boolean delayWhileIdle){	
		builder.add("delay_while_idle", delayWhileIdle);
		return this;
	}
	
	/**
	 * @param collapseKey to set
	 * @return self
	 */
	public RequestBuilder collapseKey(String collapseKey){
		builder.add("collapse_key", collapseKey);
		return this;
	}
	
	/**
	 * @return json data
	 */
	public String build(String payload){
		builder.add("data", payload);
		return builder.build().toString();
	}
	
}
