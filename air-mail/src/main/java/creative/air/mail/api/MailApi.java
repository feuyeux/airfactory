package creative.air.mail.api;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;

import creative.air.mail.pojo.MailMessage;
import creative.air.mail.service.receive.imap.ImapService;

@ManagedBean
@RequestScoped
public class MailApi {
	Logger log = Logger.getLogger(getClass());
	List<MailMessage> mailMessages;
	String server = "imap.163.com";
	String username = "feuyeux@163.com";
	String password = "z1J6BdqJCjgjjOHD6LguQQ==";

	public List<MailMessage> getMailMessages() {
		try {
			get();
		} catch (Exception e) {
			log.error(e);
		}
		return mailMessages;
	}

	public void setMailMessages(List<MailMessage> mailMessages) {
		this.mailMessages = mailMessages;
	}

	void get() throws Exception {
		ImapService service = new ImapService(server, username, new creative.air.security.DECTools().decode(password));
		mailMessages = service.receive();
	}
}
