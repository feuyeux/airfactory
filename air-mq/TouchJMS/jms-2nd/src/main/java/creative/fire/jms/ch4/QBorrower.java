package creative.fire.jms.ch4;

import java.io.*;
import java.util.Date;
import java.util.StringTokenizer;
import javax.jms.*;
import javax.naming.*;

import org.apache.log4j.Logger;

public class QBorrower {
	private QueueConnectionFactory qFactory;
	private QueueConnection qConnect;
	private QueueSession qSession;
	private QueueSender qSender;
	private QueueReceiver qReceiver;
	private Queue responseQ;
	private Queue requestQ;
	private static Logger log = Logger.getLogger("QBorrower");
	
	public QBorrower(String queuecf, String requestQueue, String responseQueue) {
		try {
			// Connect to the provider and get the JMS connection
			Context ctx = new InitialContext();
			qFactory = (QueueConnectionFactory) ctx.lookup(queuecf);
			qConnect = qFactory.createQueueConnection();

			// Create the JMS Session
			qSession = qConnect.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

			// Lookup the request and response queues
			requestQ = (Queue) ctx.lookup(requestQueue);
			responseQ = (Queue) ctx.lookup(responseQueue);
			
			// Create the sender and send the message
			qSender = qSession.createSender(requestQ);
			
			// Now that setup is complete, start the Connection
			qConnect.start();

		} catch (JMSException jmse) {
			jmse.printStackTrace();
			System.exit(1);
		} catch (NamingException jne) {
			jne.printStackTrace();
			System.exit(1);
		}
	}

	private void sendLoanRequest(double salary, double loanAmt) {
		try {
			// Create JMS message
			MapMessage msg = qSession.createMapMessage();
			msg.setDouble("Salary", salary);
			msg.setDouble("LoanAmount", loanAmt);
			//reply queue
			msg.setJMSReplyTo(responseQ);

			// set the message expiration to 30 seconds
			msg.setJMSExpiration(new Date().getTime() + 30000);

			qSender.send(msg);

			// Wait to see if the loan request was accepted or declined
			String filter = "JMSCorrelationID = '" + msg.getJMSMessageID() + "'";
			qReceiver = qSession.createReceiver(responseQ, filter);
			TextMessage tmsg = (TextMessage) qReceiver.receive(30000);
			log.info("filter="+filter);
			if (tmsg == null) {
				System.out.println("Lender not responding");
			} else {
				System.out.println("Loan request was " + tmsg.getText());
			}

		} catch (JMSException jmse) {
			jmse.printStackTrace();
			System.exit(1);
		}
	}

	public static void main(String argv[]) {
		String queuecf = null;
		String requestq = null;
		String responseq = null;
		QBorrower borrower;

		if (argv.length == 3) {
			queuecf = argv[0];
			requestq = argv[1];
			responseq = argv[2];
			borrower = new QBorrower(queuecf, requestq, responseq);
		} else {
			borrower = new QBorrower("QueueCF", "LoanRequestQ", "LoanResponseQ");
		}

		try {
			// Read all standard input and send it as a message
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("QBorrower Application Started");
			System.out.println("Press enter to quit application");
			System.out.println("Enter: Salary, Loan_Amount");
			System.out.println("\ne.g. 50000, 120000");

			while (true) {
				System.out.print("> ");

				String loanRequest = stdin.readLine();
				if (loanRequest == null || loanRequest.trim().length() <= 0) {
					borrower.exit();
				}

				// Parse the deal description
				StringTokenizer st = new StringTokenizer(loanRequest, ",");
				double salary = Double.valueOf(st.nextToken().trim()).doubleValue();
				double loanAmt = Double.valueOf(st.nextToken().trim()).doubleValue();

				borrower.sendLoanRequest(salary, loanAmt);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void exit() {
		try {
			qConnect.close();
		} catch (JMSException jmse) {
			jmse.printStackTrace();
		}
		System.exit(0);
	}
}
