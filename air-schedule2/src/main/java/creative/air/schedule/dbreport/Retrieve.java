package creative.air.schedule.dbreport;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
/**
 * @author feuyeux@gmail.com
 * 2012-11-11
 */
public class Retrieve implements Callable<Long> {
	Logger logger = Logger.getLogger(getClass());
	String threadName;

	public Retrieve(String threadName) {
		this.threadName = threadName;
	}

	@Override
	public Long call() {
		long result = System.nanoTime();
		logger.info(threadName + " gets " + result);
		try {
			logger.info("sleep...");
			Thread.sleep(2 * 1000);
			logger.info("...awake");
		} catch (InterruptedException e) {
			logger.error(e);
		}
		return result;
	}
}
