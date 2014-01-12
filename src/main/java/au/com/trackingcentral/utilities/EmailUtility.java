package au.com.trackingcentral.utilities;

import org.apache.commons.mail.EmailException;

import au.com.trackingcentral.helpers.EmailFactory;

public class EmailUtility {
	private String hostname;
	private Integer port;
	private String receiver;
	private String username;
	private String password;
	private EmailFactory emailFactory;

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmailFactory(EmailFactory emailFactory) {
		this.emailFactory = emailFactory;
	}

	public void send(String subject, String message) throws EmailException {
		emailFactory.createEmail(hostname, port, receiver, username, password,
				subject, message).send();
	}
}
