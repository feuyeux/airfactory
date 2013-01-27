package creative.air.mail.service.receive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;

import creative.air.mail.pojo.MailMessage;

public abstract class ReceiveService {
	protected Logger log = Logger.getLogger(getClass());
	protected String server;
	protected String username, password;

	public ReceiveService(String server, String username, String password) {
		this.server = server;
		this.username = username;
		this.password = password;
	}

	public List<MailMessage> fillMessages(Message[] messages) throws MessagingException, IOException {
		List<MailMessage> mailMessageList = new ArrayList<MailMessage>();
		String from;
		for (int i = 0; i < messages.length; i++) {
			Message m = messages[i];
			from = m.getFrom()[0].toString();
			InternetAddress ia = new InternetAddress(from);
			MailMessage mailMessage = new MailMessage(ia.getPersonal(), ia.getAddress(), m.getFlags(), m.getSubject(), m.getSize(), m.getContent());
			mailMessageList.add(mailMessage);
		}
		return mailMessageList;
	}

	public abstract List<MailMessage> receive();
}
