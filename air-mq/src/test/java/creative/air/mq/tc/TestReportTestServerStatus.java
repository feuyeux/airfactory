package creative.air.mq.tc;

import javax.jms.JMSException;

import junit.framework.Assert;

import org.junit.Test;

public class TestReportTestServerStatus {
	@Test
	public void testProcess() throws JMSException, InterruptedException {
		ResourceManager rm = new ResourceManager();
		String tsName = "TEE1";
		TestServer ts = new TestServer(tsName);
		rm.registerTestServer(tsName);

		for (int i = 0; i < 10; i++) {
			boolean process = ts.process();
			Thread.sleep(300);
			if (process) {
				Assert.assertEquals(TCConfiguration.SUCCESS, rm.getTestServerStatus(tsName));
			} else {
				Assert.assertEquals(TCConfiguration.FAILED, rm.getTestServerStatus(tsName));
			}
		}
	}
}
