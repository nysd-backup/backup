/**
 * 
 */
package org.coder.gear.mail;

import java.io.IOException;
import java.util.Arrays;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author yoshida-n
 *
 */
@Ignore
public class MailSenderTest {

	@Test
	public void test() {
		Session session =SessionBuilder.nativeBuilder("localhost").withDbug(true).build();
		final MailSender sender = new MailSender();
		sender.changeTransporter(new Transporter() {
			
			@Override
			public void send(MimeMessage message) throws MessagingException {
				
				Assert.assertEquals("to@yahoo.co.jp", message.getRecipients(RecipientType.TO)[0].toString());
				Assert.assertEquals("bcc@gmail.com", message.getRecipients(RecipientType.BCC)[0].toString());
				Assert.assertEquals("cc@gmail.com", message.getRecipients(RecipientType.CC)[0].toString());
				
				String r;
				try {					
					r = IOUtils.readLines(message.getInputStream()).toString();
					Assert.assertEquals("【件名】バインド 件名です。",message.getSubject());
					Assert.assertTrue(r.contains("メールテンプレートバインド, めーるてんぷれーとバインド, ﾒｰﾙﾃﾝﾌﾟﾚｰとバインド,"));
				} catch (IOException e) {
					throw new IllegalStateException(e);
				}				
	
			}
		});
		sender.addParam("bind","バインド");
		sender.withTemplateName("classpath:test").withFromName("ふろむ").withTo(Arrays.asList("to@yahoo.co.jp"));
		sender.withBcc(Arrays.asList("bcc@gmail.com")).withCc(Arrays.asList("cc@gmail.com"));
		
		sender.send(session);
	}
}
