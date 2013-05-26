package creative.air.mq.tc;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;

import org.apache.log4j.Logger;

import creative.air.mq.AirConsumer;

public class ResourceManager {
	Logger logger = Logger.getLogger(getClass());
	static ConcurrentMap<String, String> testServerMap = new ConcurrentHashMap<String, String>();

	public void registerTestServer(String tsName) throws JMSException {
		AirConsumer consumer = new AirConsumer();
		Destination destination = consumer.getSession().createTopic(tsName);
		MessageConsumer messageConsumer = consumer.getSession().createConsumer(destination);
		messageConsumer.setMessageListener(new TestServerListener(tsName, testServerMap));
	}

	public String getTestServerStatus(String tsName) {
		return testServerMap.get(tsName);
	}

	public String getTestQueueStatus(String queueName) {
		return TCConfiguration.FAILED;
	}

	public static ConcurrentMap<String, String> getTestServerMap() {
		return testServerMap;
	}
}
