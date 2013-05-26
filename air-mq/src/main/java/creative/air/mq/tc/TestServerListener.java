package creative.air.mq.tc;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.log4j.Logger;

public class TestServerListener implements MessageListener {
	Logger logger = Logger.getLogger(getClass());
	private String tsName;
	private Map<String, String> testServerMap;

	public TestServerListener(String tsName, ConcurrentMap<String, String> testServerMap) {
		super();
		this.tsName = tsName;
		this.testServerMap = testServerMap;
	}

	public void onMessage(Message message) {
		try {
			MapMessage map = (MapMessage) message;
			String status = map.getString(TCConfiguration.STATE);
			logger.info("receiving:: test server name=" + tsName + " status=" + status);
			testServerMap.put(tsName, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
