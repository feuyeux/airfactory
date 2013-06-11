package creative.fire.jms.ch2;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

public class Chat implements javax.jms.MessageListener {
	private TopicConnectionFactory conFactory;
	private TopicConnection connection;
	private TopicSession pubSession;
	private TopicSession subSession;
	private TopicPublisher publisher;
	private TopicSubscriber subscriber;
	private Topic chatTopic;
	
	private String username;
	private static Logger log = Logger.getLogger("Chat");

	/* Constructor used to Initialize Chat */
	public Chat(String topicFactory, String topicName, String username) throws Exception {

		// Obtain a JNDI connection using the jndi.properties file
		InitialContext ctx = new InitialContext();

		// Look up a JMS connection factory
		conFactory = (TopicConnectionFactory) ctx.lookup(topicFactory);

		// Create a JMS connection
		connection = conFactory.createTopicConnection();

		// Create two JMS session objects
		pubSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		subSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

		// Look up a JMS topic
		chatTopic = (Topic) ctx.lookup(topicName);

		// Create a JMS publisher and subscriber
		publisher = pubSession.createPublisher(chatTopic);
		subscriber = subSession.createSubscriber(chatTopic, null, true);

		// Set a JMS message listener
		subscriber.setMessageListener(this);

		// Start the JMS connection; allows messages to be delivered
		connection.start();
		log.info(username + " is coming.");
	}

	/* Receive Messages From Topic Subscriber */
	public void onMessage(Message message) {
		try {
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			System.out.println(text);
		} catch (JMSException jmse) {
			jmse.printStackTrace();
		}
	}

	/* Create and Send Message Using Publisher */
	protected void writeMessage(String text) throws JMSException {
		TextMessage message = pubSession.createTextMessage();
		message.setText(username + ": " + text);
		publisher.publish(message);
	}

	/* Close the JMS Connection */
	public void close() throws JMSException {
		connection.close();
	}

	/* Run the Chat Client */
	public static void main(String[] args) {
		try {
			Chat chat;
			if (args.length != 3) {
				chat = new Chat("TopicCF", "topic1", "Fred1");
			} else {
				chat = new Chat(args[0], args[1], args[2]);
			}

			// Read from command line
			BufferedReader commandLine = new java.io.BufferedReader(new InputStreamReader(System.in));

			// Loop until the word "exit" is typed
			while (true) {
				String s = commandLine.readLine();
				if (s.equalsIgnoreCase("exit")) {
					chat.close(); // close down connection
					System.exit(0);// exit program
				} else
					chat.writeMessage(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}