package creative.air.mail.service.receive.pop3;

import java.util.List;
import java.util.Properties;

import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import creative.air.mail.pojo.MailMessage;
import creative.air.mail.service.receive.ReceiveService;

public class Pop3Service extends ReceiveService {

	public Pop3Service(String server, String username, String password) {
		super(server, username, password);
	}

	public List<MailMessage> receive() {
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "pop3");
		Session session = Session.getInstance(props);
		Folder inbox = null;
		try {
			Store store = session.getStore("pop3");
			session.setDebug(false);
			store.connect(server, username, password);
			inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);
			FetchProfile profile = new FetchProfile();
			profile.add(FetchProfile.Item.ENVELOPE);
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
}
