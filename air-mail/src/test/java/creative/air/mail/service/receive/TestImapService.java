package creative.air.mail.service.receive;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import creative.air.mail.pojo.MailMessage;
import creative.air.mail.service.receive.imap.ImapService;
import creative.air.security.DECTools;

public class TestImapService {
	Logger log=Logger.getLogger(getClass());
	
	//@Test
	public void testExReceive() throws Exception {
		String server = "bjngsmail04.ap.thmulti.com";
		String username = "AP\\hanl";
		String password = "T8lEje45Y/GfFZxC58Nt7A==";
		String mailUser = "lu.han@technicolor.com";
		ImapService imap = new ImapService(server, username, new creative.air.security.DECTools().decode(password));
		List<MailMessage> mailMessages = imap.receive();
		for (MailMessage mailMessage : mailMessages) {
			log.debug(mailMessage);
		}
	}
	
	//@Test
	public void testReceive() throws Exception {
		String server = "imap.163.com";
		String username = "feuyeux@163.com";
		String password = "z1J6BdqJCjgjjOHD6LguQQ==";
		ImapService imap = new ImapService(server, username, new creative.air.security.DECTools().decode(password));
		List<MailMessage> mailMessages = imap.receive();
		for (MailMessage mailMessage : mailMessages) {
			log.debug(mailMessage);
		}
	}
}
