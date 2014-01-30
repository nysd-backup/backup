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
		//サーバAPIキーを使用する
		client.login("380188014792@gcm.googleapis.com", "AIzaSyAEP573ikx83g5nqKmFHRXv0eKGsT02h18");
	}
}
