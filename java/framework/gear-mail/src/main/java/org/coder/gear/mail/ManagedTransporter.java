package org.coder.gear.mail;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;


/**
 * ManagedTransportingGateway.
 * 
 * @author yoshida-n
 *
 */
public class ManagedTransporter implements Transporter {

    /**
     * @see org.coder.gear.mail.Transporter#send(javax.mail.internet.MimeMessage)
     */
    @Override
    public void send(MimeMessage message) throws MessagingException {
        Transport.send(message);
    }

}
