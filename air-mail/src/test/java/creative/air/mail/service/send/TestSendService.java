package creative.air.mail.service.send;
import org.junit.Test;

import creative.air.mail.service.send.SendService;
import creative.air.security.DECTools;

public class TestSendService {
	SendService sender;
	DECTools decTools = new DECTools();

	public void testTCSend() throws Exception {
		String server = "bjngsmail04.ap.thmulti.com";
		String mailUser = "lu.han@technicolor.com";
		String username = "AP\\hanl";// + ":" + mailUser;
		String password = "T8lEje45Y/GfFZxC58Nt7A==";
		sender = new SendService(server, username, decTools.decode(password));
		sender.send(mailUser, mailUser, "test", "test");
	}

	@Test
	public void test163Send() throws Exception {
		String server = "smtp.163.com";
		String username = "feuyeux@163.com";
		String password = "z1J6BdqJCjgjjOHD6LguQQ==";
		sender = new SendService(server, username, decTools.decode(password));
		String mailUser = "feuyeux@163.com";
		sender.send(mailUser, mailUser, "test", "this is a test mail from air-mail project.");
	}
}
