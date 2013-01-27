package creative.air.mail.service.receive;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import creative.air.mail.pojo.MailMessage;
import creative.air.mail.service.receive.imap.ImapService;
import creative.air.security.DECTools;

public class TestImapService {
	Logger log=Logger.getLogger(getClass());
	String server = "imap.163.com";
	String username = "feuyeux@163.com";
	String password = "z1J6BdqJCjgjjOHD6LguQQ==";
	
	@Test
	public void testReceive() throws Exception {
		ImapService imap = new ImapService(server, username, new creative.air.security.DECTools().decode(password));
		List<MailMessage> mailMessages = imap.receive();
		for (MailMessage mailMessage : mailMessages) {
			log.debug(mailMessage);
		}
	}
}
