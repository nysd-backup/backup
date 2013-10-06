import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Copyright 2011 the original author, All Rights Reserved.
 */

/**
 * function.
 *
 * @author yoshida-n
 * @version	1.0
 */
@Ignore
public class HttpClientTest {

	@Test
	public void test() throws ClientProtocolException, IOException, InterruptedException {
		String url = "http://localhost:8089/alpha-domain-spring-example/api/order/create";		
		HttpPost post = new HttpPost(url);
		post.setHeader(HTTP.CONTENT_TYPE, "text/xml");
		post.setEntity(new StringEntity("<orderDto><orderNo>1</orderNo></orderDto>",Charset.forName("UTF-8")));		
		for(int i = 0 ; i < 100; i++){
			DefaultHttpClient client = new DefaultHttpClient();
			HttpResponse resposne = client.execute(post);
			System.out.println(resposne.getStatusLine().getStatusCode());
			Thread.sleep(500);
		}
		
	}
}
