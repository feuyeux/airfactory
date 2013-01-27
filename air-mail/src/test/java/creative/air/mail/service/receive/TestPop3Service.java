package creative.air.mail.service.receive;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import creative.air.mail.pojo.MailMessage;
import creative.air.mail.service.receive.pop3.Pop3Service;

public class TestPop3Service {
	Logger log = Logger.getLogger(getClass());
	String server = "pop3.163.com";
	String username = "feuyeux@163.com";
	String password = "z1J6BdqJCjgjjOHD6LguQQ==";
	creative.air.security.DECTools decTools = new creative.air.security.DECTools();

	@Test
	public void testReceive() throws Exception {
		Pop3Service pop3 = new Pop3Service(server, username, decTools.decode(password));
		List<MailMessage> mailMessages = pop3.receive();
		for (MailMessage mailMessage : mailMessages) {
			log.debug(mailMessage);
		}
	}
}
