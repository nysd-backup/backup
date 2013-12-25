/**
 * 
 */
package org.coder.gear.gcm.ccs;

import javax.net.ssl.SSLSocketFactory;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketInterceptor;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.provider.PacketExtensionProvider;
import org.jivesoftware.smack.provider.ProviderManager;
import org.xmlpull.v1.XmlPullParser;

/**
 * @author yoshida-n
 *
 */
public class GcmClient {
	
	private XMPPConnection connection;
	
	private ConnectionConfiguration config; 

	public GcmClient() throws XMPPException {
		ProviderManager.getInstance().addExtensionProvider(GcmPacketExtension.GCM_ELEMENT_NAME,
				GcmPacketExtension.GCM_NAMESPACE, new PacketExtensionProvider() {

			@Override
			public PacketExtension parseExtension(XmlPullParser parser)
					throws Exception {
				String json = parser.nextText();
				GcmPacketExtension packet = new GcmPacketExtension(json);
				return packet;
			}
        });
		connect();
    }
	
	/**
	 * @param connectionListener to add
	 * @return self
	 */
	public GcmClient addConnectionListener(ConnectionListener connectionListener){
		connection.addConnectionListener(connectionListener);
		return this;
	}
	
	/**
	 * @param packetListener to add
	 * @param packetFilter to add
	 * @return self
	 */
	public GcmClient addPacketListener(PacketListener packetListener,PacketTypeFilter packetFilter){
		connection.addPacketListener(packetListener, packetFilter);
		return this;
	}
	
	/**
	 * @param packetInterceptor to add
	 * @param packetFilter to add
	 * @return self
	 */
	public GcmClient addPacketInterceptor(PacketInterceptor packetInterceptor,PacketTypeFilter packetFilter){
		connection.addPacketWriterInterceptor(packetInterceptor, packetFilter);
		return this;
	}
	
	/**
	 * @param senderId
	 * @param apikey
	 * @throws XMPPException 
	 */
	public void login(String senderId , String apikey) throws XMPPException{
		connection.login(senderId, apikey);
	}
	
	/**
	 * @param message to send(json)
	 */
	public void send(String message) {
	    Packet request = new GcmPacketExtension(message).toPacket();
	    connection.sendPacket(request);
	}
	
	/**
	 * @param authId username
	 * @param apiKey password
	 * @throws XMPPException
	 */
	private void connect() throws XMPPException {
	    config = new ConnectionConfiguration(GcmPacketExtension.GCM_SERVER, GcmPacketExtension.GCM_PORT);
	    config.setSecurityMode(SecurityMode.enabled);
	    config.setReconnectionAllowed(true);
	    config.setRosterLoadedAtLogin(false);
	    config.setSendPresence(false);
	    config.setSocketFactory(SSLSocketFactory.getDefault());
	    config.setDebuggerEnabled(true);
	    connection = new XMPPConnection(config);
	    connection.connect();
	}
}
