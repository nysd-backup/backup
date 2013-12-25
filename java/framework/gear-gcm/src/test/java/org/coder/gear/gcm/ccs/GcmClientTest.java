/**
 * 
 */
package org.coder.gear.gcm.ccs;

import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author yoshida-n
 *
 */
@Ignore
public class GcmClientTest {

	@Test
	public void test() throws Exception{
		GcmClient client = new GcmClient();
		client.addPacketListener(new DefaultPacketListener(), new PacketTypeFilter(Message.class));
		client.login("380188014792@gcm.googleapis.com", "AIzaSyAhC1q44MtjeyCEVXQTtjo_YidrfVJV1qk");
	}
}
