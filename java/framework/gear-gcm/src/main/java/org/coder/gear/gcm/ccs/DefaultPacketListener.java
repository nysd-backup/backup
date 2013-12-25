/**
 * 
 */
package org.coder.gear.gcm.ccs;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

/**
 * @author yoshida-n
 *
 */
public class DefaultPacketListener implements PacketListener{

	/**
	 * @see org.jivesoftware.smack.PacketListener#processPacket(org.jivesoftware.smack.packet.Packet)
	 */
	@Override
	public void processPacket(Packet packet) {
		 Message incomingMessage = (Message) packet;
	        GcmPacketExtension gcmPacket =
	            (GcmPacketExtension) incomingMessage.getExtension(GcmPacketExtension.GCM_NAMESPACE);
	        String json = gcmPacket.getJson();
	        JsonReader jsonReader = Json.createReader(new StringReader(json));
	        JsonObject jsonObject = jsonReader.readObject();
	        Object messageType = jsonObject.get("message_type");
	        System.out.println(messageType);
	}

}
