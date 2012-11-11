package creative.air.schedule.dbreport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;

/**
 * reduce and action, then summary
 * 
 * @author feuyeux@gmail.com
 * 2012-11-11
 */
public class RetrieveAndCalculate {
	Logger logger = Logger.getLogger(getClass());
	int poolSize = 3;

	public void action() {
		ExecutorService service = Executors.newFixedThreadPool(poolSize);
		List<Future<Long>> tasks = new ArrayList<Future<Long>>(poolSize);
		Long[] datas = new Long[poolSize];

		int index = poolSize;
		while (index > 0) {
			Retrieve r = new Retrieve(poolSize - index + "");
			//1. launch the partial retrieving
			tasks.add(service.submit(r));
			index--;
		}

		index = poolSize;
		while (index > 0) {
			try {
				//2. waiting for the partial retrieving result
				datas[poolSize - index] = tasks.get(poolSize - index).get(60 * 1000, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				logger.error(e);
			} catch (ExecutionException e) {
				logger.error(e);
			} catch (TimeoutException e) {
				logger.error(e);
			} catch (Exception e) {
				logger.error(e);
			}
			index--;
		}

		//3. calculate the result
		calculate(datas);
		service.shutdown();
	}

	private void calculate(Long[] datas) {
		int sum = 0;
		for (int i = 0; i < datas.length; i++) {
			sum += i * 0.05 + datas[i];
		}
		logger.info("calculate result = " + sum);
	}
}