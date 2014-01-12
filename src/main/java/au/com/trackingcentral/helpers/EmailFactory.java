package au.com.trackingcentral.helpers;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class EmailFactory {
	

	public HtmlEmail createEmail() {
		return new HtmlEmail();
	}

	public HtmlEmail createEmail(String hostname, Integer port,
			String username, String password, String receiver, String subject,
			String message) throws EmailException {
		HtmlEmail email = createEmail();
		email.setHostName(hostname);
		email.setSmtpPort(port);
		email.setAuthenticator(createAuthenticator(username, password));
		email.addTo(receiver);
		email.setFrom(receiver);
		email.setSubject(subject);
		email.setMsg(message);
		return email;
	}

	public DefaultAuthenticator createAuthenticator(String username,
			String password) {
		return new DefaultAuthenticator(username, password);
	}
}
