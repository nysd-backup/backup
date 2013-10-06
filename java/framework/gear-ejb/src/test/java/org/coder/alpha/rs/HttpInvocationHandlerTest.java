package org.coder.alpha.rs;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.junit.Test;
import org.junit.Ignore;

@Ignore
public class HttpInvocationHandlerTest {

	@Test
	public void test() {
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(MOXyJsonProvider.class);	
		Client client = ClientBuilder.newBuilder().withConfig(clientConfig).build();
		WebTarget target = client.target("http://10.23.25.7:7001/m29-m4-ws/api/platform/startJob");
		MediaType requestType = MediaType.APPLICATION_JSON_TYPE;
		
		StartJobRequest request = new StartJobRequest();
		request.setJobId("TESTJOB");
		request.setRequestId("REQUEST999");
		
		Response response =  target.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));
		
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
	
	}
}
