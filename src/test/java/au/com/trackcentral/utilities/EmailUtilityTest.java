package au.com.trackcentral.utilities;

import javax.mail.internet.MimeMessage;

import junit.framework.Assert;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import au.com.trackingcentral.BaseTest;
import au.com.trackingcentral.helpers.EmailFactory;
import au.com.trackingcentral.utilities.EmailUtility;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.icegreen.greenmail.util.ServerSetupTest;

@Transactional
public class EmailUtilityTest extends BaseTest {

	private EmailFactory emailFactory;

	@Autowired
	private EmailUtility emailUtility;

	@Before
	public void setUp() {

	}

	@Test
	public void sendEmail() throws Exception {

		ServerSetup smtp = ServerSetupTest.SMTP;
		GreenMail greenMail = new GreenMail(smtp);
		greenMail.start();

		String hostname = "127.0.0.1";
		Integer port = smtp.getPort();
		String username = "username";
		String password = "password";
		String receiver = "jdoe@somewhere.org";
		String subject = "TestMail";
		String msg = "This is a test mail ... :-)";
		DefaultAuthenticator defaultAuthenticator = new DefaultAuthenticator(
				username, password);

		// mock createAuthenticator
		emailFactory = EasyMock.createMock(EmailFactory.class);
		emailFactory.createAuthenticator(username, password);
		EasyMock.expectLastCall().andReturn(defaultAuthenticator);

		// mock up injection of email utility attributes
		emailUtility.setHostname(hostname);
		emailUtility.setPort(port);
		emailUtility.setPassword(password);
		emailUtility.setReceiver(receiver);
		emailUtility.setUsername(username);
		emailUtility.setPassword(password);

		// setup mocked email
		HtmlEmail email = new HtmlEmail();
		email.setHostName(hostname);
		email.setSmtpPort(port);
		email.setAuthenticator(defaultAuthenticator);
		email.addTo(receiver);
		email.setFrom(receiver);
		email.setSubject(subject);
		email.setMsg(msg);

		// mock createEmail
		emailFactory.createEmail(hostname, port, receiver, username, password,
				subject, msg);
		EasyMock.expectLastCall().andReturn(email);

		EasyMock.replay(emailFactory);
		emailUtility.setEmailFactory(emailFactory);
		emailUtility.send(subject, msg);

		// I am expecting one email to be in the inbox
		MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
		Assert.assertEquals(1, receivedMessages.length);
		// Assert email subject and body.
		MimeMessage message = receivedMessages[0];
		Assert.assertEquals(subject,
				message.getSubject());
		//Assert.assertEquals(msg, new java.io.StringBufferInputStream(receivedMessages[0].getR))
		greenMail.stop();
	}
}
