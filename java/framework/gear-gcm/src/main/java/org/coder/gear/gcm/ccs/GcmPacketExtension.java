/**
 * 
 */
package org.coder.gear.gcm.ccs;

import org.jivesoftware.smack.packet.DefaultPacketExtension;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.util.StringUtils;

/**
 * @author yoshida-n
 *
 */
public class GcmPacketExtension extends DefaultPacketExtension{
	
	public static final String GCM_SERVER = "gcm.googleapis.com";
	public static final int GCM_PORT = 5235;
	public static final String GCM_ELEMENT_NAME = "gcm";
	public static final String GCM_NAMESPACE = "google:mobile:data"; 
	  
	private final String json;
	
	/**
	 * @return jsons
	 */
	public String getJson(){
		return this.json;
	}
	
	/**
	 * @param json TO SET
	 */
	public GcmPacketExtension(String json) {
		super(GCM_ELEMENT_NAME, GCM_NAMESPACE);
	    this.json = json;
	}

    /**
     * @see org.jivesoftware.smack.packet.DefaultPacketExtension#toXML()
     */
    @Override
    public String toXML() {
      return String.format("<%s xmlns=\"%s\">%s</%s>", GCM_ELEMENT_NAME,
          GCM_NAMESPACE, json, GCM_ELEMENT_NAME);
    }

	/**
	 * @return get Packet
	 */
	public Packet toPacket() {
		return new Message() {
	        // Must override toXML() because it includes a <body>
	        @Override
	        public String toXML() {

	        	StringBuilder buf = new StringBuilder();
	        	buf.append("<message");
	        	if (getXmlns() != null) {
	        		buf.append(" xmlns=\"").append(getXmlns()).append("\"");
	        	}
	        	if (getPacketID() != null) {
	        		buf.append(" id=\"").append(getPacketID()).append("\"");
	        	}
	        	if (getTo() != null) {
	        		buf.append(" to=\"").append(StringUtils.escapeForXML(getTo())).append("\"");
	        	}
	        	if (getFrom() != null) {
	        		buf.append(" from=\"").append(StringUtils.escapeForXML(getFrom())).append("\"");
	        	}
	        	buf.append(">");
	        	buf.append(GcmPacketExtension.this.toXML());
	        	buf.append("</message>");
	        	return buf.toString();
	        }
	      
		};
	}
}
