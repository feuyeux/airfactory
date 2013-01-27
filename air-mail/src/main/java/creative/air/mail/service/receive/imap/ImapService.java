package creative.air.mail.service.receive.imap;

import java.util.List;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import com.sun.mail.imap.IMAPFolder;

import creative.air.mail.pojo.MailMessage;
import creative.air.mail.service.receive.ReceiveService;

public class ImapService extends ReceiveService {
	public ImapService(String server, String username, String password) {
		super(server, username, password);
	}

	public List<MailMessage> receive() {
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imap");
		props.setProperty("mail.debug", "false");
		props.setProperty("mail.imap.host", server);

		if (mustUseSSL()) {
			String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
			props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
			props.setProperty("mail.imap.socketFactory.fallback", "false");
			props.setProperty("mail.imap.socketFactory.port", "993");
			java.security.Security.setProperty("ssl.SocketFactory.provider", SSL_FACTORY);
		}

		Session s = Session.getDefaultInstance(props, null);
		Store store;
		Folder inbox = null;
		try {
			store = s.getStore("imap");
			store.connect(server, username, password);
			inbox = (IMAPFolder) store.getFolder("Inbox");
			inbox.open(Folder.READ_ONLY);
			Message[] messages = inbox.getMessages();
			int unReadMails = inbox.getUnreadMessageCount();
			return fillMessages(messages);
		} catch (Exception e) {
			log.error(e);
			return null;
		} finally {
			try {
				inbox.close(true);
			} catch (MessagingException e) {
				log.error(e);
			}
		}
	}

	public static boolean mustUseSSL() {
		return false;
	}
}