package au.com.trackingcentral.helpers;

import junit.framework.Assert;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import au.com.trackingcentral.BaseTest;

@Transactional
public class EmailFactoryTest extends BaseTest {

	@Autowired
	private EmailFactory emailFactoryTest;

	@Before
	public void setUp() {

	}

	@Test
	public void createEmailTest() throws EmailException {
		String hostname = "localhost";
		Integer port = 2525;
		String username = "fake";
		String password = "fake";
		String receiver = "fake@fake.com";
		String subject = "junk";
		String message = "junk msg";
		HtmlEmail email = emailFactoryTest.createEmail(hostname, port,
				username, password, receiver, subject, message);

		Assert.assertEquals(hostname, email.getHostName());
		Assert.assertEquals(port, new Integer(email.getSmtpPort()));
		Assert.assertEquals(receiver, email.getFromAddress().toString());
		Assert.assertEquals(receiver, email.getToAddresses().get(0).toString());
		Assert.assertEquals(subject, email.getSubject());
		Assert.assertEquals(hostname, email.getHostName());
	}

}
