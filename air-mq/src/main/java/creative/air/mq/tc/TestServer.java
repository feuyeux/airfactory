package creative.air.mq.tc;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.jms.JMSException;

import org.apache.log4j.Logger;

import creative.air.mq.AirPublisher;

public class TestServer {
	Logger logger = Logger.getLogger(getClass());
	private String tsName;
	private ConcurrentLinkedQueue<TestQueue> runningQueue = new ConcurrentLinkedQueue<TestQueue>();
	private AirPublisher publisher;
	Random r = new Random(System.nanoTime());

	public TestServer(String tsName) throws JMSException {
		super();
		this.tsName = tsName;
		publisher = new AirPublisher();
	}

	boolean randomState() {
		return r.nextBoolean();
	}

	boolean randomQueue() {
		return r.nextBoolean();
	}

	private void reportState(String status) throws JMSException {
		logger.info("sending:: test server name=" + tsName + " status=" + status);
		publisher.sendMessage(tsName, TCConfiguration.STATE, status);
	}

	public boolean process() throws JMSException {
		boolean state = randomState();
		if (state) {
			reportState(TCConfiguration.SUCCESS);
		} else {
			reportState(TCConfiguration.FAILED);
		}
		return state;
	}
}
