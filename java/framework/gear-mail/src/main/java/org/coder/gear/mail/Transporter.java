package org.coder.gear.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * TransportingGateway.
 * 
 * @author yoshida-n
 *
 */
public interface Transporter {

    /**
     * @param message to send
     * @throws MessagingException
     */
    void send(MimeMessage message) throws MessagingException;

}
