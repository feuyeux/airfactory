package creative.air.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

public class AirPublisher {
	Logger logger = Logger.getLogger(getClass());
	protected static transient ConnectionFactory factory;
	protected transient Connection connection;
	protected transient Session session;
	protected transient MessageProducer producer;

	public AirPublisher() throws JMSException {
		factory = new ActiveMQConnectionFactory(AirMQConfiguration.brokerURL);
		connection = factory.createConnection();
		try {
			connection.start();
		} catch (JMSException e) {
			connection.close();
			logger.error("error in connect", e);
			throw e;
		}
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		producer = session.createProducer(null);
	}

	public void close() throws JMSException {
		if (connection != null) {
			connection.close();
		}
	}

	public void sendMessage(String topicName, String key, String value) throws JMSException {
		Destination destination = session.createTopic(topicName);
		MapMessage message = session.createMapMessage();
		message.setString(key, value);
		producer.send(destination, message);
	}
}
