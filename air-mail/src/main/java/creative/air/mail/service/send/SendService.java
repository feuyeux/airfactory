package creative.air.mail.service.send;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class SendService {
	String server;
	final String username, password;
	Logger log = Logger.getLogger(getClass());

	public SendService(String server, String username, String password) {
		this.server = server;
		this.username = username;
		this.password = password;
	}

	public void send(String toAddress, String ccAddress, String title, String content) {
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.host", server);
		properties.setProperty("mail.smtp.auth", "true");

		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};

		Transport transport = null;

		try {
			Session session = Session.getDefaultInstance(properties, authenticator);
			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(username));
			if (toAddress != null) {
				mimeMessage.setRecipients(javax.mail.Message.RecipientType.TO, toAddress);
			}
			if (ccAddress != null)
				mimeMessage.setRecipients(javax.mail.Message.RecipientType.CC, ccAddress);
			mimeMessage.setSubject(title);
			mimeMessage.setContent(content, "text/html;charset=gbk");
			transport = session.getTransport();
			transport.connect(username, password);
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (transport != null)
				try {
					transport.close();
				} catch (MessagingException logOrIgnore) {
				}
		}
	}
}