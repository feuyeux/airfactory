package creative.air.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class AirConsumer {
	private static transient ConnectionFactory factory;
	private transient Connection connection;
	private transient Session session;

	public AirConsumer() throws JMSException {
		factory = new ActiveMQConnectionFactory(AirMQConfiguration.brokerURL);
		connection = factory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	public void close() throws JMSException {
		if (connection != null) {
			connection.close();
		}
	}

	public Session getSession() {
		return session;
	}
}
